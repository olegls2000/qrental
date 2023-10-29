package ee.qrental.user.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.user.api.in.request.UserAccountUpdateRequest;
import ee.qrental.user.api.in.response.UserAccountResponse;

import java.util.List;

public interface GetUserAccountQuery
    extends BaseGetQuery<UserAccountUpdateRequest, UserAccountResponse> {
  List<UserAccountResponse> getAllByRoleName(final String roleName);
}
