package ee.qrental.transaction.api.in.request.kind;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class TransactionKindUpdateRequest extends AbstractUpdateRequest {
  private String name;
  private String comment;
}
