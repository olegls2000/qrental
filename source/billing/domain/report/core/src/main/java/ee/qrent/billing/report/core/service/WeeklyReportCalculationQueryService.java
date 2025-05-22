package ee.qrent.billing.report.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.report.api.in.query.GetWeeklyReportCalculationQuery;
import ee.qrent.billing.report.api.in.response.WeeklyReportCalculationResponse;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportCalculationResponseMapper;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationQueryService implements GetWeeklyReportCalculationQuery {

  private final WeeklyReportCalculationLoadPort loadPort;
  private final WeeklyReportCalculationResponseMapper responseMapper;

  @Override
  public List<WeeklyReportCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public WeeklyReportCalculationResponse getById(final Long id) {
    return responseMapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    return responseMapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public Object getUpdateRequestById(final Long id) {
    return null;
  }
}
