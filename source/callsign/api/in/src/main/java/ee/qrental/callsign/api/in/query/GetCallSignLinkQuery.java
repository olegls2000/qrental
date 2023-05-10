package ee.qrental.callsign.api.in.query;

import ee.qrental.callsign.api.in.request.CallSignLinkResponse;
import ee.qrental.callsign.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.common.core.in.query.BaseGetQuery;

import java.time.LocalDate;

public interface GetCallSignLinkQuery
    extends BaseGetQuery<CallSignLinkUpdateRequest, CallSignLinkResponse> {

  CallSignLinkResponse getActiveCallSignLinkByDriverId(final Long driverId);
  CallSignLinkResponse getCallSignLinkByDriverIdAndDate(final Long driverId, final LocalDate date);
}
