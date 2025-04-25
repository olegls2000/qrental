package ee.qrent.billing.constant.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.constant.api.in.request.QWeekUpdateRequest;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public interface GetQWeekQuery extends BaseGetQuery<QWeekUpdateRequest, QWeekResponse> {

  Comparator<QWeekResponse> DEFAULT_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber);
  Comparator<QWeekResponse> REVERSED_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber).reversed();

  List<QWeekResponse> getAllByYear(final Integer year);

  List<QWeekResponse> getLastTenWeeksFromToday();

  QWeekResponse getCurrentWeek();

  QWeekResponse getByDate(final LocalDate date);

  List<QWeekResponse> getQWeeksFromPeriodOrdered(
      final Long starQtWeekId, final Long endQWeekId, final Comparator<QWeekResponse> comparator);

  QWeekResponse getByYearAndNumber(final Integer year, final Integer number);

  QWeekResponse getOneBeforeById(final Long qWeekId);

  QWeekResponse getOneAfterById(final Long qWeekId);

  QWeekResponse getFirstWeek();

  /**
   * @param startWeekId, endWeekId
   * @return return a list of weeks including start and endWeek: [start week, end week]
   */
  List<QWeekResponse> getAllBetweenByIdsReversedOrder(final Long startWeekId, final Long endWeekId);

  List<QWeekResponse> getAllBetweenByIdsDefaultOrder(final Long startWeekId, final Long endWeekId);

  List<QWeekResponse> getAllBeforeById(final Long qWeekId);

  /**
   * @param qWeekId
   * @return return a QWeeks without a Week, which ID is present in parameter
   */
  List<QWeekResponse> getAllAfterById(final Long qWeekId);
}
