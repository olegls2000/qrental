package ee.qrent.billing.car.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BrandingVerificationCalculationResult {
  private Long id;
  private BrandingVerification verification;
}
