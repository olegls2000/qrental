package ee.qrental.constant.core.service;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.core.mapper.QWeekResponseMapper;
import ee.qrental.constant.core.mapper.QWeekUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QWeekQueryService implements GetQWeekQuery {

  private final QWeekLoadPort loadPort;
  private final QWeekResponseMapper mapper;
  private final QWeekUpdateRequestMapper updateRequestMapper;

  @Override
  public List<QWeekResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber))
        .collect(toList());
  }

  @Override
  public QWeekResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public QWeekUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public List<QWeekResponse> getByYear(final Integer year) {
    return loadPort.loadByYear(year).stream()
        .map(mapper::toResponse)
        .sorted(comparing(QWeekResponse::getNumber))
        .collect(toList());
  }

  @Override
  public QWeekResponse getByYearAndNumber(final Integer year, final Integer number) {
    return mapper.toResponse(loadPort.loadByYearAndNumber(year, number));
  }

  @Override
  public List<Integer> getAllYears() {
    return loadPort.loadYears();
  }
}
