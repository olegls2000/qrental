package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.Element.*;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportPdfDocumentUtils.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.getLabel;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.*;
import static java.awt.Color.*;

import java.awt.*;

import com.lowagie.text.*;

import com.lowagie.text.Font;

import com.lowagie.text.pdf.PdfPTable;
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
    weeklyReportPdfDoc.add(getHeaderTable(language));
    weeklyReportPdfDoc.add(getDriverMainDataTable(model));
    weeklyReportPdfDoc.add(getClarificationHeaderRow(getLabel(language, THURSDAY_LABEL_KEY)));
    weeklyReportPdfDoc.add(getBonusStatus(model));
    weeklyReportPdfDoc.add(getObligationOutcomeAboutCurrentWeekTable(model));
    weeklyReportPdfDoc.add(
        getClarificationHeaderRowColored(
            getLabel(language, OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY)));
    weeklyReportPdfDoc.add(getRentClarificationTable(model));
    weeklyReportPdfDoc.add(getRentAdjustmentClarificationTable(model));
    weeklyReportPdfDoc.add(getOtherPaymentClarificationTable(model));
    weeklyReportPdfDoc.add(getDemandOnTheBeginningOfWeek(model));
    weeklyReportPdfDoc.add(getTotalBlock(model));
    weeklyReportPdfDoc.add(getCommentRowTable(language));
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }

  private PdfPTable getCommentRowTable(final String language) {
    final var row = getQpdfTable(1);
    final var cell =
        getQpdfPCell(
            new Paragraph(
                getLabel(language, COMMENT_LABEL_KEY), new Font(REPORT_FONT, 9, Font.BOLD, BLACK)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    row.addCell(cell);

    return row;
  }
}
