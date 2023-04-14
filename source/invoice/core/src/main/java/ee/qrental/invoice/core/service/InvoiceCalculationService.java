package ee.qrental.invoice.core.service;

import ee.qrental.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrental.invoice.api.in.request.InvoiceCalculationDeleteRequest;
import ee.qrental.invoice.api.in.request.InvoiceCalculationUpdateRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationDeleteUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationUpdateUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationService
    implements InvoiceCalculationAddUseCase,
        InvoiceCalculationUpdateUseCase,
        InvoiceCalculationDeleteUseCase {

  @Override
  public void add(final InvoiceCalculationAddRequest request) {
    System.out.println("MOck add");
  }

  @Override
  public void delete(final InvoiceCalculationDeleteRequest request) {
    System.out.println("MOck delete");
  }

  @Override
  public void update(final InvoiceCalculationUpdateRequest request) {
    System.out.println("MOck update");
  }
}
