package ee.qrent.billing.report.core.service;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.report.api.in.query.GetWeeklyReportCalculationQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportType;
import ee.qrent.billing.report.api.in.response.WeeklyReportCalculationResponse;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportCalculationResponseMapper;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationQueryService implements GetWeeklyReportCalculationQuery {

  private final WeeklyReportCalculationLoadPort loadPort;
  private final WeeklyReportCalculationResponseMapper mapper;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public List<WeeklyReportCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public WeeklyReportCalculationResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  // TODO get rid of this method
  @Override
  public Object getUpdateRequestById(final Long id) {
    return null;
  }

  @Override
  public Long getLastCalculatedQWeekId() {
    final var lastInsuranceCalculatedQWeek = getLatestInsuranceCalculatedQWeek();

    return lastInsuranceCalculatedQWeek.getId();
  }

  @Override
  public List<WeeklyReportType> getWeeklyReportTypes() {

    return asList(WeeklyReportType.values());
  }

  private QWeekResponse getLatestInsuranceCalculatedQWeek() {
    final var lastCalculatedQWeekId = loadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {
      return qWeekQuery.getFirstWeek();
    }
    return qWeekQuery.getById(lastCalculatedQWeekId);
  }
}
