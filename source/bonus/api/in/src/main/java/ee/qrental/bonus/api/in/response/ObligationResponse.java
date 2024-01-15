package ee.qrental.bonus.api.in.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ObligationResponse {
  private Long id;
  private Long qWeekId;
  private Long driverId;
  private BigDecimal amount;
  private BigDecimal positiveAmount;
  private Integer matchCount;
}
