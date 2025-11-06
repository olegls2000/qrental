package ee.qrent.billing.deposit.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DepositDeleteRequest extends AbstractDeleteRequest {
  public DepositDeleteRequest(final Long id) {
    super(id);
  }
}
