package ee.qrent.billing.report.api.out;

import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.common.out.port.LoadPort;
import java.util.List;

public interface WeeklyReportLoadPort extends LoadPort<WeeklyReport> {

  List<WeeklyReport> loadAllByCalculationId(final Long calculationId);
}
