package ee.qrent.billing.car.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BrandingVerificationCalculationSummaryRow {
  private Long calculationId;
  private LocalDate actionDate;
  private String type;
  private Long verificationsCount;
}
