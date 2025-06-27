package ee.qrent.billing.task.core.service;

import ee.qrent.billing.task.api.out.TaskRunResultAddPort;
import ee.qrent.billing.task.domain.TaskRunResult;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.usecase.QTask;
import ee.qrent.common.in.usecase.QTaskRunner;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static java.time.temporal.ChronoUnit.MILLIS;

@AllArgsConstructor
public class QTaskRunnerImpl implements QTaskRunner {

  private final QDateTime qDateTime;
  private final TaskRunResultAddPort addPort;

  @Transactional
  @Override
  public void run(final QTask qTask) {
    final var taskName = qTask.getName();
    final var startedAt = qDateTime.getNow();
    var status = STATUS_SUCCESS;
    System.out.println(taskName + " was started at: " + startedAt);

    try {
      qTask.getRunnable().run();

    } catch (Exception e) {
      System.out.println(taskName + " failed by next reason: " + e.getMessage());
      e.printStackTrace();
      status = STATUS_FAILED;
    } finally {
      final var finishedAt = qDateTime.getNow();
      final var durationInMillis = MILLIS.between(startedAt, finishedAt);
      final var taskRunResult =
          TaskRunResult.builder()
              .taskName(taskName)
              .startedAt(startedAt)
              .durationInMillis(durationInMillis)
              .status(status)
              .build();

      addPort.add(taskRunResult);
      System.out.println(taskRunResult);
    }
  }
}
