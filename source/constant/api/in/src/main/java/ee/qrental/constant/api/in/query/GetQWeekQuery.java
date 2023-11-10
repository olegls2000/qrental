package ee.qrental.constant.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;

import java.util.List;
import java.util.Map;

public interface GetQWeekQuery extends BaseGetQuery<QWeekUpdateRequest, QWeekResponse> {
  Map<Integer, List<QWeekResponse>> getAllGroupedByYear();

  List<QWeekResponse> getAllByYear(final Integer year);

  QWeekResponse getByYearAndNumber(final Integer year, final Integer number);

  QWeekResponse getOneWeekBefore(final Long qWeekId);

  List<Integer> getAllYears();

  /**
   * @param qWeekId
   * @return return a List of QWeeks without a Week, which ID is present in parameter
   */
  List<QWeekResponse> getAllBeforeById(final Long qWeekId);

  /**
   * @param qWeekId
   * @return return a QWeeks without a Week, which ID is present in parameter
   */
  List<QWeekResponse> getAllAfterById(final Long qWeekId);
}
