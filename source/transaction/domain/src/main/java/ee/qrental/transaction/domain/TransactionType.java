package ee.qrental.transaction.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionType {
  private Long id;
  private String name;
  private String description;
  private Boolean negative;
  private String comment;
}
