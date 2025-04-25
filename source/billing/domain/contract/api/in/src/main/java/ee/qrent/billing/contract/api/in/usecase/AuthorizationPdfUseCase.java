package ee.qrent.billing.contract.api.in.usecase;

import java.io.InputStream;

public interface AuthorizationPdfUseCase {
  InputStream getPdfInputStreamById(final Long id);
}
