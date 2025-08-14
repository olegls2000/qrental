package ee.qrent.billing.report.core.service;

import ee.qrent.billing.report.api.in.usecase.WeeklyReportPdfUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportMondayPdfConverter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportToPdfModelMapper;

import java.io.InputStream;
import java.util.List;

import ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportPdfConversionStrategy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportPdfUseCaseImpl implements WeeklyReportPdfUseCase {

  private final WeeklyReportLoadPort loadPort;
  private final WeeklyReportToPdfModelMapper mapper;
  private final List<WeeklyReportPdfConversionStrategy> strategies;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var report = loadPort.loadById(id);
    final var reportPdfModel = mapper.getPdfModel(report);

    return strategies.stream()
        .filter(strategy -> strategy.canApply(report.getType()))
        .findFirst()
        .get()
        .getPdfInputStream(reportPdfModel);
  }
}
