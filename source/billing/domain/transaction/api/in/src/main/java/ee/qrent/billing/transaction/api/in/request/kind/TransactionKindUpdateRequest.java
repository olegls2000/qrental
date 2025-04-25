package ee.qrent.billing.transaction.api.in.request.kind;

import ee.qrent.common.in.request.AbstractUpdateRequest;
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
