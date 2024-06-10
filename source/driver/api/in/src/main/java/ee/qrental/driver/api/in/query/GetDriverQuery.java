package ee.qrental.driver.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.response.DriverResponse;

import java.util.List;

public interface GetDriverQuery extends BaseGetQuery<DriverUpdateRequest, DriverResponse> {

    List<DriverResponse> getFriends(final Long driverId);
}
