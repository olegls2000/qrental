package ee.qrent.billing.report.api.out;

import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.common.out.port.LoadPort;

public interface WeeklyReportCalculationLoadPort extends LoadPort<WeeklyReportCalculation> {
    Long loadLastCalculatedQWeekId();
}
