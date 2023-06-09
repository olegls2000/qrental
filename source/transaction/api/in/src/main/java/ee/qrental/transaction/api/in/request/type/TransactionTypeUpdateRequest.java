package ee.qrental.transaction.api.in.request.type;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class TransactionTypeUpdateRequest extends AbstractUpdateRequest {

  private String name;
  private String description;
  private Boolean negative;
  private String comment;
}
