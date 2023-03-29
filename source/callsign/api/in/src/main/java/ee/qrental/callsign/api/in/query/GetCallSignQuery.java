package ee.qrental.callsign.api.in.query;

import ee.qrental.callsign.api.in.request.CallSignUpdateRequest;
import ee.qrental.callsign.api.in.response.CallSignResponse;
import ee.qrental.common.core.in.query.BaseGetQuery;

public interface GetCallSignQuery extends BaseGetQuery<CallSignUpdateRequest, CallSignResponse> {}
