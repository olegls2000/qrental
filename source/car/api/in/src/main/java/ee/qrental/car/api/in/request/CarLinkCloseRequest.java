package ee.qrental.car.api.in.request;

import ee.qrent.common.in.request.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarLinkCloseRequest extends AbstractRequest {
  private Long id;
  private Long driverId;
}
