package ee.qrental.driver.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class DriverDeleteRequest extends AbstractDeleteRequest {
  public DriverDeleteRequest(final Long id) {
    super(id);
  }
}
