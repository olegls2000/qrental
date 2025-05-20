package ee.qrent.billing.task.core;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportAddRequest;
import ee.qrent.billing.report.api.in.request.WeeklyReportType;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportAddUseCase;
import ee.qrent.common.in.usecase.QTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportMondayTask implements QTask {

  private final WeeklyReportAddUseCase addUseCase;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public Runnable getRunnable() {
    final WeeklyReportAddRequest addRequest =
        WeeklyReportAddRequest.builder()
            .qWeekId(qWeekQuery.getCurrentWeek().getId())
            .type(WeeklyReportType.MONDAY_REPORT)
            .build();

    return () -> addUseCase.add(addRequest);
  }

  @Override
  public String getName() {
    return "MONDAY-BILLING_WEEKLY-REPORT-TASK";
  }
}
