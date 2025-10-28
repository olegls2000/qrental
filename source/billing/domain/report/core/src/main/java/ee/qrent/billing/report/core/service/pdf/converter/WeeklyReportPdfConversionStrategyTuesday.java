package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.Element.*;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportFormatUtils.formatAmountWithCurrency;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportFormatUtils.formatInterval;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportPdfDocumentUtils.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderCommon.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderTuesday.*;
import static java.awt.Color.*;
import static java.math.BigDecimal.ZERO;

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
    weeklyReportPdfDoc.add(getHeaderTable(getLabelFromTuesday(language, REPORT_NAME_KEY)));
    weeklyReportPdfDoc.add(getDriverMainDataTable(model));
    weeklyReportPdfDoc.add(getClarificationHeaderRow(getLabelFromTuesday(language, THURSDAY_LABEL_KEY)));
    weeklyReportPdfDoc.add(this.getBonusStatus(model));
    weeklyReportPdfDoc.add(getObligationOutcomeAboutCurrentWeekTable(model));
    weeklyReportPdfDoc.add(
        getClarificationHeaderRowColored(
            getLabelFromCommon(language, OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY)));
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

  PdfPTable getBonusStatus(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);
    final var paragraph1 = new Paragraph();
    paragraph1.add(getNormalChunk(getLabelFromTuesday(language, OBLIGATION_TEXT_PART_1_LABEL_KEY)));
    paragraph1.add(
            model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
                    ? getBoldChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY))
                    : getBoldChunk(
                    getLabelFromCommon(language, OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY)));

    paragraph1.add(getNormalChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_3_LABEL_KEY)));
    paragraph1.add(getBoldChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_4_LABEL_KEY)));
    paragraph1.add(getNormalChunk(getLabelFromTuesday(language, OBLIGATION_TEXT_PART_5_LABEL_KEY)));
    final var weekDaysFormatted =
            formatInterval(model.getPreviousWeekStart(), model.getPreviousWeekEnd());
    paragraph1.add(getBoldChunk(weekDaysFormatted));
    paragraph1.add(getNormalChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_6_LABEL_KEY)));

    final var cell1 = getQpdfPCell(paragraph1);

    cell1.setHorizontalAlignment(ALIGN_CENTER);
    cell1.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell1);
    table.addCell(getEmptyRow());

    final var paragraph2 = new Paragraph();
    paragraph2.add(
            model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
                    ? getNormalChunk(
                    getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY))
                    : getNormalChunk(
                    getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY)));
    paragraph2.add(
            getNormalChunk(getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY)));
    paragraph2.add(
            getBoldChunk(getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY)));
    final var netAmountOnThursday =
            formatAmountWithCurrency(model.getNetAmountOnThursday(), language);

    final var amountColor =
            model.getNetAmountOnThursday().compareTo(ZERO) <= 0 ? REPORT_RED_COLOR : REPORT_GREEN_COLOR;
    paragraph2.add(
            new Chunk(
                    netAmountOnThursday,
                    new Font(REPORT_FONT, 10, Font.BOLD, amountColor))); //  amountColor
    final var cell2 = getQpdfPCell(paragraph2);
    cell2.setHorizontalAlignment(ALIGN_CENTER);
    cell2.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell2);

    final var paragraphWithBonuses = new Paragraph();
    paragraphWithBonuses.add(
            getNormalChunk(getLabelFromCommon(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY)));
    paragraphWithBonuses.add(
            getBoldChunk(getLabelFromCommon(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY)));
    paragraphWithBonuses.add(
            getNormalChunk(getLabelFromCommon(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY)));
    paragraphWithBonuses.add(
            getBoldChunk(getLabelFromCommon(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY)));
    paragraphWithBonuses.add(
            getNormalChunk(getLabelFromCommon(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY)));

    final var paragraphWithoutBonuses = new Paragraph();
    paragraphWithoutBonuses.add(
            getNormalChunk(getLabelFromCommon(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY)));
    paragraphWithoutBonuses.add(
            getBoldChunk(getLabelFromCommon(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY)));
    paragraphWithoutBonuses.add(
            getNormalChunk(getLabelFromCommon(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY)));
    paragraphWithoutBonuses.add(
            getBoldChunk(getLabelFromCommon(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY)));
    paragraphWithoutBonuses.add(
            getNormalChunk(getLabelFromCommon(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY)));

    final var paragraph =
            model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
                    ? paragraphWithBonuses
                    : paragraphWithoutBonuses;

    final var cell3 = getQpdfPCell(paragraph);
    cell3.setHorizontalAlignment(ALIGN_CENTER);
    cell3.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cell3);
    table.addCell(getEmptyRow());

    final var paragraphEmpty = new Paragraph();
    final var cellEmpty = getQpdfPCell(paragraphEmpty);
    cellEmpty.setHorizontalAlignment(ALIGN_CENTER);
    cellEmpty.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cellEmpty);

    final var row = getQpdfTable(1);
    final var paddingTopCell =
            getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 14, Font.BOLD)));
    row.addCell(paddingTopCell);

    return table;
  }
}
