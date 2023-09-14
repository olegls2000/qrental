package ee.qrental.contract.core.service;

import ee.qrental.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.core.service.pdf.ContractToPdfConverter;
import ee.qrental.contract.core.service.pdf.ContractToPdfModelMapper;
import java.io.InputStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractPdfUseCaseImpl implements ContractPdfUseCase {

  private final ContractLoadPort loadPort;
  private final ContractToPdfConverter converter;
  private final ContractToPdfModelMapper mapper;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var domain = loadPort.loadById(id);
    final var pdfModel = mapper.getPdfModel(domain);

    return converter.getPdfInputStream(pdfModel);
  }
}
