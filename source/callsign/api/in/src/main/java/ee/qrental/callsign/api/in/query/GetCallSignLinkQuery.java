package ee.qrental.callsign.api.in.query;

import ee.qrental.callsign.api.in.request.CallSignLinkResponse;
import ee.qrental.callsign.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.common.core.in.query.BaseGetQuery;

public interface GetCallSignLinkQuery
    extends BaseGetQuery<CallSignLinkUpdateRequest, CallSignLinkResponse> {

  CallSignLinkResponse getCallSignLinkByDriverId(final Long driverId);
}
