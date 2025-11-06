package ee.qrent.billing.user.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAccountDeleteRequest extends AbstractDeleteRequest {
  public UserAccountDeleteRequest(final Long id) {
    super(id);
  }
}
