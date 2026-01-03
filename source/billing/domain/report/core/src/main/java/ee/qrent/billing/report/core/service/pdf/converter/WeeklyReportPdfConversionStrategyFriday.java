package ee.qrent.billing.report.core.service.pdf.converter;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static com.lowagie.text.Element.ALIGN_CENTER;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.PageSize.A4;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportFormatUtils.formatAmountWithCurrency;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportFormatUtils.formatInterval;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportPdfDocumentUtils.*;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportPdfDocumentUtils.getEmptyRow;

import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderCommon.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderFriday.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderFriday.BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderFriday.BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderFriday.BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderFriday.BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY;
import static java.lang.String.format;

public class WeeklyReportPdfConversionStrategyFriday
    extends AbstractWeeklyReportPdfConversionStrategy {

  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.FRIDAY_REPORT;
  }

  @Override
  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var weeklyReportPdfDoc = new Document(A4, 30f, 30f, 20f, 20f);
    final var weeklyReportPdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(weeklyReportPdfDoc, weeklyReportPdfOutputStream);
    weeklyReportPdfDoc.open();
    weeklyReportPdfDoc.add(getHeaderTable(getLabelFromFriday(language, REPORT_NAME_KEY)));
    weeklyReportPdfDoc.add(getDriverMainDataTable(model));
    weeklyReportPdfDoc.add(getClarificationHeaderRow(language));
    weeklyReportPdfDoc.add(getCurrentWeekObligationStatusText(model));
    weeklyReportPdfDoc.add(getCurrentThursdayUnderOrOverpaymentInfoText(model));
    weeklyReportPdfDoc.add(getBonusStatusInfoText(model));

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

  PdfPTable getCurrentThursdayUnderOrOverpaymentInfoText(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);
    final var paragraph = new Paragraph();

    final var explanationChunk =
        model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
            ? getNormalChunk(
                getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_1_OVERPAYMENT_LABEL_KEY))
            : getNormalChunk(
                getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY));
    paragraph.add(explanationChunk);

    paragraph.add(
        getNormalChunk(getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY)));
    paragraph.add(
        getBoldChunk(getLabelFromCommon(language, FRIDAY_THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY)));
    final var netAmountOnThursday =
        formatAmountWithCurrency(model.getTotalPaymentAmountRaw(), language);

    final var sumColor =
        model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
            ? REPORT_GREEN_COLOR
            : REPORT_RED_COLOR;

    paragraph.add(new Chunk(netAmountOnThursday, new Font(REPORT_FONT, 10, BOLD, sumColor)));

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell);

    return table;
  }

  PdfPTable getBonusStatusInfoText(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);
    final var paragraph = new Paragraph();

    final var explanationChunk =
            model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
                    ? getNormalChunk(
                    getLabelFromFriday(language, BONUS_PROGRAM_ACTIVE_COMMENT_LABEL_KEY))
                    : getNormalChunk(
                    getLabelFromFriday(language, BONUS_PROGRAM_INACTIVE_COMMENT_LABEL_KEY));
    paragraph.add(explanationChunk);

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell);

    return table;
  }

  private PdfPTable getCurrentWeekObligationStatusText(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);
    final var paragraph = new Paragraph();

    final var weekDaysFormatted =
        formatInterval(model.getCurrentWeekStart(), model.getCurrentWeekEnd());

    if (model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)) {
      paragraph.add(
          getNormalChunk(getLabelFromFriday(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY)));
      paragraph.add(getBoldChunk(weekDaysFormatted));
      paragraph.add(
          getNormalChunk(getLabelFromFriday(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY)));
/*      paragraph.add(
          getNormalChunk(getLabelFromFriday(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY)));
      final var amount = model.getTotalPaymentAmountRaw();
      final var currency = getLabelFromCommon(language, CURRENCY_NAME_KEY);
      final var value = format(" %s %s", amount, currency);
      paragraph.add(getBoldChunk(value));
      paragraph.add(
          getNormalChunk(getLabelFromFriday(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY)));*/

    } else {
      paragraph.add(
          getNormalChunk(
              getLabelFromFriday(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY)));
      paragraph.add(getBoldChunk(weekDaysFormatted));
      paragraph.add(
          getNormalChunk(
              getLabelFromFriday(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY)));
    }

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell);
    table.addCell(getEmptyRow());

    return table;
  }
}
