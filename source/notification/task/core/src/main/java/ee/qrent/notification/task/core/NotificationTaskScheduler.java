package ee.qrent.notification.task.core;

import ee.qrent.notification.task.api.in.usecase.TaskRunUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class NotificationTaskScheduler {

  private final TaskRunUseCase taskRunUseCase;

  @Scheduled(cron = "0 0 * * * *")
  public void scheduleInsuranceCalculationTask() {
    taskRunUseCase.runEmailNotificationTask();
  }
}
