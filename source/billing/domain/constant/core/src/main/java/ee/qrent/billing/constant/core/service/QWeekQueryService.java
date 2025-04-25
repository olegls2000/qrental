package ee.qrent.billing.constant.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.request.QWeekAddRequest;
import ee.qrent.billing.constant.api.in.request.QWeekUpdateRequest;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.constant.api.out.QWeekLoadPort;
import ee.qrent.billing.constant.core.mapper.QWeekResponseMapper;
import ee.qrent.billing.constant.core.mapper.QWeekUpdateRequestMapper;
import lombok.AllArgsConstructor;
import org.threeten.extra.YearWeek;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.WEEKS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class QWeekQueryService implements GetQWeekQuery {

  private static final Comparator<QWeekResponse> DEFAULT_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber);
  private static final Comparator<QWeekResponse> REVERSED_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber).reversed();

  private static final Integer DEFAULT_LAST_WEEK_NUMBER = 10;

  private final QWeekLoadPort loadPort;
  private final QWeekResponseMapper mapper;
  private final QWeekUpdateRequestMapper updateRequestMapper;
  private final QDateTime qDateTime;
  private final QWeekUseCaseService qWeekUseCaseService;

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
  public List<QWeekResponse> getLastTenWeeksFromToday() {
    final var nowDate = qDateTime.getToday();
    final var dateDefaultWeeksAgo = nowDate.minusWeeks(DEFAULT_LAST_WEEK_NUMBER);
    final var qWeek = getByDate(dateDefaultWeeksAgo);

    return getAllAfterById(qWeek.getId());
  }

  @Override
  public QWeekResponse getCurrentWeek() {
    final var nowDate = qDateTime.getToday();

    return getByDate(nowDate);
  }

  @Override
  public QWeekResponse getByDate(final LocalDate date) {
    final var yearWeek =  YearWeek.from( date );

    final var year = yearWeek.getYear();
    final var number = yearWeek.getWeek();
    final var qWeek = loadPort.loadByYearAndNumber(year, number);
    if (qWeek == null) {
      final var qWeekAddRequest = new QWeekAddRequest();
      qWeekAddRequest.setWeekDate(date);
      qWeekUseCaseService.add(qWeekAddRequest);
    }

    return mapper.toResponse(qWeek);
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

    final var date = qWeek.getStart().minus(1, WEEKS);
    final var qWeekAddRequest = new QWeekAddRequest();
    qWeekAddRequest.setWeekDate(date);
    qWeekUseCaseService.add(qWeekAddRequest);

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
  public List<QWeekResponse> getAllBetweenByIdsReversedOrder(
      final Long startWeekId, final Long endWeekId) {
    return loadPort.loadAllBetweenByIds(startWeekId, endWeekId).stream()
        .map(mapper::toResponse)
        .sorted(REVERSED_COMPARATOR)
        .collect(toList());
  }

  @Override
  public List<QWeekResponse> getAllBetweenByIdsDefaultOrder(
      final Long startWeekId, final Long endWeekId) {
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
