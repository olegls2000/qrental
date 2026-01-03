package ee.qrent.billing.report.core.service;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.report.api.in.query.GetWeeklyReportCalculationQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportTypeIn;
import ee.qrent.billing.report.api.in.response.WeeklyReportCalculationResponse;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportCalculationResponseMapper;

import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationQueryService implements GetWeeklyReportCalculationQuery {

  private final WeeklyReportCalculationLoadPort loadPort;
  private final WeeklyReportCalculationResponseMapper mapper;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public List<WeeklyReportCalculationResponse> getAll() {

    return loadPort.loadAll().stream().map(mapper::toResponse)
            .sorted(getActionDateComparator())
            .collect(toList());
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
  public List<WeeklyReportTypeIn> getWeeklyReportTypes() {

    return asList(WeeklyReportTypeIn.values());
  }

  private QWeekResponse getLatestInsuranceCalculatedQWeek() {
    final var lastCalculatedQWeekId = loadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {
      return qWeekQuery.getFirstWeek();
    }
    return qWeekQuery.getById(lastCalculatedQWeekId);
  }

  private Comparator<WeeklyReportCalculationResponse> getActionDateComparator() {
    return (report1, report2) -> {
      final var actionDate1 = report1.getActionDate();
      final var actionDate2 = report2.getActionDate();

      return actionDate2.compareTo(actionDate1);
    };
  }
}
