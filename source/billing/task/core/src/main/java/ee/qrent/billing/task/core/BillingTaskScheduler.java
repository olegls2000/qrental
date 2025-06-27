package ee.qrent.billing.task.core;

import ee.qrent.billing.task.api.in.usecase.TaskRunUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class BillingTaskScheduler {

  private final TaskRunUseCase taskRunUseCase;

  // seconds minutes hours day-of-month month day-of-week
  //   0       0       8        *         *        ?
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.

  @Scheduled(cron = "0 1 0 * * MON")
  public void scheduleQWeekCreationTask() {
    taskRunUseCase.runQWeekCreationTask();
  }

  @Scheduled(cron = "0 6 0 * * MON")
  public void scheduleInsuranceCalculationTask() {
    taskRunUseCase.runInsuranceCalculationTask();
  }

  @Scheduled(cron = "0 11 0 * * MON")
  public void scheduleRentCalculationTask() {
    taskRunUseCase.runRentCalculationTask();
  }

  @Scheduled(cron = "0 16 0 * * MON")
  public void scheduleObligationCalculationTask() {
    taskRunUseCase.runObligationCalculationTask();
  }

  @Scheduled(cron = "0 21 0 * * MON")
  public void scheduleWeeklyReportMondayTask() {
    taskRunUseCase.runWeeklyReportMondayTask();
  }
}
