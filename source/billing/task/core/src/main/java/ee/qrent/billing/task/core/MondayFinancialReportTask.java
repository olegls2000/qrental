package ee.qrent.billing.task.core;

import ee.qrent.common.in.usecase.QTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MondayFinancialReportTask implements QTask {

  @Override
  public Runnable getRunnable() {
    return () -> {

    };
  }

  @Override
  public String getName() {
    return "FIRST-FINANCIAL-REPORT-TASK";
  }
}
