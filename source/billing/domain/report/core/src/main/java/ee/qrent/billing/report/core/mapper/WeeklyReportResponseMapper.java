package ee.qrent.billing.report.core.mapper;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import ee.qrent.billing.report.api.in.response.WeeklyReportResponse;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.invoice.api.in.response.InvoiceImmutableResponse;
import ee.qrent.billing.invoice.api.in.response.InvoiceResponse;
import ee.qrent.billing.invoice.domain.Invoice;
import ee.qrent.billing.invoice.domain.InvoiceItem;
import java.util.HashMap;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportResponseMapper
    implements ResponseMapper<WeeklyReportResponse, WeeklyReport> {

  private final GetQWeekQuery qWeekQuery;

  @Override
  public WeeklyReportResponse toResponse(final WeeklyReport domain) {
    final var qWeek = qWeekQuery.getById(domain.getQWeekId());
    return WeeklyReportResponse.builder()
        .id(domain.getId())
        // TODO add mapping
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final WeeklyReport domain) {
    // TODO add mapping
    return null;
  }
}
