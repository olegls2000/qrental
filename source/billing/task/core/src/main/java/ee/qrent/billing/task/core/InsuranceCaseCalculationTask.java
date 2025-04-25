package ee.qrent.billing.task.core;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrent.common.in.usecase.QTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseCalculationTask implements QTask {

  private final InsuranceCalculationAddUseCase addUseCase;
  private final GetQWeekQuery getQWeekQuery;

  @Override
  public Runnable getRunnable() {
    return () -> {
      final var addRequest = new InsuranceCalculationAddRequest();
      addRequest.setComment("Automatically triggered Insurance Balance Calculation");
      addRequest.setQWeekId(getQWeekQuery.getCurrentWeek().getId());
      addUseCase.add(addRequest);
    };
  }

  @Override
  public String getName() {
    return "INSURANCE-CALCULATION-TASK";
  }
}
