package ee.qrental.bonus.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BonusCalculationAddRequest extends AbstractAddRequest {
  private LocalDate actionDate = LocalDate.now();
  private Long qWeekId;
  private String comment;
}
