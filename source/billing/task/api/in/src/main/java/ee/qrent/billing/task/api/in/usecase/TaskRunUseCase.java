package ee.qrent.billing.task.api.in.usecase;


public interface TaskRunUseCase {

  void runQWeekCreationTask();

  void runInsuranceCalculationTask();

  void runRentCalculationTask();

  void runObligationCalculationTask();

  void runWeeklyReportMondayTask();
}
