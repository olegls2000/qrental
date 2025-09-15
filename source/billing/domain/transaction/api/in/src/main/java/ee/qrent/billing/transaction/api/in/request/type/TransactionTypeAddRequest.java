package ee.qrent.billing.transaction.api.in.request.type;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionTypeAddRequest extends AbstractAddRequest {
  private Long transactionKindId;
  @Deprecated private String name;
  @Deprecated private String description;
  @Deprecated private String invoiceName;
  private String code;
  private String nameEng;
  private String nameEst;
  private String nameRus;
  private Boolean invoiceIncluded;
  private Boolean visibleForUi;
  private String comment;
}
