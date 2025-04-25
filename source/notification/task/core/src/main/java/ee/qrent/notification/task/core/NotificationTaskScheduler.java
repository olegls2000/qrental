package ee.qrent.notification.task.core;

import ee.qrent.common.in.usecase.RunTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class NotificationTaskScheduler {

  private final RunTaskUseCase runTaskUseCase;
  private final EmailNotificationTask task;

  // seconds minutes hours day-of-month month day-of-week
  //   0       0      8        *         *        ?
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.

  @Scheduled(cron = "5 2 * * * ?")
  public void scheduleEmailNotificationTask() {
    runTaskUseCase.run(task);
  }
}
