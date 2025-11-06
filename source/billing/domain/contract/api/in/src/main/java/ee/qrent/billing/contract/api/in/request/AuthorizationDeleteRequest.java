package ee.qrent.billing.contract.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorizationDeleteRequest extends AbstractDeleteRequest {
  public AuthorizationDeleteRequest(final Long id) {
    super(id);
  }
}
