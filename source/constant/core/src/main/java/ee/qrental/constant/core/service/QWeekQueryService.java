package ee.qrental.constant.core.service;

import ee.qrental.common.utils.QTimeUtils;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.core.mapper.QWeekResponseMapper;
import ee.qrental.constant.core.mapper.QWeekUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static ee.qrental.common.utils.QTimeUtils.getWeekNumber;
import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class QWeekQueryService implements GetQWeekQuery {

  private static final Comparator<QWeekResponse> DEFAULT_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber);
  private static final Comparator<QWeekResponse> REVERSED_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber).reversed();

  private final QWeekLoadPort loadPort;
  private final QWeekResponseMapper mapper;
  private final QWeekUpdateRequestMapper updateRequestMapper;

  @Override
  public List<QWeekResponse> getAll() {

    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(REVERSED_COMPARATOR)
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
        .sorted(REVERSED_COMPARATOR)
        .collect(toList());
  }

  @Override
  public QWeekResponse getCurrentWeek() {
    final var nowDate = LocalDate.now();
    final var year = nowDate.getYear();
    final var number = getWeekNumber(nowDate);
    final var currentQWeek = loadPort.loadByYearAndNumber(year, number);
    if (currentQWeek == null) {
      throw new RuntimeException(
          format("Q Week number: %d for the %d year is missing", number, year));
    }

    return mapper.toResponse(currentQWeek);
  }

  @Override
  public List<QWeekResponse> getQWeeksFromPeriodOrdered(
      final Long starQtWeekId, final Long endQWeekId, final Comparator<QWeekResponse> comparator) {
    final var qWeeks =
        starQtWeekId == null
            ? loadPort.loadAllBeforeById(endQWeekId)
            : loadPort.loadAllBetweenByIds(starQtWeekId, endQWeekId);

    return qWeeks.stream().map(mapper::toResponse).sorted(comparator).collect(toList());
  }

  @Override
  public QWeekResponse getByYearAndNumber(final Integer year, final Integer number) {
    return mapper.toResponse(loadPort.loadByYearAndNumber(year, number));
  }

  @Override
  public QWeekResponse getOneBeforeById(final Long qWeekId) {
    final var qWeek = loadPort.loadById(qWeekId);
    final var qWeekYear = qWeek.getYear();
    final var qWeekNumber = qWeek.getNumber();
    var previousWeekYear = qWeekYear;
    var previousWeekNumber = qWeekNumber - 1;
    if (qWeekYear == 2023 && qWeekNumber == 1) {
      return null;
    }

    if (previousWeekNumber == 0) {
      previousWeekYear = qWeekYear - 1;
      previousWeekNumber = 52;
    }

    return mapper.toResponse(loadPort.loadByYearAndNumber(previousWeekYear, previousWeekNumber));
  }

  @Override
  public QWeekResponse getOneAfterById(final Long qWeekId) {
    final var qWeek = loadPort.loadById(qWeekId);
    final var qWeekYear = qWeek.getYear();
    final var qWeekNumber = qWeek.getNumber();
    var nextWeekYear = qWeekYear;
    var nextWeekNumber = qWeekNumber;

    if (qWeekNumber == 52) {
      nextWeekYear = qWeekYear + 1;
      nextWeekNumber = 1;
    } else {
      nextWeekNumber = qWeekNumber + 1;
    }

    return mapper.toResponse(loadPort.loadByYearAndNumber(nextWeekYear, nextWeekNumber));
  }

  @Override
  public QWeekResponse getFirstWeek() {
    return mapper.toResponse(loadPort.loadByYearAndNumber(2023, 1));
  }

  @Override
  public List<QWeekResponse> getAllBetweenByIdsReversedOrder(final Long startWeekId, final Long endWeekId) {
    return loadPort.loadAllBetweenByIds(startWeekId, endWeekId).stream()
        .map(mapper::toResponse)
        .sorted(REVERSED_COMPARATOR)
        .collect(toList());
  }
  @Override
  public List<QWeekResponse> getAllBetweenByIdsDefaultOrder(final Long startWeekId, final Long endWeekId) {
    return loadPort.loadAllBetweenByIds(startWeekId, endWeekId).stream()
            .map(mapper::toResponse)
            .sorted(DEFAULT_COMPARATOR)
            .collect(toList());
  }

  @Override
  public List<QWeekResponse> getAllBeforeById(final Long qWeekId) {
    return loadPort.loadAllBeforeById(qWeekId).stream()
        .map(mapper::toResponse)
        .sorted(REVERSED_COMPARATOR)
        .collect(toList());
  }

  @Override
  public List<QWeekResponse> getAllAfterById(final Long id) {
    if (id == null) {
      return getAll();
    }

    return loadPort.loadAllAfterById(id).stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }
}
