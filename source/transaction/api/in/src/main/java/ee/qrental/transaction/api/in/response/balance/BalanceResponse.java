package ee.qrental.transaction.api.in.response.balance;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BalanceResponse {
  private Long id;
  private BigDecimal feeAbleAmount;
  private BigDecimal nonFeeAbleAmount;
  private BigDecimal feeAmount;
  private BigDecimal positiveAmount;
  private BigDecimal amount;
  private Long qWeekId;
  private Integer year;
  private Integer weekNumber;
  private Long driverId;
  private String driverInfo;
  private LocalDate created;
}
