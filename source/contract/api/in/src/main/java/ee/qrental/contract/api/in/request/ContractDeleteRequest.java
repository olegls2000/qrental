package ee.qrental.contract.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class ContractDeleteRequest extends AbstractDeleteRequest {
  public ContractDeleteRequest(final Long id) {
    super(id);
  }
}
