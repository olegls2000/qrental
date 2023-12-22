package ee.qrental.task.core;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
public class RentCalculationTask {

  private final GetQWeekQuery qWeekQuery;
  private final RentCalculationAddUseCase rentCalculationAddUseCase;

  // seconds minutes hours day-of-month month day-of-week
  //   0       0      8        *         *        ?
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.

  @Scheduled(cron = "5 5 0 * * MON") // means that the task is executed at 00:05:05 every Monday.
  public void scheduleTask() {
    final var executorName = RentCalculationTask.class.getSimpleName();
    System.out.println(executorName + "was started at: " + LocalDateTime.now());
    try {
      final var addRequest = new RentCalculationAddRequest();
      addRequest.setComment("Automatically triggered rent calculation");
      addRequest.setQWeekId(getQWeekId());
      rentCalculationAddUseCase.add(addRequest);
    } catch (Exception e) {
      System.out.println(executorName + " failed by next reason: " + e.getMessage());
      e.printStackTrace();
    } finally {
      System.out.println(executorName + " completed at: " + LocalTime.now());
    }
  }

  private Long getQWeekId() {
    final var nowDate = LocalDate.now();
    final var year = nowDate.getYear();
    final var weekNumber = QTimeUtils.getWeekNumber(nowDate);
    final var qWeek = qWeekQuery.getByYearAndNumber(year, weekNumber);
    if (qWeek == null) {
      throw new RuntimeException(
          "Impossible to start Rent calculation for (d% year d% week number), because Q-week does not exist.");
    }

    return qWeek.getId();
  }
}
