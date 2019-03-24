package com.sap1ens.kafka;

import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerSimple {
    public static void main(String[] args) {
        String topic = "demo-topic";
//        String topic = "demo-topic-single-partition";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("auto.offset.reset", "earliest");
        props.put("group.id", "demo-consumer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.key() + "\t" + record.value());
            }
//            consumer.commitSync();
        }
    }
}
