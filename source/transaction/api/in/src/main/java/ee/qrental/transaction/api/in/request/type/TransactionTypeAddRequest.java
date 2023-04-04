package ee.qrental.transaction.api.in.request.type;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionTypeAddRequest extends AbstractAddRequest {

  private String name;
  private String description;
  private Boolean negative;
  private String comment;
}
