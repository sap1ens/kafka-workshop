package com.sap1ens.kafka.game_analytics;

import com.sap1ens.kafka.game_analytics.model.User;
import com.sap1ens.kafka.game_analytics.model.UserActivity;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

public class StreamsAnalytics {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-analytics-v1");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, "org.apache.kafka.common.serialization.Serdes$StringSerde");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, "com.sap1ens.kafka.game_analytics.serde.JSONSerde");
        StreamsBuilder builder = new StreamsBuilder();

        KTable<String, User> users = builder.table("users");

        KStream<String, UserActivity> usersActivity = builder.stream("user_activity");

        KTable<Windowed<String>, Long> activityByConsole = usersActivity
            .leftJoin(users, (activity, user) -> {
                if (user != null) {
                    Map<String, String> headers = activity.getMetadata();
                    headers.put("console", user.getConsole().toString());
                }
                return activity;
            })
            .map((userId, activity) -> new KeyValue<>(activity.getMetadata().get("console"), activity))
            .groupByKey()
            .windowedBy(TimeWindows.of(Duration.ofSeconds(15)))
            .count();

        activityByConsole
            .toStream((windowedConsole, count) -> windowedConsole.toString())
            .to("user_activity_by_console", Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
