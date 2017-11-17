package kafka.producers;

import kafka.producers.xmlClass.employee;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class employeeStAXReader {
  // Streaming API XML (StAX)
  // https://www.journaldev.com/1191/java-stax-parser-example-read-xml-file

  public static void main(String[] args) {
    Properties prop = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream stream = loader.getResourceAsStream("kafka.properties");
    try {
      prop.load(stream);

      Producer<String, employee> producer = new KafkaProducer<>(prop);

      String fileName = "src/main/resources/employee.xml";
      List<employee> empList = parseXML(fileName);

      for(employee emp : empList) {
        System.out.println(emp.toString());

        producer.send(new ProducerRecord<>("employees", Integer.toString(emp.getId()), emp));
        producer.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<employee> parseXML(String fileName) {
    List<employee> empList = new ArrayList<>();
    employee emp = null;
    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    try {
      XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
      while(xmlEventReader.hasNext()){
        XMLEvent xmlEvent = xmlEventReader.nextEvent();
        if (xmlEvent.isStartElement()){
          StartElement startElement = xmlEvent.asStartElement();
          if(startElement.getName().getLocalPart().equals("Employee")){
            emp = new employee();
            //Get the 'id' attribute from Employee element
            Attribute idAttr = startElement.getAttributeByName(new QName("id"));
            if(idAttr != null){
              emp.setId(Integer.parseInt(idAttr.getValue()));
            }
          }
          //set the other varibles from xml elements
          else if(startElement.getName().getLocalPart().equals("age")){
            xmlEvent = xmlEventReader.nextEvent();
            emp.setAge(Integer.parseInt(xmlEvent.asCharacters().getData()));
          }else if(startElement.getName().getLocalPart().equals("name")){
            xmlEvent = xmlEventReader.nextEvent();
            emp.setName(xmlEvent.asCharacters().getData());
          }else if(startElement.getName().getLocalPart().equals("gender")){
            xmlEvent = xmlEventReader.nextEvent();
            emp.setGender(xmlEvent.asCharacters().getData());
          }else if(startElement.getName().getLocalPart().equals("role")){
            xmlEvent = xmlEventReader.nextEvent();
            emp.setRole(xmlEvent.asCharacters().getData());
          }
        }
        //if Employee end element is reached, add employee object to list
        if(xmlEvent.isEndElement()){
          EndElement endElement = xmlEvent.asEndElement();
          if(endElement.getName().getLocalPart().equals("Employee")){
            empList.add(emp);
          }
        }
      }

    } catch (FileNotFoundException | XMLStreamException e) {
      e.printStackTrace();
    }
    return empList;
  }
}