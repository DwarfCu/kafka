# KAFKA STREAMS
### Environment
Add <i>kafka</i> user:

    useradd kafka 
    passwd kafka

Download and install Apache Kafka:

    sudo tar xvzf kafka-<version>.tar.gz
    sudo mv kafka-<version> /usr/local/
    sudo chown -R kafka:kafka /usr/local/kafka-<version>
    sudo ln -s /usr/local/kafka-<version>/ /usr/local/kafka

Start Zookeeper server:

    su kafka /usr/local/kafka/bin/zookeeper-server-start.sh /usr/local/kafka/config/zookeeper.properties

Start Kafka Broker server:

    su kafka /usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties

### Example 1) Tutorial Kafka Streams (High-Level Streams DSL): Write a streams application
Create topics:

    /usr/local/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --topic streams-plaintext-input --create --partitions 1 --replication-factor 1

    /usr/local/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --topic streams-pipe-output --create --partitions 1 --replication-factor 1

    /usr/local/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --topic streams-linesplit-output --create --partitions 1 --replication-factor 1

    /usr/local/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --topic streams-wordcount-output --create --partitions 1 --replication-factor 1
 
[Maven] Build Project:

    mvn clean package

Launch JAR application:
java 

Start kafka-producer:

    /usr/local/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input

Start kafka-consumer:

    1) Pipe:
    /usr/local/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic streams-pipe-output --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
    
    2) LineSplit:
    /usr/local/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic streams-linesplit-output --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
    
    3) WordCount:
    /usr/local/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic streams-wordcount-output --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

### Example 2) Tutorial Kafka Streams: Low-Level Processor API

### Example 3) ...
