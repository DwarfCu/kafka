package kafka.socket.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class tcpSend {
  public String tcpSend(String ip, int port, int timeout, String content) {
    String modifiedSentence;
    Socket clientSocket;
    try {
      clientSocket = new Socket(ip, port);
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      outToServer.writeBytes(content + '\n');
      clientSocket.setSoTimeout(timeout);
      modifiedSentence = inFromServer.readLine();
      clientSocket.close();
      outToServer.close();
      inFromServer.close();
    } catch (Exception exc) {
      modifiedSentence = "";
    }
    return modifiedSentence;
  }
}
