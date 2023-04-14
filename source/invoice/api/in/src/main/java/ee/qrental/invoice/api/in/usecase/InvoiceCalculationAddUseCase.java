package ee.qrental.invoice.api.in.usecase;

import ee.qrental.invoice.api.in.request.InvoiceCalculationAddRequest;

public interface InvoiceCalculationAddUseCase {

  void add(final InvoiceCalculationAddRequest request);
}
