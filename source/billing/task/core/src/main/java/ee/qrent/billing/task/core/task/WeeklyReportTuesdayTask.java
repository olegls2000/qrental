package ee.qrent.billing.task.core.task;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.request.WeeklyReportTypeIn;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import ee.qrent.common.in.usecase.QTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportTuesdayTask implements QTask {

  private final WeeklyReportCalculationAddUseCase addUseCase;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public Runnable getRunnable() {
    return () -> {
      final WeeklyReportCalculationAddRequest addRequest = new WeeklyReportCalculationAddRequest();
      addRequest.setQWeekId(qWeekQuery.getCurrentWeek().getId());
      addRequest.setType(WeeklyReportTypeIn.TUESDAY_REPORT);
      addUseCase.add(addRequest);
      if (addRequest.hasViolations()) {
        throw new RuntimeException(addRequest.getViolations().toString());
      }
    };
  }

  @Override
  public String getName() {
    return "TUESDAY-BILLING_WEEKLY-REPORT-TASK";
  }
}
