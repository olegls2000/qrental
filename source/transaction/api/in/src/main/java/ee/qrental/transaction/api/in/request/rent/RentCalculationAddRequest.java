package ee.qrental.transaction.api.in.request.rent;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RentCalculationAddRequest extends AbstractAddRequest {
  private LocalDate actionDate = LocalDate.now();
  private Integer year;
  private Integer weekNumber;
  private String comment;
}
