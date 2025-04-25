package ee.qrent.billing.task.core;

import ee.qrent.common.in.usecase.RunTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class BillingTaskScheduler {

  private final RunTaskUseCase runTaskUseCase;
  private final InsuranceCaseCalculationTask insuranceCalculationTask;
  private final ObligationCalculationTask obligationCalculationTask;
  private final QWeekCreationTask qWeekCreationTask;
  private final RentCalculationTask rentCalculationTask;

  // seconds minutes hours day-of-month month day-of-week
  //   0       0      8        *         *        ?
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.
  // For example, 25 10 8 * * ?, means that the task is executed at 08:10:25 every day.

  @Scheduled(cron = "0 1 0 * * MON")
  public void scheduleQWeekCreationTask() {
    runTaskUseCase.run(qWeekCreationTask);
  }

  @Scheduled(cron = "0 6 0 * * MON")
  public void scheduleInsuranceCalculationTask() {
    runTaskUseCase.run(insuranceCalculationTask);
  }

  @Scheduled(cron = "0 11 0 * * MON")
  public void scheduleRentCalculationTask() {
    runTaskUseCase.run(rentCalculationTask);
  }

  @Scheduled(cron = "0 16 0 * * MON")
  public void scheduleObligationCalculationTask() {
    runTaskUseCase.run(obligationCalculationTask);
  }
}
