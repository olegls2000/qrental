package ee.qrental.car.api.in.request;

import java.time.LocalDate;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarLinkAddRequest extends AbstractAddRequest {
  private Long carId;
  private Long driverId;
  private LocalDate dateStart = LocalDate.now();
  private LocalDate dateEnd;
  private String comment;
}
