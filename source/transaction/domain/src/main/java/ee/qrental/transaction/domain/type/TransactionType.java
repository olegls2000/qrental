package ee.qrental.transaction.domain.type;

import ee.qrental.transaction.domain.kind.TransactionKind;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionType {
  private Long id;
  private TransactionKind kind;
  private String name;
  private String description;
  private String descriptionRus;
  private Boolean negative;
  private Boolean feeAble;
  private String comment;
}
