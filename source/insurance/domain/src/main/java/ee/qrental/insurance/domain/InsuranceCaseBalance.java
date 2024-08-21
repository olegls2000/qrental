package ee.qrental.insurance.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Getter
@Setter
public class InsuranceCaseBalance {
  private Long id;
  private Long qWeekId;
  private BigDecimal damageRemaining;
  private BigDecimal selfResponsibilityRemaining;
  private InsuranceCase insuranceCase;
}
