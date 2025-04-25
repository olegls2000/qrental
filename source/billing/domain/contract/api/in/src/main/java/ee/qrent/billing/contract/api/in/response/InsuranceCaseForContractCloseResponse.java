package ee.qrent.billing.contract.api.in.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class InsuranceCaseForContractCloseResponse {
  private Long insuranceCaseId;
  private Boolean withQKasko;
  private BigDecimal paymentAmount;
  private BigDecimal paidAmount;
  private BigDecimal originalAmount;
}
