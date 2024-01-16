package ee.qrental.task.core;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class ObligationCalculationTask {

  private final GetQWeekQuery qWeekQuery;
  private final ObligationCalculationAddUseCase calculationAddUseCase;

  // seconds minutes hours day-of-month month day-of-week
  //   0       0      8        *         *        ?
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.

  @Scheduled(cron = "5 10 0 * * MON") // means that the task is executed at 00:05:10 every Monday.
  public void scheduleTask() {
    final var executorName = ObligationCalculationTask.class.getSimpleName();
    System.out.println(executorName + "was started at: " + LocalDateTime.now());
    try {
      final var addRequest = new ObligationCalculationAddRequest();
      addRequest.setComment("Automatically triggered obligation calculation");
      addRequest.setQWeekId(getQWeekId());
      calculationAddUseCase.add(addRequest);
    } catch (Exception e) {
      System.out.println(executorName + " failed by next reason: " + e.getMessage());
      e.printStackTrace();
    } finally {
      System.out.println(executorName + " completed at: " + LocalTime.now());
    }
  }

  private Long getQWeekId() {
    final var qWeek = qWeekQuery.getCurrentWeek();
    if (qWeek == null) {
      throw new RuntimeException(
          "Impossible to start Obligation calculation for (d% year d% week number), because Q-week does not exist.");
    }

    return qWeek.getId();
  }
}
