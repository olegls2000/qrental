package ee.qrent.billing.driver.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.driver.api.in.request.DriverUpdateRequest;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.driver.api.in.response.FriendshipResponse;

import java.util.List;

public interface GetDriverQuery extends BaseGetQuery<DriverUpdateRequest, DriverResponse> {
  List<FriendshipResponse> getFriendships(final Long driverId);

  List<DriverResponse> getDriversWithZeroMatchCountForLatestCalculation();
}
