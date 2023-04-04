package ee.qrental.transaction.domain;

import ee.qrental.common.core.utils.QTimeUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Transaction {

  private Long id;
  private TransactionType type;
  private Long driverId;
  private BigDecimal amount;
  private LocalDate date;
  private String comment;

  public BigDecimal getRealAmount() {
    return type.getNegative() ? amount.negate() : amount;
  }

  public Integer getWeekNumber() {
    return QTimeUtils.getWeekNumber(date);
  }
}
