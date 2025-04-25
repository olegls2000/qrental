package ee.qrent.billing.invoice.core.service;

import ee.qrent.billing.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;
import ee.qrent.billing.invoice.core.service.pdf.InvoiceToPdfConverter;
import ee.qrent.billing.invoice.core.service.pdf.InvoiceToPdfModelMapper;

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
