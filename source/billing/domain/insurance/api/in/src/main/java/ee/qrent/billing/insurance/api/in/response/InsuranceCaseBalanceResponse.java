package ee.qrent.billing.insurance.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Getter
public class InsuranceCaseBalanceResponse {
  private String qWeekInfo;
  private BigDecimal damageRemaining;
  private BigDecimal selfResponsibilityRemaining;
  private Boolean withQKasko;
}
