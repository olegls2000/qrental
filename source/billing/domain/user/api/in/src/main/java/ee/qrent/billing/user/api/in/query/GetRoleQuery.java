package ee.qrent.billing.user.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.user.api.in.request.RoleUpdateRequest;
import ee.qrent.billing.user.api.in.response.RoleResponse;

public interface GetRoleQuery extends BaseGetQuery<RoleUpdateRequest, RoleResponse> {}
