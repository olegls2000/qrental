package ee.qrental.invoice.core.service;

import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import java.io.InputStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoicePdfUseCaseImpl implements InvoicePdfUseCase {

  private final InvoiceLoadPort loadPort;
  private final InvoicePdfService pdfService;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var invoice = loadPort.loadById(id);
    return pdfService.getPdfInputStream(invoice);
  }
}
