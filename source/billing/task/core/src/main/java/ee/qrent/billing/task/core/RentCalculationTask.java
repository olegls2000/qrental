package ee.qrent.billing.task.core;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import ee.qrent.common.in.usecase.QTask;
import ee.qrent.common.in.usecase.RunTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class RentCalculationTask implements QTask {

  private final RentCalculationAddUseCase addUseCase;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public Runnable getRunnable() {
    return () -> {
      final var addRequest = new RentCalculationAddRequest();
      addRequest.setComment("Automatically triggered Rent Calculation");
      addRequest.setQWeekId(qWeekQuery.getCurrentWeek().getId());
      addUseCase.add(addRequest);
    };
  }

  @Override
  public String getName() {
    return "RENT-CALCULATION-TASK";
  }
}
