package ee.qrent.billing.report.core.service.pdf.converter;

import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportPdfDocumentUtils.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderCommon.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderTuesday.*;

import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportPdfConversionStrategyTuesday
    extends AbstractWeeklyReportPdfConversionStrategy {

  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.TUESDAY_REPORT;
  }

  @Override
  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var weeklyReportPdfDoc = getA4PdfDocument();
    final var weeklyReportPdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(weeklyReportPdfDoc, weeklyReportPdfOutputStream);
    weeklyReportPdfDoc.open();
    weeklyReportPdfDoc.add(getHeaderTable(getLabelFromTuesday(language, REPORT_NAME_KEY)));
    weeklyReportPdfDoc.add(getDriverMainDataTable(model));
    weeklyReportPdfDoc.add(getClarificationHeaderRow(language));
    weeklyReportPdfDoc.add(getPreviousWeekObligationStatusText(model));
    gePreviousThursdayUnderpaymentInfoText(model).ifPresent(weeklyReportPdfDoc::add);
    weeklyReportPdfDoc.add(getBonusStatusText(model));
    weeklyReportPdfDoc.add(getPaymentStatus(model));
    getObligationOutcomeAboutCurrentWeekTable(model).ifPresent(weeklyReportPdfDoc::add);
    weeklyReportPdfDoc.add(
        getClarificationHeaderRowColored(
            getLabelFromCommon(language, OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY)));
    weeklyReportPdfDoc.add(getRentClarificationTable(model));
    weeklyReportPdfDoc.add(getRentAdjustmentClarificationTable(model));
    weeklyReportPdfDoc.add(getOtherPaymentClarificationTable(model));
    weeklyReportPdfDoc.add(getPivotalSummary(model));
    weeklyReportPdfDoc.add(getTotalBlock(model));
    weeklyReportPdfDoc.add(getCommentRowTable(language));
    weeklyReportPdfDoc.add(getPredictionHeaderRow(language));
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }

}
