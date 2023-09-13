package ee.qrental.contract.core.service;

import ee.qrental.contract.core.service.pdf.ContractToPdfModelMapper;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.contract.core.service.pdf.ContractToPdfConverter;

import java.io.InputStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractPdfUseCaseImpl implements ContractPdfUseCase {

  private final ContractLoadPort loadPort;
  private final ContractToPdfConverter converter;
  private final ContractToPdfModelMapper mapper;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var invoice = loadPort.loadById(id);
    final var invoicePdfModel = mapper.getPdfModel(invoice);

    return converter.getPdfInputStream(invoicePdfModel);
  }
}
