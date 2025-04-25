package ee.qrent.billing.user.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public class UserAccountDeleteRequest extends AbstractDeleteRequest {
  public UserAccountDeleteRequest(final Long id) {
    super(id);
  }
}
