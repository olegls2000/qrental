package ee.qrental.contract.api.in.usecase;

import java.io.InputStream;

public interface ContractPdfUseCase {
  InputStream getPdfInputStreamById(final Long id);
}
