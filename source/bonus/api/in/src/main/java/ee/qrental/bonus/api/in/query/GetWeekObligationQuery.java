package ee.qrental.bonus.api.in.query;


import ee.qrental.bonus.api.in.request.WeekObligationUpdateRequest;
import ee.qrental.bonus.api.in.response.WeekObligationResponse;
import ee.qrental.common.core.in.query.BaseGetQuery;

public interface GetWeekObligationQuery extends BaseGetQuery<WeekObligationUpdateRequest, WeekObligationResponse> {
}
