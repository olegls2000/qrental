package ee.qrent.billing.task.core;

import ee.qrent.billing.constant.api.in.request.QWeekAddRequest;
import ee.qrent.billing.constant.api.in.usecase.QWeekAddUseCase;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.usecase.QTask;
import ee.qrent.common.in.usecase.RunTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class QWeekCreationTask implements QTask {

  private final QWeekAddUseCase qWeekAddUseCase;
  private final QDateTime qDateTime;

  @Override
  public Runnable getRunnable() {
    return () -> {
      final var nowDate = qDateTime.getToday();
      final var addRequest = new QWeekAddRequest();
      addRequest.setWeekDate(nowDate);
      qWeekAddUseCase.add(addRequest);
    };
  }

  @Override
  public String getName() {
    return "QWEEK-CREATION-TASK";
  }
}
