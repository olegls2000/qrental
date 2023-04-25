package ee.qrental.invoice.core.service;

import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfConverter;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfModelMapper;
import ee.qrental.invoice.domain.Invoice;
import java.io.InputStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoicePdfUseCaseImpl implements InvoicePdfUseCase {

  private final InvoiceLoadPort loadPort;
  private final InvoiceToPdfConverter converter;
  private final InvoiceToPdfModelMapper mapper;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var invoice = loadPort.loadById(id);
    final var invoicePdfModel = mapper.getPdfModel(invoice);

    return converter.getPdfInputStream(invoicePdfModel);
  }
}
