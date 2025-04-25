package ee.qrent.billing.user.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;

public class RoleDeleteRequest extends AbstractDeleteRequest {
  public RoleDeleteRequest(final Long id) {
    super(id);
  }
}
