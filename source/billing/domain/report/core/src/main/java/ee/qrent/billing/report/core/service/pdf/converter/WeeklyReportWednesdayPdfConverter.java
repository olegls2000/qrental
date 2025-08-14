package ee.qrent.billing.report.core.service.pdf.converter;

import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;

import java.io.InputStream;

public class WeeklyReportWednesdayPdfConverter implements WeeklyReportPdfConversionStrategy {
  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.WEDNESDAY_REPORT;
  }

  @Override
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    return null;
  }
}
