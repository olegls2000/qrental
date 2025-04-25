package ee.qrent.billing.firm.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public class FirmDeleteRequest extends AbstractDeleteRequest {
  public FirmDeleteRequest(final Long id) {
    super(id);
  }
}
