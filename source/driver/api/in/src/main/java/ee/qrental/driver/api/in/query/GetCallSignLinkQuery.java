package ee.qrental.driver.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.CallSignLinkResponse;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import java.time.LocalDate;
import java.util.List;

public interface GetCallSignLinkQuery
    extends BaseGetQuery<CallSignLinkUpdateRequest, CallSignLinkResponse> {

  CallSignLinkResponse getActiveCallSignLinkByDriverId(final Long driverId);

  List<CallSignLinkResponse> getCallSignLinksByDriverId(final Long driverId);

  CallSignLinkResponse getCallSignLinkByDriverIdAndDate(final Long driverId, final LocalDate date);

  List<CallSignLinkResponse> getActive();

  List<CallSignLinkResponse> getClosed();

  Long getCountActive();

  Long getCountClosed();
}
