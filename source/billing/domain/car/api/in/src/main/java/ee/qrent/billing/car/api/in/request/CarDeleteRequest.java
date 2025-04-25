package ee.qrent.billing.car.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;

public class CarDeleteRequest extends AbstractDeleteRequest {
  public CarDeleteRequest(final Long id) {
    super(id);
  }
}
