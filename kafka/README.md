# Kafka DwarfCu Examples
1. <a href="https://github.com/DwarfCu/kafka/tree/master/kafka/src/main/java/kafka/producers">Custom Kafka Producers.</a>
2. <a href="https://github.com/DwarfCu/kafka/tree/master/kafka/src/main/java/kafka/consumers">Custom Kafka Consumers.</a>
3. <a href="https://github.com/DwarfCu/kafka/tree/master/kafka/src/main/java/kafka/producers/xmlClass/serialization">Custom (De)Serializers.</a>

### XML messages
##### Demo Environment

Start a Zookeeper Server (default configuration):

    $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties

Start a Kafka Broker (default configuration):

    $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties

Create topic (if not created yet):

    kafka-topics.sh --zookeeper localhost:2181 --topic employees --create --replication-factor 1 --partitions 1

Run employeeConsumer class:

    java -classpath kafka-dwarfcu-examples-1.0-SNAPSHOT.jar employeeConsumer

Run employeeSocket class:

    java -classpath kafka-dwarfcu-examples-1.0-SNAPSHOT.jar employeeSocket

Run employeesThreadPool class:

    java -classpath kafka-dwarfcu-examples-1.0-SNAPSHOT.jar employeesThreadPool
