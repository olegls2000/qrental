package ee.qrent.billing.car.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BrandingVerification {
  private Long id;
  private LocalDate date;
  private Long driverId;
  private Long carId;
  private Long carLinkId;
  private LocalDate brandingExpirationDate;
}
