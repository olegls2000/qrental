package ee.qrent.billing.task.core;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.request.WeeklyReportType;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import ee.qrent.common.in.usecase.QTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportMondayTask implements QTask {

  private final WeeklyReportCalculationAddUseCase addUseCase;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public Runnable getRunnable() {
    return () -> {
      final WeeklyReportCalculationAddRequest addRequest =
          WeeklyReportCalculationAddRequest.builder()
              .qWeekId(qWeekQuery.getCurrentWeek().getId())
              .type(WeeklyReportType.MONDAY_REPORT)
              .build();
      addUseCase.add(addRequest);
      if (addRequest.hasViolations()) {
        throw new RuntimeException(addRequest.getViolations().toString());
      }
    };
  }

  @Override
  public String getName() {
    return "MONDAY-BILLING_WEEKLY-REPORT-TASK";
  }
}
