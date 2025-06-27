package ee.qrent.billing.task.core.service;

import ee.qrent.billing.task.api.in.usecase.TaskRunUseCase;
import ee.qrent.billing.task.core.task.WeeklyReportMondayTask;
import ee.qrent.billing.task.core.task.InsuranceCaseCalculationTask;
import ee.qrent.billing.task.core.task.ObligationCalculationTask;
import ee.qrent.billing.task.core.task.QWeekCreationTask;
import ee.qrent.billing.task.core.task.RentCalculationTask;
import ee.qrent.common.in.usecase.QTaskRunner;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskRunService implements TaskRunUseCase {

  private final QTaskRunner qTaskRunner;
  private final InsuranceCaseCalculationTask insuranceCalculationTask;
  private final ObligationCalculationTask obligationCalculationTask;
  private final QWeekCreationTask qWeekCreationTask;
  private final RentCalculationTask rentCalculationTask;
  private final WeeklyReportMondayTask mondayFinancialReportTask;

  @Override
  public void runQWeekCreationTask() {
    qTaskRunner.run(qWeekCreationTask);
  }

  @Override
  public void runInsuranceCalculationTask() {
    qTaskRunner.run(insuranceCalculationTask);
  }

  @Override
  public void runRentCalculationTask() {
    qTaskRunner.run(rentCalculationTask);
  }

  @Override
  public void runObligationCalculationTask() {
    qTaskRunner.run(obligationCalculationTask);
  }

  @Override
  public void runWeeklyReportMondayTask() {
    qTaskRunner.run(mondayFinancialReportTask);
  }
}
