package ee.qrental.driver.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.response.DriverResponse;

public interface GetDriverQuery extends BaseGetQuery<DriverUpdateRequest, DriverResponse> {}
