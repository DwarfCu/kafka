# Kafka DwarfCu Examples

1. <a href="https://github.com/DwarfCu/kafka/tree/master/src/main/java/kafka/producers">Custom Kafka Producers.</a>

  * XML messages:
  
    * <a href="https://github.com/DwarfCu/kafka/tree/master/src/main/java/kafka/producers#employeestaxreader">From a file</a> (e.g. resources/employee.xml)
    
         java ...
         
    * <a href="https://github.com/DwarfCu/kafka/tree/master/src/main/java/kafka/serverSocket">From a socket</a> (e.g. tcp port 9090)
    
      java ...

2. Custom Kafka Consumers.

## Test Environment

Start a Zookeeper Server (default configuration):

    $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties

Start a Kafka Broker (default configuration):

    $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties

Create topic (if not created yet):

    kafka-topics.sh --zookeeper localhost:2181 --topic employees --create --replication-factor 1 --partitions 1

Run kafka.consumers.employeeConsumer class:

    java -classpath kafka-dwarfcu-examples-1.0-SNAPSHOT.jar kafka.consumers.employeeConsumer

Run kafka.socket.server.employeeSocket class:

    java -classpath kafka-dwarfcu-examples-1.0-SNAPSHOT.jar kafka.socket.server.employeeSocket

Run kafka.messageGenerators.employeesThreadPool class:

    java -classpath kafka-dwarfcu-examples-1.0-SNAPSHOT.jar kafka.messageGenerators.employeesThreadPool