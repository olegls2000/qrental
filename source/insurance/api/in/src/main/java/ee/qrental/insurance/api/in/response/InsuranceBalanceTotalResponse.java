package ee.qrental.insurance.api.in.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class InsuranceBalanceTotalResponse {
  private BigDecimal damageRemainingTotal;
  private BigDecimal selfResponsibilityRemainingTotal;
}
