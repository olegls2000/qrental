package ee.qrent.billing.driver.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.driver.api.in.request.CallSignLinkResponse;
import ee.qrent.billing.driver.api.in.request.CallSignLinkUpdateRequest;

import java.util.List;

public interface GetCallSignLinkQuery
    extends BaseGetQuery<CallSignLinkUpdateRequest, CallSignLinkResponse> {

  CallSignLinkResponse getActiveCallSignLinkByDriverId(final Long driverId);

  CallSignLinkResponse getActiveByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  List<CallSignLinkResponse> getActive();

  List<CallSignLinkResponse> getClosed();

  Long getCountActive();

  Long getCountClosed();
}
