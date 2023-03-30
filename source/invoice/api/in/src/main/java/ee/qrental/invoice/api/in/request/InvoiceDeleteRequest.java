package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class InvoiceDeleteRequest extends AbstractDeleteRequest {
  public InvoiceDeleteRequest(final Long id) {
    super(id);
  }
}
