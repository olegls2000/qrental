package ee.qrental.driver.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.CallSignLinkResponse;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import java.time.LocalDate;

public interface GetCallSignLinkQuery
    extends BaseGetQuery<CallSignLinkUpdateRequest, CallSignLinkResponse> {

  CallSignLinkResponse getActiveCallSignLinkByDriverId(final Long driverId);

  CallSignLinkResponse getCallSignLinkByDriverIdAndDate(final Long driverId, final LocalDate date);
}
