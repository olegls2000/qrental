package ee.qrental.invoice.api.in.usecase;

import java.io.InputStream;

public interface InvoicePdfUseCase {
  InputStream getPdfInputStreamById(final Long id);
}
