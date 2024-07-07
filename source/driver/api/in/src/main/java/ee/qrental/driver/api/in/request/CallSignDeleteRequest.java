package ee.qrental.driver.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public class CallSignDeleteRequest extends AbstractDeleteRequest {
  public CallSignDeleteRequest(final Long id) {
    super(id);
  }
}
