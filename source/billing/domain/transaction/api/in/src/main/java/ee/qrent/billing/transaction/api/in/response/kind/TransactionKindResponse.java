package ee.qrent.billing.transaction.api.in.response.kind;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionKindResponse {
  private Long id;
  private String code;
  private String name;
  private String comment;
}
