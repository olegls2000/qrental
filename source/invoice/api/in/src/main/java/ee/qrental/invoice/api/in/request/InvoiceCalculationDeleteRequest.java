package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class InvoiceCalculationDeleteRequest extends AbstractDeleteRequest {
  public InvoiceCalculationDeleteRequest(final Long id) {
    super(id);
  }
}
