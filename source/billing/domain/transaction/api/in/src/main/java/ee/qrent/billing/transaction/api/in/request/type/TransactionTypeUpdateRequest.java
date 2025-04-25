package ee.qrent.billing.transaction.api.in.request.type;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class TransactionTypeUpdateRequest extends AbstractUpdateRequest {
  private Long transactionKindId;
  private String name;
  private String description;
  private String invoiceName;
  private Boolean invoiceIncluded;
  private String comment;
}
