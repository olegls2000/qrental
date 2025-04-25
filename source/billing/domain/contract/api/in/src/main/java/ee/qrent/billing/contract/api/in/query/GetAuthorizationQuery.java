package ee.qrent.billing.contract.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.contract.api.in.request.AuthorizationUpdateRequest;
import ee.qrent.billing.contract.api.in.response.AuthorizationResponse;

public interface GetAuthorizationQuery
    extends BaseGetQuery<AuthorizationUpdateRequest, AuthorizationResponse> {

  AuthorizationResponse getLatestByDriverId(final Long driverId);
}
