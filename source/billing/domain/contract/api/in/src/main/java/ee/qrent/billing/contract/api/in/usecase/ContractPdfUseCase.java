package ee.qrent.billing.contract.api.in.usecase;

import java.io.InputStream;

public interface ContractPdfUseCase {
  InputStream getPdfInputStreamById(final Long id);
}
