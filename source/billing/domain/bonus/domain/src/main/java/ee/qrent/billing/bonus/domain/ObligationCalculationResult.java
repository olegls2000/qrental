package ee.qrent.billing.bonus.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ObligationCalculationResult {
  private Long id;
  private Long calculationId;
  private Long obligationId;
}
