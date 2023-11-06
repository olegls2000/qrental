package ee.qrental.constant.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;

import java.util.List;

public interface GetQWeekQuery extends BaseGetQuery<QWeekUpdateRequest, QWeekResponse> {
  List<QWeekResponse> getByYear(final Integer year);

  QWeekResponse getByYearAndNumber(final Integer year, final Integer number);

  List<Integer> getAllYears();
}
