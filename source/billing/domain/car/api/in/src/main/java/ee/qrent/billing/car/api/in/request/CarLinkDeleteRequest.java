package ee.qrent.billing.car.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CarLinkDeleteRequest extends AbstractDeleteRequest {
  public CarLinkDeleteRequest(final Long id) {
    super(id);
  }
}
