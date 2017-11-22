package kafka.consumers;

import kafka.producers.xmlClass.employee;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class employeeConsumer {
  public static void main(String[] args) {
    Properties prop = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream stream = loader.getResourceAsStream("kafka-consumer-employee.properties");

    try {
      prop.load(stream);

      KafkaConsumer<String, employee> consumer = new KafkaConsumer<>(prop);
      consumer.subscribe(Arrays.asList("employees"));
      while (true) {
        ConsumerRecords<String, employee> records = consumer.poll(100);
        for (ConsumerRecord<String, employee> record : records)
          System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
