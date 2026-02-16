package ee.qrent.billing.car.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BrandingVerificationCalculationAddRequest extends AbstractAddRequest {
  private LocalDate date = LocalDate.now();
  private String type = "branding notification";
  private LocalDate actionDate = LocalDate.now();
  private String comment;
}
