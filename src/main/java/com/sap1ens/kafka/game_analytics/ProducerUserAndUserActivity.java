package com.sap1ens.kafka.game_analytics;

import com.sap1ens.kafka.game_analytics.dao.UserRegistry;
import com.sap1ens.kafka.game_analytics.model.User;
import com.sap1ens.kafka.game_analytics.model.UserActivity;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerUserAndUserActivity {
    private static Properties props = new Properties() {{
        put("bootstrap.servers", "localhost:9092");
        put("acks", "all");
        put("retries", 0);
        put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        put("value.serializer", "com.sap1ens.kafka.game_analytics.serde.JSONSerializer");
    }};

    public static void main(String[] args) {
        new Thread(ProducerUserAndUserActivity::produceUserData).start();
        new Thread(ProducerUserAndUserActivity::produceUserActivityData).start();
    }

    public static void produceUserData() {
        String topic = "users";

        try (KafkaProducer<String, User> producer = new KafkaProducer<>(props)) {
            while(true) {
                User user = UserRegistry.findUser();

                ProducerRecord<String, User> data = new ProducerRecord<>(
                    topic,
                    user.getId().toString(),
                    user
                );
                producer.send(data);

                Thread.sleep(5000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void produceUserActivityData() {
        String topic = "users_activity";

        try (KafkaProducer<String, UserActivity> producer = new KafkaProducer<>(props)) {
            while(true) {
                UserActivity userActivity = UserActivity.generate();

                ProducerRecord<String, UserActivity> data = new ProducerRecord<>(
                    topic,
                    userActivity.getUserId().toString(),
                    userActivity
                );
                producer.send(data);

                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
