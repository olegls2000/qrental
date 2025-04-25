package ee.qrent.common.core.task;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.usecase.QTask;
import ee.qrent.common.in.usecase.RunTaskUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RunTaskUseCaseImpl implements RunTaskUseCase {

  private final QDateTime qDateTime;

  @Transactional
  @Override
  public void run(final QTask qTask) {
    final var taskName = qTask.getName();
    System.out.println(taskName + " was started at: " + qDateTime.getNow());
    try {
      qTask.getRunnable().run();
    } catch (Exception e) {
      System.out.println(taskName + " failed by next reason: " + e.getMessage());
      e.printStackTrace();
    } finally {
      System.out.println(taskName + " completed at: " + qDateTime.getNow());
    }
  }
}
