package com.sap1ens.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerSimple {
    public static void main(String[] args) {
        String topic = "demo-topic";
        Integer count = 100;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            for(Integer i = 0; i < count; i++) {
                ProducerRecord<String, String> data = new ProducerRecord<>(topic, i.toString(), "value-" + i);
                producer.send(data);
            }
        }
    }
}
