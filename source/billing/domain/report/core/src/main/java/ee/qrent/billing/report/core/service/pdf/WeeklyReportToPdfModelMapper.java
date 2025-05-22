package ee.qrent.billing.report.core.service.pdf;


import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.domain.WeeklyReport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportToPdfModelMapper {

  private final WeeklyReportLoadPort weeklyReportLoadPort;
  private final GetQWeekQuery qWeekQuery;

  public WeeklyReportPdfModel getPdfModel(final WeeklyReport report) {

    return WeeklyReportPdfModel.builder()
        // TODO add mapping
        .build();
  }
}
