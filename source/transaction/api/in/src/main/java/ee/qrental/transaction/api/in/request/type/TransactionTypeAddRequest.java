package ee.qrental.transaction.api.in.request.type;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionTypeAddRequest extends AbstractAddRequest {
  private Long transactionKindId;
  private String name;
  private String description;
  private String invoiceName;
  private Boolean invoiceIncluded;
  private String comment;
}
