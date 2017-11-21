package kafka.messageGenerators;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class employees {

  public static void main(String[] args) {
    int num_messages = 1000;

    String name;
    String gender;
    int age;
    String role;
    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    for (int i=0; i<=num_messages; i++) {
      Random randomAge = new Random();
      age = randomAge.nextInt(100);

      StringBuilder sb = new StringBuilder();
      Random randomName = new Random();
      for (int j = 0; j < 8; j++) {
        char c = chars[randomName.nextInt(chars.length)];
        sb.append(c);
      }
      name = sb.toString();

      if (i%2 == 0) { gender = "male"; } else { gender = "female"; }

      StringBuilder sb2 = new StringBuilder();
      Random randomRole = new Random();
      for (int j = 0; j < 15; j++) {
        char c = chars[randomName.nextInt(chars.length)];
        sb2.append(c);
      }
      role = sb2.toString();

      String message = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Employees><Employee id=\"" + Integer.toString(i) + "\"><age>" + Integer.toString(age) + "</age><name>" + name + "</name><gender>" + gender + "</gender><role>" + role + "</role></Employee></Employees>";

      tcpSend("127.0.0.1", 9090, 5000 , message);
    }
  }

  private static String tcpSend(String ip, int port, int timeout, String content)
  {
    String ipaddress = ip;
    int portnumber = port;
    String sentence;
    String modifiedSentence;
    Socket clientSocket;
    try
    {
      clientSocket = new Socket(ipaddress, portnumber);
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      outToServer.writeBytes(content + '\n');
      clientSocket.setSoTimeout(timeout);
      modifiedSentence = inFromServer.readLine();
      clientSocket.close();
      outToServer.close();
      inFromServer.close();
    }
    catch (Exception exc)
    {
      modifiedSentence = "";
    }
    return modifiedSentence;
  }
}
