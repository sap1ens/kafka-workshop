package com.sap1ens.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object ProducerSimpleScala extends App {
  val topic = "demo-topic"
  val count = 100

  val props = new Properties
  props.put("bootstrap.servers", "localhost:9092")
  props.put("acks", "all")
  props.put("retries", 0)
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  try {
    for (i <- 0 to count) {
      val data = new ProducerRecord[String, String](topic, i.toString, "value-" + i)
      producer.send(data)
    }
  } finally {
    producer.close()
  }
}
