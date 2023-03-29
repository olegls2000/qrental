package ee.qrental.callsign.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class CallSignDeleteRequest extends AbstractDeleteRequest {
  public CallSignDeleteRequest(final Long id) {
    super(id);
  }
}
