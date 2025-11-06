package ee.qrent.billing.contract.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AbsenceDeleteRequest extends AbstractDeleteRequest {
  public AbsenceDeleteRequest(final Long id) {
    super(id);
  }
}
