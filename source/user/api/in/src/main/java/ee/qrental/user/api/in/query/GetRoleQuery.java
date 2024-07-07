package ee.qrental.user.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.user.api.in.request.RoleUpdateRequest;
import ee.qrental.user.api.in.response.RoleResponse;

public interface GetRoleQuery extends BaseGetQuery<RoleUpdateRequest, RoleResponse> {}
