package ee.qrental.contract.core.service;

import ee.qrental.contract.api.in.usecase.AuthorizationBoltPdfUseCase;
import ee.qrental.contract.api.out.AuthorizationBoltLoadPort;
import ee.qrental.contract.core.service.pdf.AuthorizationBoltToPdfConverter;
import ee.qrental.contract.core.service.pdf.AuthorizationBoltToPdfModelMapper;
import java.io.InputStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltPdfUseCaseImpl implements AuthorizationBoltPdfUseCase {

  private final AuthorizationBoltLoadPort loadPort;
  private final AuthorizationBoltToPdfConverter converter;
  private final AuthorizationBoltToPdfModelMapper mapper;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var domain = loadPort.loadById(id);
    final var pdfModel = mapper.getPdfModel(domain);

    return converter.getPdfInputStream(pdfModel);
  }
}
