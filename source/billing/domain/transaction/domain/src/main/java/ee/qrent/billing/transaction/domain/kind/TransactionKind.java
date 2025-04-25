package ee.qrent.billing.transaction.domain.kind;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionKind {
  private Long id;
  private String code;
  private String name;
  private String comment;
}
