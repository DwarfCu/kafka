# Mockaroo Kafka Producer

Many times at many projects the dataset is not available while we are starting to develop, or simply it doesn't exist. This project tries to simulate streaming events.

[Mockaroo](https://mockaroo.com/) let you to create an API to get events from a schema that you have defined previously. Then, a Kafka Producer calls your Mockaroo API and pushes the events to a *topic*. Several Kafka Producers can be running at the same time.

Events are checked against an schema thanks to **Confluent Schema Registry** service.

### [HowTo] Use it 
 
1. Create a schema and API at [Mockaroo](https://mockaroo.com/). For example:

    BankBalanceDataset: https://mockaroo.com/9b0d87e0

2. Setup ***resources/mockaroo.properties***. For example:
  
       mockaroo.url=https://api.mockaroo.com/api/9b0d87e0?count=1000&key=<your_key>

3. Create the right AVRO Schema ***resources/avro/avro-schema.avsc***. Please, edit only the *fields* section. For BankBalanceDataset example:

       {
         "type": "record",
         "namespace": "com.dwarfcu.mockaroo",
         "name": "Dataset",
         "version": "1",
         "fields": [
           { "name": "name", "type": "string", "doc": "First Name of customer" },
           { "name": "amount", "type": "string", "doc": "Amount of money between [0..100]" },
           { "name": "date", "type": "string", "doc": "Date of transaction" },
           { "name": "time", "type": "string", "doc": "Time of transaction" }
         ]
       }

4. Setup ***resources/kafka.properties*** according to your Confluent-Kafka environment. For
 
       BOOTSTRAP_SERVERS_CONFIG=localhost:9092
       SCHEMA_REGISTRY_URL_CONFIG=http://localhost:8081
       CLIENT_ID_CONFIG=MockarooBankBalanceDatasetProducer
       TOPIC=mockarooBankBalanceDataset
       
5. Maven project.

       bash$ mvn clean package

6. Run **MockarooProducer** class.

#

##### [HowTo] Deploy a Test Environment with Docker

Create network:
````
docker network create confluent
````
Start Zookeeper:
````
docker run -d \
    --net=confluent \
    --name=zookeeper \
    -p 2181:2181 \
    -e ZOOKEEPER_CLIENT_PORT=2181 \
    confluentinc/cp-zookeeper:5.0.0
````
Start Kafka:
````
docker run -d \
    --net=confluent \
    --name=kafka \
    -p 9092:9092 \
    -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 \
    -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
    confluentinc/cp-kafka:5.0.0
````
Start Schema Registry:
````
docker run -d \
  --net=confluent \
  --name=schema-registry \
  -p 8081:8081 \
  -e SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL=zookeeper:2181 \
  -e SCHEMA_REGISTRY_HOST_NAME=schema-registry \
  -e SCHEMA_REGISTRY_LISTENERS=http://0.0.0.0:8081 \
  confluentinc/cp-schema-registry:5.0.0
````
Edit ***resources/kafka.properties***.
````
BOOTSTRAP_SERVERS_CONFIG=localhost:9092
SCHEMA_REGISTRY_URL_CONFIG=http://localhost:8081
CLIENT_ID_CONFIG=<your_client_id>
TOPIC=<your_topic>
````
Run MockarooProducer class.

[+INFO] [Confluent: Single Node Basic Deployment on Docker](https://docs.confluent.io/current/installation/docker/docs/installation/single-node-client.html)