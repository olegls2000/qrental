package ee.qrent.billing.report.core.mapper;

import static java.lang.String.format;

import ee.qrent.billing.report.api.in.request.WeeklyReportTypeIn;
import ee.qrent.billing.report.api.in.response.WeeklyReportCalculationResponse;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationResponseMapper
    implements ResponseMapper<WeeklyReportCalculationResponse, WeeklyReportCalculation> {

  private final GetQWeekQuery qWeekQuery;

  @Override
  public WeeklyReportCalculationResponse toResponse(final WeeklyReportCalculation domain) {
    if (domain == null) {

      return null;
    }
    final var reportQWeek = qWeekQuery.getById(domain.getQWeekId());

    return WeeklyReportCalculationResponse.builder()
        .id(domain.getId())
        .type(WeeklyReportTypeIn.valueOf(domain.getReportType().name()).getLabel())
        .reportsCount(domain.getReportTransactionLinks().size())
        .year(reportQWeek.getYear())
        .weekNumber(reportQWeek.getNumber())
        .dateStart(reportQWeek.getStart())
        .dateEnd(reportQWeek.getEnd())
        .actionDate(domain.getActionDate())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final WeeklyReportCalculation domain) {
    return format("Weekly Report Calculation was done at: %s", domain.getActionDate());
  }
}
