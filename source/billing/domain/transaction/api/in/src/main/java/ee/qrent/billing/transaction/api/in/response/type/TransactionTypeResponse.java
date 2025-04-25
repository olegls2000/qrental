package ee.qrent.billing.transaction.api.in.response.type;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionTypeResponse {
  private Long id;
  private String name;
  private String description;
  private String invoiceName;
  private Boolean invoiceIncluded;
  private String kind;
  private Boolean negative;
  private Boolean feeAble;
  private String comment;
}
