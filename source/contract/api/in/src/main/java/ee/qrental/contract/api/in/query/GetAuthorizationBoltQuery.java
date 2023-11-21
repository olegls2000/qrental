package ee.qrental.contract.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.contract.api.in.request.AuthorizationBoltUpdateRequest;
import ee.qrental.contract.api.in.response.AuthorizationBoltResponse;

public interface GetAuthorizationBoltQuery extends BaseGetQuery<AuthorizationBoltUpdateRequest,
        AuthorizationBoltResponse> {
}
