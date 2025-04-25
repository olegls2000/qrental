package ee.qrent.billing.contract.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;

public class AuthorizationDeleteRequest extends AbstractDeleteRequest {
  public AuthorizationDeleteRequest(final Long id) {
    super(id);
  }
}
