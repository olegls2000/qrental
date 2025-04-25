package ee.qrent.billing.invoice.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public class InvoiceDeleteRequest extends AbstractDeleteRequest {
  public InvoiceDeleteRequest(final Long id) {
    super(id);
  }
}
