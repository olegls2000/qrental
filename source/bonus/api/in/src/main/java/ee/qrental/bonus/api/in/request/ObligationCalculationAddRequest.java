package ee.qrental.bonus.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ObligationCalculationAddRequest extends AbstractAddRequest {
  private LocalDate actionDate = LocalDate.now();
  private Long qWeekId;
  private String comment;
}
