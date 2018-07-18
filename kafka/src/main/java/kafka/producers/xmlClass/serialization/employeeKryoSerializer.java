package kafka.producers.xmlClass.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import kafka.producers.xmlClass.employee;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Closeable;
import java.util.Map;

/**
 * (De)serializes SensorReadings using Kryo.
 */
public class employeeKryoSerializer implements Closeable, AutoCloseable, Serializer<employee>, Deserializer<employee> {
  private ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
    protected Kryo initialValue() {
      Kryo kryo = new Kryo();
      kryo.addDefaultSerializer(employee.class, new KryoInternalSerializer());
      return kryo;
    }
  };

  @Override
  public void configure(Map<String, ?> map, boolean b) {
  }

  @Override
  public byte[] serialize(String s, employee employee) {
    ByteBufferOutput output = new ByteBufferOutput(100);
    kryos.get().writeObject(output, employee);
    return output.toBytes();
  }

  @Override
  public employee deserialize(String s, byte[] bytes) {
    try {
      return kryos.get().readObject(new ByteBufferInput(bytes), employee.class);
    }
    catch(Exception e) {
      throw new IllegalArgumentException("Error reading bytes",e);
    }
  }

  @Override
  public void close() {

  }

  private static class KryoInternalSerializer extends com.esotericsoftware.kryo.Serializer<employee> {
    @Override
    public void write(Kryo kryo, Output output, employee employee) {
      output.writeInt(employee.getId());
      output.writeString(employee.getName());
      output.writeString(employee.getGender());
      output.writeInt(employee.getAge());
      output.writeString(employee.getRole());
    }

    @Override
    public employee read(Kryo kryo, Input input, Class<employee> aClass) {
      int id = input.readInt();
      String name = input.readString();
      String gender = input.readString();
      int age = input.readInt();
      String role = input.readString();

      return new employee(id, name, gender, age, role);
    }
  }
}