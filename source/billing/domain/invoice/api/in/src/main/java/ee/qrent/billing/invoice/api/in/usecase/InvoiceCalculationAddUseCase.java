package ee.qrent.billing.invoice.api.in.usecase;

import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationAddRequest;

public interface InvoiceCalculationAddUseCase {

  void add(final InvoiceCalculationAddRequest request);
}
