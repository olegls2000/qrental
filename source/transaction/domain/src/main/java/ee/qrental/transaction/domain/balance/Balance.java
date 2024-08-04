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
  private BigDecimal feeAmount;
  private BigDecimal positiveAmount;
  private BigDecimal repairmentAmount;
  private Boolean derived;

  public BigDecimal getAmountsSumWithoutRepairment() {
    return positiveAmount
        .subtract(feeAmount)
        .subtract(nonFeeAbleAmount)
        .subtract(feeAbleAmount);
  }

  @Override
  public String toString() {
    return "Balance{"
        + "id="
        + id
        + ", driverId="
        + driverId
        + ", qWeekId="
        + qWeekId
        + ", created="
        + created
        + ", feeAbleAmount="
        + feeAbleAmount
        + ", nonFeeAbleAmount="
        + nonFeeAbleAmount
        + ", feeAmount="
        + feeAmount
        + ", repairmentAmount="
        + repairmentAmount
        + ", positiveAmount="
        + positiveAmount
        + ", derived="
        + derived
        + '}';
  }
}
