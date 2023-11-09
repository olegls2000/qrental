package ee.qrental.constant.core.service;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.core.mapper.QWeekResponseMapper;
import ee.qrental.constant.core.mapper.QWeekUpdateRequestMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QWeekQueryService implements GetQWeekQuery {

  private static final Comparator<QWeekResponse> DEFAULT_COMPARATOR = comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber);
  private static final Comparator<QWeekResponse> REVERSED_COMPARATOR = comparing(QWeekResponse::getYear).reversed()
          .thenComparing(QWeekResponse::getNumber).reversed();

  private final QWeekLoadPort loadPort;
  private final QWeekResponseMapper mapper;
  private final QWeekUpdateRequestMapper updateRequestMapper;

  @Override
  public List<QWeekResponse> getAll() {

    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
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
  public List<QWeekResponse> getAllByYear(final Integer year) {
    return loadPort.loadByYear(year).stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public Map<Integer, List<QWeekResponse>> getAllGroupedByYear() {
    return getAllYears().stream().collect(toMap(identity(), this::getAllByYear));
  }

  @Override
  public QWeekResponse getByYearAndNumber(final Integer year, final Integer number) {
    return mapper.toResponse(loadPort.loadByYearAndNumber(year, number));
  }

  @Override
  public List<Integer> getAllYears() {
    return loadPort.loadYears();
  }

  @Override
  public List<QWeekResponse> getAllBeforeById(final Long id) {
    return loadPort.loadAllBeforeById(id).stream()
            .map(mapper::toResponse)
            .sorted(REVERSED_COMPARATOR)
            .collect(toList());
  }
}
