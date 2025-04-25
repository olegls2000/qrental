package ee.qrent.billing.car.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class CarLink {
  private Long id;
  private Long carId;
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
