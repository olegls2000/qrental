package ee.qrent.billing.contract.api.in.request;

import ee.qrent.common.in.request.AbstractCloseRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContractCloseRequest extends AbstractCloseRequest {
  private Long id;
}
