package ee.qrent.billing.driver.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public class DriverDeleteRequest extends AbstractDeleteRequest {
  public DriverDeleteRequest(final Long id) {
    super(id);
  }
}
