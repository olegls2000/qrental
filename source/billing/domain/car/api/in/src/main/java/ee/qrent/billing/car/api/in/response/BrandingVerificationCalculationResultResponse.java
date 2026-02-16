package ee.qrent.billing.car.api.in.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandingVerificationCalculationResultResponse {
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
