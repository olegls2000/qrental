package ee.qrent.billing.contract.core.service;

import ee.qrent.billing.contract.api.in.usecase.AuthorizationPdfUseCase;
import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import ee.qrent.billing.contract.core.service.pdf.AuthorizationToPdfConverter;
import ee.qrent.billing.contract.core.service.pdf.AuthorizationToPdfModelMapper;
import java.io.InputStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationPdfUseCaseImpl implements AuthorizationPdfUseCase {

  private final AuthorizationLoadPort loadPort;
  private final AuthorizationToPdfConverter converter;
  private final AuthorizationToPdfModelMapper mapper;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var domain = loadPort.loadById(id);
    final var pdfModel = mapper.getPdfModel(domain);

    return converter.getPdfInputStream(pdfModel);
  }
}
