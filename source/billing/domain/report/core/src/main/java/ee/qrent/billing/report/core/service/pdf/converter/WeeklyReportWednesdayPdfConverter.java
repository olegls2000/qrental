package ee.qrent.billing.report.core.service.pdf.converter;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.lowagie.text.Element.ALIGN_BOTTOM;
import static com.lowagie.text.Element.ALIGN_CENTER;
import static com.lowagie.text.Element.ALIGN_LEFT;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.Element.ALIGN_RIGHT;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.*;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.*;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_FEE_DEBT_CODE;
import static java.awt.Color.BLACK;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

public class WeeklyReportWednesdayPdfConverter implements WeeklyReportPdfConversionStrategy {

  public static final int REPORT_FONT = Font.HELVETICA;
  private static final Color REPORT_GRAY_BACKGROUND_COLOR = new Color(241, 241, 241);
  private static final Color REPORT_WHITE_BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color REPORT_GREEN_COLOR = new Color(0, 100, 0); // Green
  private static final Color REPORT_RED_COLOR = new Color(150, 30, 0); // Red
  private static final Color REPORT_DARK_BLUE_COLOR = new Color(0, 40, 120); // Dark Blue
  private static final Color REPORT_DARK_GRAY_COLOR = new Color(50, 50, 50); // Dark Gray
  private static final Color REPORT_PURPLE_COLOR = new Color(128, 0, 128); // Purple

  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.WEDNESDAY_REPORT;
  }

  @Override
  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();

    final var weeklyReportPdfDoc = new Document(A4, 30f, 30f, 20f, 20f);
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
            getLabel(language, OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY)
      //      getLabel(language, OBLIGATION_HEADER_TEXT_PART_2_LABEL_KEY)));
        ));
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

  private PdfPCell getQpdfPCell(final Paragraph paragraph) {
    final var qCell = new PdfPCell(paragraph);
    qCell.setBorder(NO_BORDER);

    return qCell;
  }

  private PdfPTable getQpdfTable(int numColumns) {
    final var table = new PdfPTable(numColumns);
    table.setWidthPercentage(100f);

    return table;
  }

  @SneakyThrows
  private PdfPTable getHeaderTable(final String language) {
    final var header = getQpdfTable(2);
    header.setWidths(new int[] {20, 80});

    com.lowagie.text.Image img = Image.getInstance("Images/qRentalGroup_gorznt.png");
    img.scaleToFit(33, 14);

    PdfPCell imageCell = new PdfPCell(img, true);
    imageCell.setHorizontalAlignment(ALIGN_LEFT);
    imageCell.setVerticalAlignment(ALIGN_MIDDLE);
    imageCell.setFixedHeight(20f);
    imageCell.setBorder(NO_BORDER);
    header.addCell(imageCell);
    final var reportNameCell =
        getQpdfPCell(
            new Paragraph(
                getLabel(language, REPORT_NAME_KEY), new Font(REPORT_FONT, 14, Font.BOLD)));

    reportNameCell.setHorizontalAlignment(ALIGN_LEFT);
    reportNameCell.setPaddingLeft(65f);
    reportNameCell.setVerticalAlignment(ALIGN_MIDDLE);
    header.addCell(reportNameCell);
    header.addCell(getEmptyRow());

    return header;
  }

  private PdfPCell getDriverMainDataLabelCell(final String label) {
    final var labelCell = getQpdfPCell(new Paragraph(label + ":", new Font(REPORT_FONT, 12)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_MIDDLE);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    labelCell.setFixedHeight(20f);
    labelCell.setPaddingRight(2f);

    return labelCell;
  }

  private PdfPCell getDriverMainDataValueCell(final String value) {
    final var labelCell = getQpdfPCell(new Paragraph(value, new Font(REPORT_FONT, 12, Font.BOLD)));
    labelCell.setHorizontalAlignment(ALIGN_LEFT);
    labelCell.setVerticalAlignment(ALIGN_MIDDLE);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    labelCell.setFixedHeight(20f);
    labelCell.setPaddingLeft(8f);

    return labelCell;
  }

  private PdfPTable getDriverMainDataTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(2);
    table.setWidths(new int[] {70, 30});
    table.addCell(getDriverMainDataLabelCell(getLabel(language, DRIVER_LABEL_KEY)));

    final var driverName = "%s %s".formatted(model.getFirstName(), model.getLastName());
    table.addCell(getDriverMainDataValueCell(driverName));

    final var taxNumber = model.getIdNumber().toString();
    table.addCell(getDriverMainDataLabelCell(getLabel(language, PERSONAL_NUMBER_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(taxNumber));

    table.addCell(getDriverMainDataLabelCell(getLabel(language, CALL_SIGN_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(model.getCallSign().toString()));

    table.addCell(getDriverMainDataLabelCell(getLabel(language, RENTED_CAR_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(model.getCarRegistrationNumber()));

    return table;
  }

  private Chunk getNormalChunk(final String text) {
    return new Chunk(text + " ", new Font(REPORT_FONT, 10, Font.NORMAL));
  }

  private Chunk getBoldChunk(final String text) {
    return new Chunk(text + " ", new Font(REPORT_FONT, 10, Font.BOLD));
  }

  private PdfPTable getBonusStatus(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);
    final var paragraph1 = new Paragraph();
    paragraph1.add(getNormalChunk(getLabel(language, OBLIGATION_TEXT_PART_1_LABEL_KEY)));
    paragraph1.add(
        model.getObligationStatus().equals("COMPLETED")
            ? getBoldChunk(getLabel(language, OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY))
            : getBoldChunk(getLabel(language, OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY)));

    paragraph1.add(getNormalChunk(getLabel(language, OBLIGATION_TEXT_PART_3_LABEL_KEY)));
    paragraph1.add(getBoldChunk(getLabel(language, OBLIGATION_TEXT_PART_4_LABEL_KEY)));
    paragraph1.add(getNormalChunk(getLabel(language, OBLIGATION_TEXT_PART_5_LABEL_KEY)));
    final var weekDaysFormatted =
        getInterval(model.getPreviousWeekStart(), model.getPreviousWeekEnd());
    paragraph1.add(getBoldChunk(weekDaysFormatted));
    paragraph1.add(getNormalChunk(getLabel(language, OBLIGATION_TEXT_PART_6_LABEL_KEY)));

    final var cell1 = getQpdfPCell(paragraph1);

    cell1.setHorizontalAlignment(ALIGN_CENTER);
    cell1.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell1);
    table.addCell(getEmptyRow());

    final var paragraph2 = new Paragraph();
    paragraph2.add(
        model.getObligationStatus().equals("COMPLETED")
            ? getNormalChunk(getLabel(language, THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY))
            : getNormalChunk(getLabel(language, THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY)));
    paragraph2.add(getNormalChunk(getLabel(language, THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY)));
    paragraph2.add(getBoldChunk(getLabel(language, THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY)));
    final var netAmountOnThursday = amountWithCurrency(model.getNetAmountOnThursday(), language);

    final var amountColor =
        model.getNetAmountOnThursday().compareTo(ZERO) < 0 ? REPORT_RED_COLOR : REPORT_GREEN_COLOR;
    paragraph2.add(
        new Chunk(
            netAmountOnThursday,
            new Font(REPORT_FONT, 10, Font.BOLD, amountColor))); //  amountColor
    final var cell2 = getQpdfPCell(paragraph2);
    cell2.setHorizontalAlignment(ALIGN_CENTER);
    cell2.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cell2);

    final var paragraphWithBonuses = new Paragraph();
    paragraphWithBonuses.add(
        getNormalChunk(getLabel(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY)));
    paragraphWithBonuses.add(
        getBoldChunk(getLabel(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY)));
    paragraphWithBonuses.add(
        getNormalChunk(getLabel(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY)));
    paragraphWithBonuses.add(
        getBoldChunk(getLabel(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY)));
    paragraphWithBonuses.add(
        getNormalChunk(getLabel(language, BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY)));

    final var paragraphWithoutBonuses = new Paragraph();
    paragraphWithoutBonuses.add(
        getNormalChunk(getLabel(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY)));
    paragraphWithoutBonuses.add(
        getBoldChunk(getLabel(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY)));
    paragraphWithoutBonuses.add(
        getNormalChunk(getLabel(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY)));
    paragraphWithoutBonuses.add(
        getBoldChunk(getLabel(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY)));
    paragraphWithoutBonuses.add(
        getNormalChunk(getLabel(language, BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY)));

    final var paragraph =
        model.getObligationStatus().equals("COMPLETED")
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

  private PdfPTable getObligationOutcomeAboutCurrentWeekTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var row = getQpdfTable(1);
    final var nextWeekDaysFormatted = getInterval(model.getNextWeekStart(), model.getNextWeekEnd());
    final var paragraph = new Paragraph();
    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.BOLD)));
    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.BOLD, REPORT_PURPLE_COLOR)));
    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.BOLD)));
    paragraph.add(
        new Chunk(
            amountWithCurrency(model.getTotalPaymentAmount(), model.getLanguage()),
            new Font(REPORT_FONT, 10, Font.BOLD, REPORT_RED_COLOR)));
    paragraph.add(
        new Chunk(
            "\n" + getLabel(language, OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));

    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));

    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY) + "\n",
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabel(language, OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(new Chunk(nextWeekDaysFormatted, new Font(REPORT_FONT, 10, Font.BOLD)));

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getClarificationHeaderRow(final String text) {
    final var row = getQpdfTable(1);
    final var paddingTopCell =
        getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 13, Font.BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);
    final var cell = getQpdfPCell(new Paragraph(text + ":", new Font(REPORT_FONT, 13, Font.BOLD)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getClarificationHeaderRowColored(
      final String prefixText) {
    final var row = getQpdfTable(1);
    final var paddingTopCell =
        getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 13, Font.BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);

    final var paragraph = new Paragraph();
    paragraph.add(new Chunk(prefixText, new Font(REPORT_FONT, 13, Font.BOLD)));
  //  paragraph.add(new Chunk(purpleText, new Font(REPORT_FONT, 13, Font.BOLD, REPORT_PURPLE_COLOR)));
    paragraph.add(new Chunk(":", new Font(REPORT_FONT, 13, Font.BOLD)));

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  private PdfPCell getClarificationTableLabelCell(final String label) {
    final var labelCell =
        getQpdfPCell(new Paragraph(label + ":", new Font(REPORT_FONT, 11, Font.BOLD, BLACK)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

    return labelCell;
  }

  private PdfPCell getClarificationTableLabelCellDarkBlue(final String label) {
    final var labelCell =
        getQpdfPCell(
            new Paragraph(
                label + ":", new Font(REPORT_FONT, 10, Font.BOLD, REPORT_DARK_BLUE_COLOR)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);
    ;

    return labelCell;
  }

  private PdfPCell getClarificationTableValueCell(final BigDecimal value, final String language) {
    final var nonNullValue = value == null ? ZERO : value;
    final var color = nonNullValue.compareTo(ZERO) >= 0 ? REPORT_GREEN_COLOR : REPORT_RED_COLOR;
    final var valueCell =
        getQpdfPCell(
            new Paragraph(
                amountWithCurrency(value, language), new Font(REPORT_FONT, 10, Font.BOLD, color)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);

    return valueCell;
  }

  private PdfPCell getClarificationTableValueCellDarkGray(
      final BigDecimal value, final String language) {
    final var formattedValue = amountWithCurrency(value, language);
    final var valueCell =
        getQpdfPCell(
            new Paragraph(
                formattedValue, new Font(REPORT_FONT, 10, Font.BOLD, REPORT_DARK_GRAY_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);
    ;

    return valueCell;
  }

  private PdfPCell getClarificationTableLabelCellMultiColor(
      final String text, final String language) {
    final var paragraph = new Paragraph();

    // Добавляем "Заработок" темно-синим цветом
    final var earningsText =
        new Chunk(" - Заработок ", new Font(REPORT_FONT, 10, Font.BOLD, REPORT_DARK_BLUE_COLOR));
    paragraph.add(earningsText);

    // Добавляем "Bolt" зеленым цветом
    final var boltText =
        new Chunk("Bolt:", new Font(REPORT_FONT, 10, Font.BOLD, REPORT_GREEN_COLOR));
    paragraph.add(boltText);

    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    return labelCell;
  }

  private PdfPCell getClarificationTableLabelCellCampaign(
      final String campaignName, final String language) {
    final var paragraph = new Paragraph();

    // Добавляем "Кампания " черным цветом
    final var campaignText =
        new Chunk(
            " - " + getLabel(language, BONUS_PROGRAM_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL, BLACK));
    paragraph.add(campaignText);

    // Добавляем название кампании в кавычках темно-синим цветом
    final var campaignNameText =
            new Chunk(
                    format("«%s»" + ":", campaignName),
                    new Font(REPORT_FONT, 10, Font.BOLD, REPORT_DARK_BLUE_COLOR));
    paragraph.add(campaignNameText);


    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    return labelCell;
  }

  private PdfPTable getClarificationTable() {
    final var table = getQpdfTable(2);
    table.setWidths(new int[] {70, 30});
    return table;
  }

  private PdfPTable getRentClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var currency = getLabel(language, CURRENCY_NAME_KEY);
    final var rentAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE);
    final var bonusReliablePartnerAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BONUS_RELIABLE_PARTNER_CODE);
    final var bonusBoltAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BONUS_BOLT_CODE);
    final var bonusPlusAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BONUS_PLUS_CODE);
    final var bonusFriendAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BONUS_FRIEND_CODE);
    final var bonusNewDriverAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BONUS_NEW_DRIVER_CODE);

    final var totalRentAmount = formatAmount(model.getTotalRentAmount().abs());

    final var table = getClarificationTable();
    final var headerPhrase = new com.lowagie.text.Phrase();
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * " + getLabel(language, RENT_HEADER_TEXT_LABEL_KEY),
            new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            format("%s %s", totalRentAmount, currency),
            new Font(REPORT_FONT, 12, Font.BOLD, REPORT_RED_COLOR)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));

    table.addCell(getClarificationTableHeaderCell(headerPhrase));

    final var weekRentLabelCell =
        getClarificationTableLabelCellDarkBlue(
            "- " + getLabel(language, RENT_CLARIFICATION_TEXT_LABEL_KEY));

    final var rentValueCell = getClarificationTableValueCell(rentAmount, language);
    addRowIfValueIsNonZero(rentAmount, weekRentLabelCell, rentValueCell, table);

    final var bonusReliablePartnerLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabel(language, BONUS_PROGRAM_REL_PARTNER_LABEL_KEY), language);
    final var bonusReliablePartnerValueCell =
        getClarificationTableValueCell(bonusReliablePartnerAmount, language);
    addRowIfValueIsNonZero(
        bonusReliablePartnerAmount,
        bonusReliablePartnerLabelCell,
        bonusReliablePartnerValueCell,
        table);
    final var bonusBoltLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabel(language, BONUS_PROGRAM_BOLT_RIDES_LABEL_KEY), language);
    final var bonusBoltValueCell = getClarificationTableValueCell(bonusBoltAmount, language);
    addRowIfValueIsNonZero(bonusBoltAmount, bonusBoltLabelCell, bonusBoltValueCell, table);

    final var bonusPlusLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabel(language, BONUS_PROGRAM_PLUS_LABEL_KEY), language);
    final var bonusPlusValueCell = getClarificationTableValueCell(bonusPlusAmount, language);
    addRowIfValueIsNonZero(bonusPlusAmount, bonusPlusLabelCell, bonusPlusValueCell, table);

    final var bonusFriendLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabel(language, BONUS_PROGRAM_FRIEND_REF_LABEL_KEY), language);
    final var bonusFriendValueCell = getClarificationTableValueCell(bonusFriendAmount, language);
    addRowIfValueIsNonZero(bonusFriendAmount, bonusFriendLabelCell, bonusFriendValueCell, table);
    table.addCell(getEmptyRow());

    final var bonusNewDriverLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabel(language, BONUS_PROGRAM_NEW_DRIVER_LABEL_KEY), language);
    final var bonusNewDriverValueCell =
        getClarificationTableValueCell(bonusNewDriverAmount, language);
    addRowIfValueIsNonZero(bonusNewDriverAmount, bonusNewDriverLabelCell, bonusNewDriverValueCell, table);
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPCell getEmptyRow() {
    final var emptyRow = getQpdfPCell(new Paragraph(" ", new Font(REPORT_FONT, 14, Font.BOLD)));
    emptyRow.setColspan(2);
    emptyRow.setFixedHeight((float) 8.0);

    return emptyRow;
  }

  private PdfPTable getRentAdjustmentClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getClarificationTable();
    final var correctionOfRent = formatAmount(model.getIncomeTotal());
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var headerPhrase = new com.lowagie.text.Phrase();

    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * " + getLabel(language, RENT_ADJUSTMENT_LABEL_KEY),
            new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            format("%s %s", correctionOfRent, euroCurrency),
            new Font(REPORT_FONT, 12, Font.BOLD, REPORT_GREEN_COLOR)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));
    final var boltPlusAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BOLT_PLUS_CODE);
    table.addCell(getClarificationTableHeaderCell(headerPhrase));

    final var paragraph = new Paragraph();
    final var incomeText =
        new Chunk(
            " - " + getLabel(language, RENT_ADJUSTMENT_BOLT_INCOME_1_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL, REPORT_DARK_BLUE_COLOR));
    paragraph.add(incomeText);

    // Добавляем название кампании в кавычках Green цветом
    final var incomeCompany =
        new Chunk(
            getLabel(language, RENT_ADJUSTMENT_BOLT_INCOME_2_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.BOLD, REPORT_GREEN_COLOR));
    paragraph.add(incomeCompany);
    //
    final var doted =
            new Chunk(
                    format(":"),
                    new Font(REPORT_FONT, 10, Font.BOLD));
    paragraph.add(doted);




    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);
    final var boltPlusValueCell = getClarificationTableValueCell(boltPlusAmount, language);
    addRowIfValueIsNonZero(boltPlusAmount, labelCell, boltPlusValueCell, table);
    table.addCell(getEmptyRow());






    return table;
  }

  private void addRowIfValueIsNonZero(
      final BigDecimal value,
      final PdfPCell labelCell,
      final PdfPCell valueCell,
      final PdfPTable table) {
    if (value != null && value.compareTo(ZERO) != 0) {
      table.addCell(labelCell);
      table.addCell(valueCell);
    }
  }

  private PdfPTable getOtherPaymentClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var otherObligations = formatAmount(model.getTotalOtherPaymentAmount().abs());
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var headerPhrase = new com.lowagie.text.Phrase();
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * " + getLabel(language, OTHER_OBLIGATIONS_LABEL_KEY),
            new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            format("%s %s", otherObligations, euroCurrency),
            new Font(REPORT_FONT, 12, Font.BOLD, REPORT_RED_COLOR)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));
    final var table = getClarificationTable();
    table.addCell(getClarificationTableHeaderCell(headerPhrase));
    var innerInsuranceAmount =
        model
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE, ZERO);
    innerInsuranceAmount =
        innerInsuranceAmount.add(
            model
                .getTransactionTypesVsAmount()
                .getOrDefault(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_MANUAL_CODE, ZERO));

    final var innerInsuranceLabelCell =
        getClarificationTableLabelCellDarkBlue(getLabel(language, ADD_INN_INSURANCE_LABEL_KEY));
    final var innerInsuranceValueCell =
        getClarificationTableValueCell(innerInsuranceAmount, language);
    addRowIfValueIsNonZero(
        innerInsuranceAmount, innerInsuranceLabelCell, innerInsuranceValueCell, table);

    final var nonLabelFineAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_NO_LABEL_FINE_CODE, ZERO);
    final var nonLabelFineLabelCell =
        getClarificationTableLabelCellDarkBlue(getLabel(language, NON_LABEL_FINE_LABEL_KEY));

    final var nonLabelFineValueCell = getClarificationTableValueCell(nonLabelFineAmount, language);
    addRowIfValueIsNonZero(nonLabelFineAmount, nonLabelFineLabelCell, nonLabelFineValueCell, table);

    final var distributedObligationAmount = model.getDistributedObligationAmount().negate();
    final var distributedObligationLabelCell =
        getClarificationTableLabelCellDarkBlue(
            getLabel(language, DISTRIBUTED_OBLIGATION_LABEL_KEY));

    final var distributedObligationValueCell =
        getClarificationTableValueCell(distributedObligationAmount, language);
    addRowIfValueIsNonZero(
        distributedObligationAmount,
        distributedObligationLabelCell,
        distributedObligationValueCell,
        table);

    final var parkingFineAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_PARKING_FINE_CODE, ZERO);
    final var parkingFineLabelCell =
        getClarificationTableLabelCellDarkBlue(getLabel(language, PARKING_FINE_LABEL_KEY));
    final var parkingFineValueCell = getClarificationTableValueCell(parkingFineAmount, language);
    addRowIfValueIsNonZero(parkingFineAmount, parkingFineLabelCell, parkingFineValueCell, table);

    final var feeAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_FEE_DEBT_CODE, ZERO);
    final var feeLabelCell =
        getClarificationTableLabelCellDarkBlue(getLabel(language, FEE_WEEK_BEGINNING_LABEL_KEY));
    final var feeValueCell = getClarificationTableValueCell(feeAmount, language);
    addRowIfValueIsNonZero(feeAmount, feeLabelCell, feeValueCell, table);

    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getDemandOnTheBeginningOfWeek(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getClarificationTable();
    final var headerPhrase = new com.lowagie.text.Phrase();
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * " + getLabel(language, DEMAND_ON_BEGINNING_OF_WEEK_LABEL_KEY),
            new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));

    table.addCell(getClarificationTableHeaderCell(headerPhrase));
    table.addCell(getClarificationTableLabelCellDarkBlue(getLabel(language, DEMAND_FEE_LABEL_KEY)));
    table.addCell(
        getClarificationTableValueCell(model.getFeeAmountAtCalculationMoment(), language));

    table.addCell(
        getClarificationTableLabelCellDarkBlue(
            getLabel(
                language,
                DEMAND_DEBT_WITHOUT_REPAIRMENT_LABEL_KEY))); // из баланса на понедельник ( при
    // условии что есть
    // долг)
    table.addCell(getClarificationTableValueCell(model.getDebtAmountSunday(), language));

    model
        .getInsuranceCases()
        .forEach(
            insuranceCase -> {
              final var occurrenceDate = formatDate(insuranceCase.getOccurrenceDate());
              final var insuranceCaseInfo =
                  format(
                      "Ремонт / Сумма франшизы - %s (дата происшествия: %s)",
                      insuranceCase.getCarRegNumber(), occurrenceDate);
              table.addCell(getClarificationTableLabelCell(insuranceCaseInfo));
              table.addCell(
                  getClarificationTableValueCellDarkGray(
                      insuranceCase.getDamageRemaining(), language));
            });
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getTotalBlock(final WeeklyReportPdfModel model) {
    final var table = getQpdfTable(2);
    final var language = model.getLanguage();
    final var labelCell =
        getQpdfPCell(
            new Paragraph(
                getLabel(language, TOTAL_PAYMENT_LABEL_KEY) + ":",
                new Font(REPORT_FONT, 14, Font.BOLD, BLACK)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    // labelCell.setBorderWidthTop(1f);
    labelCell.setFixedHeight(30f);

    labelCell.setPaddingTop(7f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    table.addCell(labelCell);
    final var totalPaymentAmountFormatted =
        amountWithCurrency(model.getTotalPaymentAmount(), language);
    final var valueCell =
        getQpdfPCell(
            new Paragraph(
                totalPaymentAmountFormatted,
                new Font(REPORT_FONT, 14, Font.BOLD, REPORT_RED_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);

    // valueCell.setBorderWidthTop(1f);
    valueCell.setFixedHeight(30f);

    valueCell.setPaddingTop(7f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

    table.addCell(valueCell);

    return table;
  }

  private PdfPCell getClarificationTableHeaderCell(final String headerText) {
    final var tableHeaderCell =
        getQpdfPCell(new Paragraph(headerText, new Font(REPORT_FONT, 12, Font.BOLD, BLACK)));
    tableHeaderCell.setHorizontalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(35f);
    tableHeaderCell.setPaddingTop(9f);
    tableHeaderCell.setColspan(2);
    tableHeaderCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    return tableHeaderCell;
  }

  private PdfPCell getClarificationTableHeaderCell(final com.lowagie.text.Phrase headerPhrase) {
    final var tableHeaderCell = getQpdfPCell(new Paragraph(headerPhrase));
    tableHeaderCell.setHorizontalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(35f);
    tableHeaderCell.setPaddingTop(9f);
    tableHeaderCell.setColspan(2);
    tableHeaderCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

    return tableHeaderCell;
  }

  private static String formatDate(final LocalDate date) {
    if (date == null) {

      return "";
    }
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");

    return date.format(formatter);
  }

  private String getInterval(final LocalDate start, final LocalDate end) {

    return "(%s - %s)".formatted(formatDate(start), formatDate(end));
  }

  private static String formatAmount(final BigDecimal amount) {
    if (amount == null) {
      return "0.00";
    }
    return format(Locale.US, "%,.2f", amount);
  }

  private static String amountWithCurrency(final BigDecimal amount, final String language) {
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    var nonNullAmount = amount;
    if (amount == null) {
      nonNullAmount = ZERO;
    }
    final var formattedAmount = formatAmount(nonNullAmount.abs());

    return format("%s %s", formattedAmount, euroCurrency);
  }
}
