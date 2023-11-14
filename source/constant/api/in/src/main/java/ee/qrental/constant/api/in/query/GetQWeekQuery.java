package ee.qrental.constant.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import java.util.List;

public interface GetQWeekQuery extends BaseGetQuery<QWeekUpdateRequest, QWeekResponse> {

  List<QWeekResponse> getAllByYear(final Integer year);

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
;
}
