package com.sap1ens.kafka;

import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerCounts {
    public static void main(String[] args) {
        Random rand = new Random();

        String topic = "input-counts";

        Integer keysBound = 5;
        Integer valuesBound = 100;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            for(Integer i = 0; i < valuesBound; i++) {
                String key = Integer.valueOf(rand.nextInt(keysBound)).toString();
                String value = Integer.valueOf(rand.nextInt(valuesBound)).toString();
                ProducerRecord<String, String> data = new ProducerRecord<>(topic, key, value);
                producer.send(data);
            }
        }
    }
}
