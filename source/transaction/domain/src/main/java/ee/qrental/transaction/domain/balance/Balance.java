package ee.qrental.transaction.domain.balance;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Balance {
  private Long id;
  private Long driverId;
  private Long qWeekId;
  private LocalDate created;
  private BigDecimal feeAbleAmount;
  private BigDecimal nonFeeAbleAmount;
  private BigDecimal fee;

  public BigDecimal getAmount() {
    return feeAbleAmount.add(nonFeeAbleAmount);
  }
}
