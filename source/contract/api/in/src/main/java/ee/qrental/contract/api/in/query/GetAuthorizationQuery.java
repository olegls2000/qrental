package ee.qrental.contract.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.contract.api.in.request.AuthorizationUpdateRequest;
import ee.qrental.contract.api.in.response.AuthorizationResponse;

public interface GetAuthorizationQuery extends BaseGetQuery<AuthorizationUpdateRequest,
        AuthorizationResponse> {
}
