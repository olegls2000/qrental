package ee.qrental.contract.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class AuthorizationBoltDeleteRequest extends AbstractDeleteRequest {
  public AuthorizationBoltDeleteRequest(final Long id) {
    super(id);
  }
}
