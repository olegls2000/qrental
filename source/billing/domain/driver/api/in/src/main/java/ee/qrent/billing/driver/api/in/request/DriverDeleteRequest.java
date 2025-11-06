package ee.qrent.billing.driver.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DriverDeleteRequest extends AbstractDeleteRequest {
  public DriverDeleteRequest(final Long id) {
    super(id);
  }
}
