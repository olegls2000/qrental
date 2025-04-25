package ee.qrent.billing.task.core;

import ee.qrent.billing.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrent.billing.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.common.in.usecase.QTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationTask implements QTask {

  private final ObligationCalculationAddUseCase addUseCase;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public Runnable getRunnable() {
    return () -> {
      final var addRequest = new ObligationCalculationAddRequest();
      addRequest.setComment("Automatically triggered obligation calculation");
      addRequest.setQWeekId(qWeekQuery.getCurrentWeek().getId());
      addUseCase.add(addRequest);
    };
  }

  @Override
  public String getName() {
    return "OBLIGATION-CALCULATION-TASK";
  }
}
