package kafka.serverSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class serverSocket {
  private static int port = 9090;
  /**
   * Application method to run the server runs in an infinite loop
   * listening on port 9090.  When a connection is requested, it
   * spawns a new thread to do the servicing and immediately returns
   * to listening.  The server keeps a unique client number for each
   * client that connects just to show interesting logging
   * messages.  It is certainly not necessary to do this.
   */
  public static void main(String[] args) throws Exception {
    System.out.println("Listening on port: " + Integer.toString(port));
    int clientNumber = 0;
    ServerSocket listener = new ServerSocket(port);
    try {
      while (true) {
        new xmlListener(listener.accept(), clientNumber++).start();
      }
    } finally {
      listener.close();
    }
  }

  /**
   * A private thread to handle XML messages requests on a particular socket.
   */
  private static class xmlListener extends Thread {
    private Socket socket;
    private int clientNumber;

    public xmlListener(Socket socket, int clientNumber) {
      this.socket = socket;
      this.clientNumber = clientNumber;
      log("NEW connection with client# " + clientNumber + " at " + socket);
    }

    /**
     * Services this thread's client by first sending the
     * client a welcome message then repeatedly reading strings
     * and sending back the capitalized version of the string.
     */
    public void run() {
      try {
        /**
         *  Output is flushed after every newline.
         */
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        /**
         * (Optional) Send a welcome message to the client.
         */
        //out.println("Hello, you are client #" + clientNumber + ".");

        /**
         * Get messages from the client, line by line.
         */
        while (true) {
          String input = in.readLine();
          log("Message from client# " + clientNumber + ": " + input);

          /**
           * Processing message.
           */


          /**
           * Receive one message => Close the socket connection.
           * If you receive several messages, setup the condition to finish the socket:
           * if(condicion) { break }
           */
          break;
        }
      } catch (IOException e) {
        log("Error handling client# " + clientNumber + ": " + e);
      } finally {
        try {
          socket.close();
        } catch (IOException e) {
          log("Couldn't close a socket, what's going on?");
        }
        log("Connection with client# " + clientNumber + " CLOSED");
      }
    }

    /**
     * Log a simple message.
     */
    private void log(String message) {
      System.out.println(message);
    }
  }
}