package ee.qrental.contract.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class AuthorizationDeleteRequest extends AbstractDeleteRequest {
  public AuthorizationDeleteRequest(final Long id) {
    super(id);
  }
}
