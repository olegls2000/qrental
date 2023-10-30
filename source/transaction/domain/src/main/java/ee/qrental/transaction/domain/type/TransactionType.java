package ee.qrental.transaction.domain.type;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionType {
  private Long id;
  private String name;
  private String description;
  private String descriptionRus;
  private Boolean negative;
  private Boolean feeAble;
  private String comment;
}
