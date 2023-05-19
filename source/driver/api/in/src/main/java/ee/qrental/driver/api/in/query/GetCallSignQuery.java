package ee.qrental.driver.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.CallSignUpdateRequest;
import ee.qrental.driver.api.in.response.CallSignResponse;

public interface GetCallSignQuery extends BaseGetQuery<CallSignUpdateRequest, CallSignResponse> {}
