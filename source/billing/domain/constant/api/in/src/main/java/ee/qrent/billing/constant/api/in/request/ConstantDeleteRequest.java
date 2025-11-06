package ee.qrent.billing.constant.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConstantDeleteRequest extends AbstractDeleteRequest {
  public ConstantDeleteRequest(final Long id) {
    super(id);
  }
}
