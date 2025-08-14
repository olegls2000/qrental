package ee.qrent.billing.report.core.service.pdf.converter;

import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;

import java.io.InputStream;

public interface WeeklyReportPdfConversionStrategy {
  boolean canApply(final WeeklyReportType reportType);

  InputStream getPdfInputStream(final WeeklyReportPdfModel model);
}
