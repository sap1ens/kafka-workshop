package com.sap1ens.kafka;

import java.time.Duration;
import java.util.Properties;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.Suppressed.BufferConfig;

public class StreamsAggregation {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-aggregation-v1");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, "org.apache.kafka.common.serialization.Serdes$StringSerde");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, "org.apache.kafka.common.serialization.Serdes$StringSerde");
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, "exactly_once");
//        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> stream = builder.stream("input-counts");

        stream
            .groupByKey()
            .count()
//            .suppress(Suppressed.untilTimeLimit(Duration.ofSeconds(15), BufferConfig.unbounded()))
            .toStream()
//            .mapValues(Object::toString)
            .to("output-counts");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
