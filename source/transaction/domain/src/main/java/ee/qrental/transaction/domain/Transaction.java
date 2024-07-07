package ee.qrental.transaction.domain;

import ee.qrental.common.utils.QTimeUtils;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;
import ee.qrental.transaction.domain.type.TransactionType;
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
  private Boolean calculated;
  private String comment;

  public BigDecimal getRealAmount() {
    final var kind = type.getKind();
    // TODO remove after kind is fulfilled
    if (kind == null) {
      return amount;
    }

    final var kindCode = type.getKind().getCode();

    if (TransactionKindsCode.P.name().equals(kindCode)) {
      return amount;
    }
    return amount.negate();
  }

  public Integer getWeekNumber() {
    return QTimeUtils.getWeekNumber(date);
  }

  public Boolean isRaw() {
    return !calculated;
  }
}
