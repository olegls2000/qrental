package ee.qrent.billing.insurance.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class InsuranceCaseBalance {
  private Long id;
  private Long qWeekId;
  private BigDecimal damageRemaining;
  private BigDecimal selfResponsibilityRemaining;
  private InsuranceCase insuranceCase;
  private Boolean withQKasko;
  private List<Long> transactionIds;
}
