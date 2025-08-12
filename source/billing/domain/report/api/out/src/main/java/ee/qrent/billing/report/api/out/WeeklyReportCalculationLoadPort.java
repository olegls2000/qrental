package ee.qrent.billing.report.api.out;

import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.common.out.port.LoadPort;

public interface WeeklyReportCalculationLoadPort extends LoadPort<WeeklyReportCalculation> {
  Long loadLastCalculatedQWeekId();

  WeeklyReportCalculation loadByQWeekIdAndReportType(
      final Long qWeekId, final WeeklyReportType reportType);
}
