package kafka.messageGenerators;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class employeesThreadPool {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 10; i++) {
      Runnable worker = new employeesThread(i);
      executor.execute(worker);
    }
    executor.shutdown();
    while (!executor.isTerminated()) {
    }
  }

}
