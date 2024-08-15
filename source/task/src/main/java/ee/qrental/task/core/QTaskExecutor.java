package ee.qrental.task.core;

import ee.qrent.common.in.time.QDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QTaskExecutor {

  private final QDateTime qDateTime;

  protected void runTask(final Runnable runnable, final String executorName) {
    System.out.println(executorName + "was started at: " + qDateTime.getNow());
    try {
      runnable.run();
    } catch (Exception e) {
      System.out.println(executorName + " failed by next reason: " + e.getMessage());
      e.printStackTrace();
    } finally {
      System.out.println(executorName + " completed at: " + qDateTime.getNow());
    }
  }
}
