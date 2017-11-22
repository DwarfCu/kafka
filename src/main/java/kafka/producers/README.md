# XML Messages
Example of a Kafka Producer for importing XML messages (e.g. employee class) from a file (parseXMLFile) or from a socket -tcp port 9090- (parseXMLStream).

It's based on the following documents/links:
## XML Parsers

1. <b>Streaming API XML (StAX)</b>:

Lesson: Streaming API for XML (The Java Tutorials): https://docs.oracle.com/javase/tutorial/jaxp/stax/index.html

Java StAX Parser Example to read XML file: https://www.journaldev.com/1191/java-stax-parser-example-read-xml-file

2. <b>DOM parser</b>: <i>Pendiente</i>

Convert an XML file to CSV file using javaAsk Question: http://www.tuicode.com/question/58eaa08bba8da7dd0545df04

3. <b>Schema Registry</b> (Confluent): <i>Pendiente</i>

## Custom (De)Serializes

Kafka - Creating custom serializers. Create Kafka serializers for JSON, Kryo and Smile (by <a href=https://github.com/nielsutrecht/kafka-serializer-example>nielsutrecht)</a>: http://niels.nu/blog/2016/kafka-custom-serializers.html