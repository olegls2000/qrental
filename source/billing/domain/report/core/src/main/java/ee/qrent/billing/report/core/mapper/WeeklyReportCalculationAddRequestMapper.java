package ee.qrent.billing.report.core.mapper;

import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.in.time.QDateTime;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class WeeklyReportCalculationAddRequestMapper
    implements AddRequestMapper<WeeklyReportCalculationAddRequest, WeeklyReportCalculation> {

  private final QDateTime qDateTime;

  public WeeklyReportCalculation toDomain(final WeeklyReportCalculationAddRequest request) {

    return WeeklyReportCalculation.builder()
        .actionDate(qDateTime.getToday())
        .qWeekId(request.getQWeekId())
        .reportTransactionLinks(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
