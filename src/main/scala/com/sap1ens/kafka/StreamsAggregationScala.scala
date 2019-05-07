package com.sap1ens.kafka

import java.util.Properties

import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.Serdes._
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.kstream.KStream

object StreamsAggregationScala extends App {
  val props = new Properties
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-aggregation-v1")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, "exactly_once")
  val builder = new StreamsBuilder()

  val stream: KStream[String, String] = builder.stream("input-counts")

  stream
    .groupByKey
    .count
    .toStream
    .mapValues(_.toString)
    .to("output-counts")

  val streams = new KafkaStreams(builder.build, props)
  streams.start()
}
