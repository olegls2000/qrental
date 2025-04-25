package ee.qrent.billing.insurance.api.in.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class InsuranceCasePreCloseResponse {
  private Long insuranceCaseId;
  private Long driverId;
  private String driverInfo;
  private Boolean withQKasko;
  private BigDecimal paymentAmount;
  private BigDecimal paidAmount;
  private BigDecimal originalAmount;
}
