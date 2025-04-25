package ee.qrent.billing.invoice.api.in.request;


import ee.qrent.common.in.request.AbstractDeleteRequest;

public class InvoiceCalculationDeleteRequest extends AbstractDeleteRequest {
  public InvoiceCalculationDeleteRequest(final Long id) {
    super(id);
  }
}
