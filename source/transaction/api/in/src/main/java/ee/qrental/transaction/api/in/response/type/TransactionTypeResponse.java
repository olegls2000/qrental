package ee.qrental.transaction.api.in.response.type;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionTypeResponse {
  private Long id;
  private String name;
  private String description;
  private String descriptionRus;
  private Boolean negative;
  private Boolean feeAble;
  private String comment;
}
