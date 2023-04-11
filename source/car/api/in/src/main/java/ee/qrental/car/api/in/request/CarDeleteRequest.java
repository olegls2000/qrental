package ee.qrental.car.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class CarDeleteRequest extends AbstractDeleteRequest {
  public CarDeleteRequest(final Long id) {
    super(id);
  }
}
