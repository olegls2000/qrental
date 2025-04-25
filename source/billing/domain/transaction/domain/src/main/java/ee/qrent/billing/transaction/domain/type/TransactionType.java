package ee.qrent.billing.transaction.domain.type;

import ee.qrent.billing.transaction.domain.kind.TransactionKind;
import ee.qrent.billing.transaction.domain.kind.TransactionKindsCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionType {
  private Long id;
  private TransactionKind kind;
  private String name;
  private String description;
  private String invoiceName;
  private Boolean invoiceIncluded;
  private String comment;

  public boolean isNegative() {
    // TODO must be removed
    if (kind == null) {
      return false;
    }
    final var kindCode = kind.getCode();
    if (TransactionKindsCode.P.name().equals(kindCode)) {

      return false;
    }
    return true;
  }

  public boolean isFeeAble() {
    // TODO remove after Kind is set for all types
    if (kind == null) {
      return false;
    }

    final var kindCode = kind.getCode();
    if (TransactionKindsCode.FA.name().equals(kindCode)) {

      return true;
    }
    return false;
  }
}
