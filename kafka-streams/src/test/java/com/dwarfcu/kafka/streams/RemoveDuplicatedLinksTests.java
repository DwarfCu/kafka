package com.dwarfcu.kafka.streams;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
import org.apache.kafka.streams.test.OutputVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class RemoveDuplicatedLinksTests {
  private TopologyTestDriver topologyTestDriver;

  private StringSerializer stringSerializer = new StringSerializer();

  private ConsumerRecordFactory<String, String> recordFactory = new ConsumerRecordFactory<>(stringSerializer, stringSerializer);

  @Before
  public void setUpTopologyTestDriver() {
    Properties config = new Properties();
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, "Links-Tests");
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "noneeded:9092");
    config.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    config.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    RemoveDuplicatedLinks removeDuplicatedLinks = new RemoveDuplicatedLinks();

    topologyTestDriver = new TopologyTestDriver(removeDuplicatedLinks.createTopology(), config);
  }

  // Always include the following method
  @After
  public void closeTestDriver() {
    topologyTestDriver.close();
  }

  private void pushNewInputRecord(String value) {
    topologyTestDriver.pipeInput(recordFactory.create("links-input", null, value));
  }

  private ProducerRecord<String, String> readOutput() {
    return topologyTestDriver.readOutput("links-output", new StringDeserializer(), new StringDeserializer());
  }

  @Test
  public void notValidLink() {
    String input = "withouthttp.com";
    pushNewInputRecord(input);
    assertEquals(readOutput(), null);
  }

  @Test
  public void blankLink() {
    String input = "\n";
    pushNewInputRecord(input);
    assertEquals(readOutput(), null);
  }

  @Test
  public void validLink() {
    String httpInput = "http://www.strava.com/";
    pushNewInputRecord(httpInput);
    OutputVerifier.compareKeyValue(readOutput(), "www.strava.com", "http://www.strava.com/");

    String httpsInput = "https://www.google.es/search?q=kafka+streams";
    pushNewInputRecord(httpsInput);
    OutputVerifier.compareKeyValue(readOutput(), "www.google.es", "https://www.google.es/search?q=kafka+streams");
  }
}
