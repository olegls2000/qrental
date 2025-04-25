package ee.qrent.billing.transaction.api.in.request.kind;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionKindAddRequest extends AbstractAddRequest {
  private String code;
  private String name;
  private String comment;
}
