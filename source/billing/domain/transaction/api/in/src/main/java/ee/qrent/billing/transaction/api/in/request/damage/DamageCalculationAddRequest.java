package ee.qrent.billing.transaction.api.in.request.damage;

import ee.qrent.common.in.request.AbstractAddRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DamageCalculationAddRequest extends AbstractAddRequest {
  private LocalDate actionDate;
  private String comment;
}
