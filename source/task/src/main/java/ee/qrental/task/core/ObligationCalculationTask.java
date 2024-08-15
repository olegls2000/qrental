package ee.qrental.task.core;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrental.constant.api.in.query.GetQWeekQuery;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class ObligationCalculationTask {

  private final ObligationCalculationAddUseCase addUseCase;
  private final GetQWeekQuery qWeekQuery;
  private final QTaskExecutor qTaskExecutor;

  // seconds minutes hours day-of-month month day-of-week
  //   0       0      8        *         *        ?
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.

  @Scheduled(cron = "5 10 0 * * MON") // means that the task is executed at 00:05:10 every Monday.
  public void scheduleTask() {
    final var executorName = ObligationCalculationTask.class.getSimpleName();
    qTaskExecutor.runTask(
        () -> {
          final var addRequest = new ObligationCalculationAddRequest();
          addRequest.setComment("Automatically triggered obligation calculation");
          addRequest.setQWeekId(qWeekQuery.getCurrentWeek().getId());
          addUseCase.add(addRequest);
        },
        executorName);
  }
}
