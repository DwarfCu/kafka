package com.dwarfcu.kafka.streams;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

public class CompactLinks {

  private boolean URLvalid(String url) {
    String[] schemes = {"http","https"};
    UrlValidator urlValidator = new UrlValidator(schemes);

    return urlValidator.isValid(url);
  }

  public Topology createTopology() {
    StreamsBuilder builder = new StreamsBuilder();

    KStream<String, String> linksInput = builder.stream("links-input");

    KStream<String, String> links = linksInput
      .filter((key, value) -> URLvalid(value))
      .selectKey((ignoredKey, link) -> link.split("/")[2]);

    links.to("links-output", Produced.with(Serdes.String(), Serdes.String()));

    return builder.build();
  }

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "remove-duplicated-links");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    CompactLinks removeDuplicatedLinks = new CompactLinks();

    KafkaStreams streams = new KafkaStreams(removeDuplicatedLinks.createTopology(), props);
    streams.start();

    streams.localThreadsMetadata().forEach(data -> System.out.println(data));

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

}