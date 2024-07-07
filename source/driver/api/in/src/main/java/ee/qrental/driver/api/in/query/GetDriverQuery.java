package ee.qrental.driver.api.in.query;


import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.api.in.response.FriendshipResponse;

import java.util.List;

public interface GetDriverQuery extends BaseGetQuery<DriverUpdateRequest, DriverResponse> {
    List<FriendshipResponse> getFriendships(final Long driverId);
}
