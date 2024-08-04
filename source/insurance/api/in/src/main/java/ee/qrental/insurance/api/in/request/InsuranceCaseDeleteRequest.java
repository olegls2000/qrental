package ee.qrental.insurance.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public class InsuranceCaseDeleteRequest extends AbstractDeleteRequest {
  public InsuranceCaseDeleteRequest(final Long id) {
    super(id);
  }
}
