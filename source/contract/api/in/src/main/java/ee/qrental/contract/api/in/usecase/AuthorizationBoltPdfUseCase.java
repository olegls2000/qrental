package ee.qrental.contract.api.in.usecase;

import java.io.InputStream;

public interface AuthorizationBoltPdfUseCase {
  InputStream getPdfInputStreamById(final Long id);
}
