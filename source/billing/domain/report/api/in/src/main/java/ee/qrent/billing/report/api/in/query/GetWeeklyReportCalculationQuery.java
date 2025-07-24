package ee.qrent.billing.report.api.in.query;

import ee.qrent.billing.report.api.in.request.WeeklyReportTypeIn;
import ee.qrent.billing.report.api.in.response.WeeklyReportCalculationResponse;
import ee.qrent.common.in.query.BaseGetQuery;

import java.util.List;

// TODO get rid of Object
public interface GetWeeklyReportCalculationQuery
    extends BaseGetQuery<Object, WeeklyReportCalculationResponse> {
  Long getLastCalculatedQWeekId();

  List<WeeklyReportTypeIn> getWeeklyReportTypes();
}
