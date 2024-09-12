package ee.qrental.insurance.api.in.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class InsuranceBalanceTotalResponse {
  private BigDecimal damageInitialTotal;
  private BigDecimal damageRemainingTotal;
  private BigDecimal selfResponsibilityRemainingTotal;
}
