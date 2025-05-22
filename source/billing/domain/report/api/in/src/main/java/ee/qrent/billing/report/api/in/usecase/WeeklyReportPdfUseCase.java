package ee.qrent.billing.report.api.in.usecase;

import java.io.InputStream;

public interface WeeklyReportPdfUseCase {
  InputStream getPdfInputStreamById(final Long id);
}
