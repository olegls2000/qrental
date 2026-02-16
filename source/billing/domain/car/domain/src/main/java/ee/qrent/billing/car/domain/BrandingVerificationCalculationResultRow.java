package ee.qrent.billing.car.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BrandingVerificationCalculationResultRow {
  private Long calculationId;
  private LocalDate actionDate;
  private String comment;
  private String type;
  private Long verificationId;
  private LocalDate verificationDate;
  private Long driverId;
  private String driverFirstName;
  private String driverLastName;
  private Long carId;
  private String carRegNumber;
  private Long carLinkId;
  private LocalDate carLinkStartDate;
  private LocalDate brandingExpirationDate;
}
