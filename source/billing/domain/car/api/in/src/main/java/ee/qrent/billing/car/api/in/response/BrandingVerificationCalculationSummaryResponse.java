package ee.qrent.billing.car.api.in.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandingVerificationCalculationSummaryResponse {
  private Long calculationId;
  private LocalDate actionDate;
  private String type;
  private long verificationsCount;
}
