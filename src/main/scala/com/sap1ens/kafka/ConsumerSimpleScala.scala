package com.sap1ens.kafka

import java.util.{Collections, Properties}

import scala.collection.JavaConverters._

import org.apache.kafka.clients.consumer.KafkaConsumer

object ConsumerSimpleScala extends App {
  val topic = "demo-topic"

  val props = new Properties
  props.put("bootstrap.servers", "localhost:9092")
  props.put("auto.offset.reset", "earliest")
  props.put("group.id", "demo-consumer")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Collections.singletonList(topic))

  while (true) {
    val records = consumer.poll(Long.MaxValue).asScala
    for (record <- records) {
      println(record.key() + "\t" + record.value)
    }
//    consumer.commitSync()
  }
}