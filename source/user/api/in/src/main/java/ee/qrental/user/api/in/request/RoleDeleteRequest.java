package ee.qrental.user.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class RoleDeleteRequest extends AbstractDeleteRequest {
  public RoleDeleteRequest(final Long id) {
    super(id);
  }
}
