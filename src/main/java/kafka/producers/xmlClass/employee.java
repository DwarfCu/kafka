package kafka.producers.xmlClass;

import org.json.simple.JSONObject;

public class employee {
  private int id;
  private String name;
  private String gender;
  private int age;
  private String role;

  public employee() {  }

  public employee(int id, String name, String gender, int age, String role) {
    this.id = id;
    this.name = name;
    this.gender = gender;
    this.age = age;
    this.role = role;
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getGender() {
    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public int getAge() {
    return age;
  }
  public void setAge(int age) {
    this.age = age;
  }
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "Employee:: ID="+this.id+" Name=" + this.name + " Age=" + this.age + " Gender=" + this.gender + " Role=" + this.role;
  }

  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("ID", this.id);
    json.put("Name", this.name);
    json.put("Age", this.age);
    json.put("Gender", this.gender);
    json.put("Role", this.role);

    return json;
  }
}