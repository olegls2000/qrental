package ee.qrental.car.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class CarLinkDeleteRequest extends AbstractDeleteRequest {
  public CarLinkDeleteRequest(final Long id) {
    super(id);
  }
}
