package ee.qrent.billing.driver.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CallSignDeleteRequest extends AbstractDeleteRequest {
  public CallSignDeleteRequest(final Long id) {
    super(id);
  }
}
