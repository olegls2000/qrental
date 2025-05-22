package ee.qrent.billing.report.api.in.query;

import ee.qrent.billing.report.api.in.response.WeeklyReportResponse;
import ee.qrent.common.in.query.BaseGetQuery;
import java.util.List;

//TODO get rid of Object
public interface GetWeeklyReportQuery extends BaseGetQuery<Object, WeeklyReportResponse> {

  List<WeeklyReportResponse> getAllByCalculationId(final Long calculationId);
}
