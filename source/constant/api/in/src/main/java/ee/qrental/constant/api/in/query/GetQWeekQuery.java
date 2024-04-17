package ee.qrental.constant.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public interface GetQWeekQuery extends BaseGetQuery<QWeekUpdateRequest, QWeekResponse> {

  Comparator<QWeekResponse> DEFAULT_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber);
  Comparator<QWeekResponse> REVERSED_COMPARATOR =
      comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber).reversed();

  List<QWeekResponse> getAllByYear(final Integer year);

  QWeekResponse getCurrentWeek();

  List<QWeekResponse> getQWeeksFromPeriodOrdered(
      final Long starQtWeekId, final Long endQWeekId, final Comparator<QWeekResponse> comparator);

  QWeekResponse getByYearAndNumber(final Integer year, final Integer number);

  QWeekResponse getOneBeforeById(final Long qWeekId);

  QWeekResponse getOneAfterById(final Long qWeekId);

  QWeekResponse getFirstWeek();

  List<QWeekResponse> getAllBetweenByIds(final Long startWeekId, final Long endWeekId);

  List<QWeekResponse> getAllBeforeById(final Long qWeekId);

  /**
   * @param qWeekId
   * @return return a QWeeks without a Week, which ID is present in parameter
   */
  List<QWeekResponse> getAllAfterById(final Long qWeekId);
}
