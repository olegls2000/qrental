package ee.qrental.invoice.api.in.usecase;

import ee.qrental.invoice.api.in.request.InvoiceCalculationRequest;

public interface InvoiceCalculationUseCase {

  void calculate(final InvoiceCalculationRequest request);
}
