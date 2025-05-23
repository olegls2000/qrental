package ee.qrent.billing.report.core.service;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;

import ee.qrent.billing.report.api.in.query.GetWeeklyReportQuery;
import ee.qrent.billing.report.api.in.response.WeeklyReportResponse;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportResponseMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportQueryService implements GetWeeklyReportQuery {

  private final WeeklyReportLoadPort loadPort;
  private final WeeklyReportResponseMapper mapper;

  @Override
  public List<WeeklyReportResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(getInvoiceYearAndWeekComparator())
        .collect(toList());
  }

  @Override
  public WeeklyReportResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  // TODO get rid of this method
  @Override
  public Object getUpdateRequestById(Long id) {
    return null;
  }

  @Override
  public List<WeeklyReportResponse> getAllByCalculationId(Long calculationId) {
    return loadPort.loadAllByCalculationId(calculationId).stream()
        .map(mapper::toResponse)
        .sorted(getInvoiceYearAndWeekComparator())
        .toList();
  }

  private Comparator<WeeklyReportResponse> getInvoiceYearAndWeekComparator() {
    return (report1, report2) -> {
      final var yearComparison = report1.getWeekYear().compareTo(report1.getWeekYear());
      if (yearComparison != 0) {
        return yearComparison;
      }

      return report2.getWeekNumber().compareTo(report1.getWeekNumber());
    };
  }
}
