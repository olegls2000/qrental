package ee.qrent.billing.bonus.api.in.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ObligationResponse {
  private Long id;
  private String driverInfo;
  private BigDecimal amount;
  private BigDecimal positiveAmount;
  private Integer matchCount;
}
