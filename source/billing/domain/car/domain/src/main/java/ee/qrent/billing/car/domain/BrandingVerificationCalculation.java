package ee.qrent.billing.car.domain;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BrandingVerificationCalculation {
  private Long id;
  private LocalDate actionDate;
  private String comment;
  private String type;
  private List<BrandingVerificationCalculationResult> results;
}
