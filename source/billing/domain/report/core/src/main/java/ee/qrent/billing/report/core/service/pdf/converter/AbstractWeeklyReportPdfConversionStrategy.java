package ee.qrent.billing.report.core.service.pdf.converter;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import lombok.SneakyThrows;

import java.awt.*;
import java.math.BigDecimal;

import static com.lowagie.text.Element.*;
import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportFormatUtils.*;
import static ee.qrent.billing.report.core.service.pdf.converter.WeeklyReportPdfDocumentUtils.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderCommon.*;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderTuesday.*;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.*;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_FEE_DEBT_CODE;
import static java.awt.Color.BLACK;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

abstract class AbstractWeeklyReportPdfConversionStrategy
    implements WeeklyReportPdfConversionStrategy {

  static final String OBLIGATION_STATUS_COMPLETED = "COMPLETED";

  @SneakyThrows
  PdfPTable getHeaderTable(final String reportName) {
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
        getQpdfPCell(new Paragraph(reportName, new Font(REPORT_FONT, 14, BOLD)));

    reportNameCell.setHorizontalAlignment(ALIGN_LEFT);
    reportNameCell.setPaddingLeft(65f);
    reportNameCell.setVerticalAlignment(ALIGN_MIDDLE);
    header.addCell(reportNameCell);
    header.addCell(getEmptyRow());

    return header;
  }

  PdfPTable getDriverMainDataTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(4);
    table.setWidths(new int[] {20, 30, 20, 30});
    // Row 1:
    table.addCell(getDriverMainDataLabelCell("Q firm"));
    table.addCell(getDriverMainDataValueCell(model.getQFirmName()));
    table.addCell(getDriverMainDataLabelCell(getLabelFromCommon(language, RENTER_LABEL_KEY)));
    final var driverName = "%s %s".formatted(model.getFirstName(), model.getLastName());
    table.addCell(getDriverMainDataValueCell(driverName));
    // Row 2:
    table.addCell(getDriverMainDataLabelCell("IBAN"));
    table.addCell(getDriverMainDataValueCell(model.getQFirmIban()));
    final var taxNumber = model.getIdNumber().toString();
    table.addCell(
        getDriverMainDataLabelCell(getLabelFromCommon(language, PERSONAL_NUMBER_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(taxNumber));

    // Row 3:
    table.addCell(getDriverMainDataLabelCell("Q contact"));
    table.addCell(getDriverMainDataValueCell(model.getQFirmContact()));
    table.addCell(getDriverMainDataLabelCell(getLabelFromCommon(language, CALL_SIGN_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(model.getCallSign().toString()));
    // Row 4:
    table.addCell(getDriverMainDataLabelCell("Reported week"));
    final var reportedWeekDaysFormatted =
        formatInterval(model.getCurrentWeekStart(), model.getCurrentWeekEnd());
    table.addCell(getDriverMainDataValueCell(reportedWeekDaysFormatted));
    table.addCell(getDriverMainDataLabelCell(getLabelFromCommon(language, RENTED_CAR_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(model.getCarRegistrationNumber()));

    return table;
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
    final var labelCell = getQpdfPCell(new Paragraph(value, new Font(REPORT_FONT, 12, BOLD)));
    labelCell.setHorizontalAlignment(ALIGN_LEFT);
    labelCell.setVerticalAlignment(ALIGN_MIDDLE);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    labelCell.setFixedHeight(20f);
    labelCell.setPaddingLeft(8f);

    return labelCell;
  }

  PdfPTable getClarificationHeaderRow(final String text) {
    final var row = getQpdfTable(1);
    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 13, BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);
    final var cell = getQpdfPCell(new Paragraph(text + ":", new Font(REPORT_FONT, 13, BOLD)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  PdfPTable getPreviousWeekObligationStatusText(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);
    final var paragraph = new Paragraph();
    paragraph.add(getNormalChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_1_LABEL_KEY)));
    paragraph.add(
        model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
            ? getBoldChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY))
            : getBoldChunk(
                getLabelFromCommon(language, OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY)));

    paragraph.add(getNormalChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_3_LABEL_KEY)));
    paragraph.add(getBoldChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_4_LABEL_KEY)));
    paragraph.add(getNormalChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_5_LABEL_KEY)));
    final var weekDaysFormatted =
        formatInterval(model.getPreviousWeekStart(), model.getPreviousWeekEnd());
    paragraph.add(getBoldChunk(weekDaysFormatted));
    paragraph.add(getNormalChunk(getLabelFromCommon(language, OBLIGATION_TEXT_PART_6_LABEL_KEY)));
    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell);
    table.addCell(getEmptyRow());

    return table;
  }

  PdfPTable gePreviousThursdayNetInfoText(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);

    final var paragraph = new Paragraph();
    paragraph.add(
        model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
            ? getNormalChunk(
                getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY))
            : getNormalChunk(
                getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY)));
    paragraph.add(
        getNormalChunk(getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY)));
    paragraph.add(
        getBoldChunk(getLabelFromCommon(language, THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY)));
    final var netAmountOnThursday =
        formatAmountWithCurrency(model.getNetAmountOnThursday(), language);
    final var amountColor =
        model.getObligationStatus().equals(OBLIGATION_STATUS_COMPLETED)
            ? REPORT_GREEN_COLOR
            : REPORT_RED_COLOR;
    paragraph.add(new Chunk(netAmountOnThursday, new Font(REPORT_FONT, 10, BOLD, amountColor)));
    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(getEmptyRow());
    table.addCell(cell);

    return table;
  }

  PdfPTable getBonusStatusText(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(1);

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

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cell);
    table.addCell(getEmptyRow());

    final var paragraphEmpty = new Paragraph();
    final var cellEmpty = getQpdfPCell(paragraphEmpty);
    cellEmpty.setHorizontalAlignment(ALIGN_CENTER);
    cellEmpty.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cellEmpty);

    final var row = getQpdfTable(1);
    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 14, BOLD)));
    row.addCell(paddingTopCell);

    return table;
  }

  PdfPTable getObligationOutcomeAboutCurrentWeekTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var row = getQpdfTable(1);
    final var nextWeekDaysFormatted =
        formatInterval(model.getNextWeekStart(), model.getNextWeekEnd());
    final var paragraph = new Paragraph();
    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY),
            new Font(REPORT_FONT, 10, BOLD)));
    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY),
            new Font(REPORT_FONT, 10, BOLD, REPORT_PURPLE_COLOR)));
    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY),
            new Font(REPORT_FONT, 10, BOLD)));
    paragraph.add(
        new Chunk(
            formatAmountWithCurrency(model.getTotalPaymentAmount(), model.getLanguage()),
            new Font(REPORT_FONT, 10, BOLD, REPORT_RED_COLOR)));
    paragraph.add(
        new Chunk(
            " \n \n " + getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));

    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));

    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY) + "\n",
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(
        new Chunk(
            getLabelFromCommon(language, OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL)));
    paragraph.add(new Chunk(nextWeekDaysFormatted, new Font(REPORT_FONT, 10, BOLD)));

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    row.addCell(cell);

    return row;
  }

  PdfPTable getRentAdjustmentClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getClarificationTable();
    final var correctionOfRent = formatAmount(model.getIncomeTotal());
    final var euroCurrency = getLabelFromCommon(language, CURRENCY_NAME_KEY);
    final var headerPhrase = new com.lowagie.text.Phrase();
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * " + getLabelFromCommon(language, RENT_ADJUSTMENT_LABEL_KEY),
            new Font(REPORT_FONT, 12, BOLD, BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            format("%s %s", correctionOfRent, euroCurrency),
            new Font(REPORT_FONT, 12, BOLD, REPORT_GREEN_COLOR)));
    headerPhrase.add(new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, BOLD, BLACK)));
    final var boltPlusAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BOLT_PLUS_CODE);
    table.addCell(getClarificationTableHeaderCell(headerPhrase));
    final var paragraph = new Paragraph();
    final var incomeText =
        new Chunk(
            " - " + getLabelFromCommon(language, RENT_ADJUSTMENT_BOLT_INCOME_1_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL, REPORT_DARK_BLUE_COLOR));
    paragraph.add(incomeText);
    final var incomeCompany =
        new Chunk(
            getLabelFromCommon(language, RENT_ADJUSTMENT_BOLT_INCOME_2_LABEL_KEY),
            new Font(REPORT_FONT, 10, BOLD, REPORT_GREEN_COLOR));
    paragraph.add(incomeCompany);

    final var doted = new Chunk(format(":"), new Font(REPORT_FONT, 10, BOLD));
    paragraph.add(doted);
    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);
    final var boltPlusValueCell = getClarificationTableValueCell(boltPlusAmount, language);
    addRowIfValueIsNonZero(boltPlusAmount, labelCell, boltPlusValueCell, table);

    final var incomeOthersAmount = model.getIncomeOthers();
    final var incomeOthersLabelCell =
        getClarificationTableLabelCellDarkBlue(
            getLabelFromCommon(language, RENT_ADJUSTMENT_OTHER_INCOME_LABEL_KEY));
    final var incomeOthersValueCell =
        getClarificationTableValueCell(model.getIncomeOthers(), language);
    addRowIfValueIsNonZero(incomeOthersAmount, incomeOthersLabelCell, incomeOthersValueCell, table);
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPCell getClarificationTableLabelCellDarkBlue(final String label) {
    final var labelCell =
        getQpdfPCell(
            new Paragraph(label + ":", new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_BLUE_COLOR)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    return labelCell;
  }

  PdfPCell getClarificationTableHeaderCell(final com.lowagie.text.Phrase headerPhrase) {
    final var tableHeaderCell = getQpdfPCell(new Paragraph(headerPhrase));
    tableHeaderCell.setHorizontalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(35f);
    tableHeaderCell.setPaddingTop(9f);
    tableHeaderCell.setColspan(2);
    tableHeaderCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

    return tableHeaderCell;
  }

  PdfPTable getClarificationTable() {
    final var table = getQpdfTable(2);
    table.setWidths(new int[] {70, 30});
    return table;
  }

  PdfPCell getClarificationTableValueCell(final BigDecimal value, final String language) {
    final var nonNullValue = value == null ? ZERO : value;
    final var color = nonNullValue.compareTo(ZERO) >= 0 ? REPORT_GREEN_COLOR : REPORT_RED_COLOR;
    final var valueCell =
        getQpdfPCell(
            new Paragraph(
                formatAmountWithCurrency(value, language), new Font(REPORT_FONT, 10, BOLD, color)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);

    return valueCell;
  }

  PdfPTable getClarificationHeaderRowColored(final String prefixText) {
    final var row = getQpdfTable(1);
    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 13, BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);
    final var paragraph = new Paragraph();
    paragraph.add(new Chunk(prefixText, new Font(REPORT_FONT, 13, BOLD)));
    paragraph.add(new Chunk(":", new Font(REPORT_FONT, 13, BOLD)));
    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  PdfPTable getOtherPaymentClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var otherObligations = formatAmount(model.getTotalOtherPaymentAmount().abs());
    final var euroCurrency = getLabelFromCommon(language, CURRENCY_NAME_KEY);
    final var headerPhrase = new com.lowagie.text.Phrase();
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * " + getLabelFromCommon(language, OTHER_OBLIGATIONS_LABEL_KEY),
            new Font(REPORT_FONT, 12, BOLD, BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            format("%s %s", otherObligations, euroCurrency),
            new Font(REPORT_FONT, 12, BOLD, REPORT_RED_COLOR)));
    headerPhrase.add(new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, BOLD, BLACK)));
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
        getClarificationTableLabelCell(
            getLabelFromCommon(language, ADD_INN_INSURANCE_LABEL_KEY), REPORT_DARK_BLUE_COLOR);
    final var innerInsuranceValueCell =
        getClarificationTableValueCell(innerInsuranceAmount, language);
    addRowIfValueIsNonZero(
        innerInsuranceAmount, innerInsuranceLabelCell, innerInsuranceValueCell, table);

    final var nonLabelFineAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_NO_LABEL_FINE_CODE, ZERO);
    final var nonLabelFineLabelCell =
        getClarificationTableLabelCell(
            getLabelFromCommon(language, NON_LABEL_FINE_LABEL_KEY), REPORT_DARK_BLUE_COLOR);

    final var nonLabelFineValueCell = getClarificationTableValueCell(nonLabelFineAmount, language);
    addRowIfValueIsNonZero(nonLabelFineAmount, nonLabelFineLabelCell, nonLabelFineValueCell, table);

    final var distributedObligationAmount = model.getDistributedObligationAmount(); // .negate()
    final var distributedObligationLabelCell =
        getClarificationTableLabelCell(
            getLabelFromCommon(language, DISTRIBUTED_OBLIGATION_LABEL_KEY), REPORT_DARK_BLUE_COLOR);

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
        getClarificationTableLabelCell(
            getLabelFromCommon(language, PARKING_FINE_LABEL_KEY), REPORT_DARK_BLUE_COLOR);
    final var parkingFineValueCell = getClarificationTableValueCell(parkingFineAmount, language);
    addRowIfValueIsNonZero(parkingFineAmount, parkingFineLabelCell, parkingFineValueCell, table);

    final var feeAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_FEE_DEBT_CODE, ZERO);
    final var feeLabelCell =
        getClarificationTableLabelCell(
            getLabelFromCommon(language, FEE_WEEK_BEGINNING_LABEL_KEY), REPORT_DARK_BLUE_COLOR);
    final var feeValueCell = getClarificationTableValueCell(feeAmount, language);
    addRowIfValueIsNonZero(feeAmount, feeLabelCell, feeValueCell, table);

    table.addCell(getEmptyRow());

    return table;
  }

  PdfPTable getDemandOnTheBeginningOfWeek(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getClarificationTable();
    final var headerPhrase = new com.lowagie.text.Phrase();
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * " + getLabelFromCommon(language, DEMAND_ON_BEGINNING_OF_WEEK_LABEL_KEY),
            new Font(REPORT_FONT, 12, BOLD, BLACK)));
    headerPhrase.add(new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, BOLD, BLACK)));

    table.addCell(getClarificationTableHeaderCell(headerPhrase));
    table.addCell(
        getClarificationTableLabelCell(
            getLabelFromCommon(language, DEMAND_FEE_LABEL_KEY), REPORT_DARK_BLUE_COLOR));
    table.addCell(
        getClarificationTableValueCell(model.getFeeAmountAtCalculationMoment(), language));

    table.addCell(
        getClarificationTableLabelCell(
            getLabelFromCommon(language, DEMAND_DEBT_WITHOUT_REPAIRMENT_LABEL_KEY),
            REPORT_DARK_BLUE_COLOR));
    table.addCell(
        getClarificationTableValueCell(model.getBalanceAmountAtCalculationMoment(), language));
    model
        .getInsuranceCases()
        .forEach(
            insuranceCase -> {
              final var occurrenceDate = formatDate(insuranceCase.getOccurrenceDate());
              final var insuranceCaseInfo =
                  format(
                      getLabelFromCommon(language, REPAIRMENT_1_LABEL_KEY)
                          + " - %s ("
                          + getLabelFromCommon(language, REPAIRMENT_2_LABEL_KEY)
                          + ": %s)",
                      insuranceCase.getCarRegNumber(),
                      occurrenceDate);
              table.addCell(getClarificationTableLabelCell(insuranceCaseInfo, BLACK));
              table.addCell(
                  getClarificationTableValueCellDarkGray(
                      insuranceCase.getDamageRemaining(), language));
            });
    table.addCell(getEmptyRow());

    return table;
  }

  PdfPTable getRentClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var currency = getLabelFromCommon(language, CURRENCY_NAME_KEY);
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
            " * " + getLabelFromCommon(language, RENT_HEADER_TEXT_LABEL_KEY),
            new Font(REPORT_FONT, 12, BOLD, BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            format("%s %s", totalRentAmount, currency),
            new Font(REPORT_FONT, 12, BOLD, REPORT_RED_COLOR)));
    headerPhrase.add(new com.lowagie.text.Chunk(" * ", new Font(REPORT_FONT, 12, BOLD, BLACK)));
    table.addCell(getClarificationTableHeaderCell(headerPhrase));
    final var weekRentLabelCell =
        getClarificationTableLabelCell(
            getLabelFromCommon(language, RENT_CLARIFICATION_TEXT_LABEL_KEY),
            REPORT_DARK_BLUE_COLOR);
    final var rentValueCell = getClarificationTableValueCell(rentAmount, language);
    addRowIfValueIsNonZero(rentAmount, weekRentLabelCell, rentValueCell, table);
    final var bonusReliablePartnerLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabelFromCommon(language, BONUS_PROGRAM_REL_PARTNER_LABEL_KEY), language);
    final var bonusReliablePartnerValueCell =
        getClarificationTableValueCell(bonusReliablePartnerAmount, language);
    addRowIfValueIsNonZero(
        bonusReliablePartnerAmount,
        bonusReliablePartnerLabelCell,
        bonusReliablePartnerValueCell,
        table);
    final var bonusBoltLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabelFromCommon(language, BONUS_PROGRAM_BOLT_RIDES_LABEL_KEY), language);
    final var bonusBoltValueCell = getClarificationTableValueCell(bonusBoltAmount, language);
    addRowIfValueIsNonZero(bonusBoltAmount, bonusBoltLabelCell, bonusBoltValueCell, table);
    final var bonusPlusLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabelFromCommon(language, BONUS_PROGRAM_PLUS_LABEL_KEY), language);
    final var bonusPlusValueCell = getClarificationTableValueCell(bonusPlusAmount, language);
    addRowIfValueIsNonZero(bonusPlusAmount, bonusPlusLabelCell, bonusPlusValueCell, table);
    final var bonusFriendLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabelFromCommon(language, BONUS_PROGRAM_FRIEND_REF_LABEL_KEY), language);
    final var bonusFriendValueCell = getClarificationTableValueCell(bonusFriendAmount, language);
    addRowIfValueIsNonZero(bonusFriendAmount, bonusFriendLabelCell, bonusFriendValueCell, table);
    table.addCell(getEmptyRow());
    final var bonusNewDriverLabelCell =
        getClarificationTableLabelCellCampaign(
            getLabelFromCommon(language, BONUS_PROGRAM_NEW_DRIVER_LABEL_KEY), language);
    final var bonusNewDriverValueCell =
        getClarificationTableValueCell(bonusNewDriverAmount, language);
    addRowIfValueIsNonZero(
        bonusNewDriverAmount, bonusNewDriverLabelCell, bonusNewDriverValueCell, table);
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPCell getClarificationTableLabelCellCampaign(
      final String campaignName, final String language) {
    final var paragraph = new Paragraph();

    final var campaignText =
        new Chunk(
            " - " + getLabelFromCommon(language, BONUS_PROGRAM_LABEL_KEY),
            new Font(REPORT_FONT, 10, Font.NORMAL, BLACK));
    paragraph.add(campaignText);

    final var campaignNameText =
        new Chunk(
            format("«%s»" + ":", campaignName),
            new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_BLUE_COLOR));
    paragraph.add(campaignNameText);

    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    return labelCell;
  }

  PdfPCell getClarificationTableLabelCell(final String label, final Color color) {
    final var labelCell =
        getQpdfPCell(new Paragraph("- " + label + ":", new Font(REPORT_FONT, 11, BOLD, color)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    // labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

    return labelCell;
  }

  PdfPCell getClarificationTableValueCellDarkGray(final BigDecimal value, final String language) {
    final var formattedValue = formatAmountWithCurrency(value, language);
    final var valueCell =
        getQpdfPCell(
            new Paragraph(formattedValue, new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_GRAY_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    return valueCell;
  }

  PdfPTable getTotalBlock(final WeeklyReportPdfModel model) {
    final var table = getQpdfTable(2);
    final var language = model.getLanguage();
    final var labelCell =
        getQpdfPCell(
            new Paragraph(
                getLabelFromCommon(language, TOTAL_PAYMENT_LABEL_KEY) + ":",
                new Font(REPORT_FONT, 14, BOLD, BLACK)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(30f);
    labelCell.setPaddingTop(7f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    table.addCell(labelCell);
    final var totalPaymentAmountFormatted =
        formatAmountWithCurrency(model.getTotalPaymentAmount(), language);

    final var color =
        model.getTotalPaymentAmount().compareTo(ZERO) == 0 ? REPORT_GREEN_COLOR : REPORT_RED_COLOR;

    final var valueCell =
        getQpdfPCell(
            new Paragraph(totalPaymentAmountFormatted, new Font(REPORT_FONT, 14, BOLD, color)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(30f);
    valueCell.setPaddingTop(7f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    table.addCell(valueCell);

    return table;
  }

  PdfPTable getCommentRowTable(final String language) {
    final var row = getQpdfTable(1);
    final var cell =
        getQpdfPCell(
            new Paragraph(
                getLabelFromCommon(language, COMMENT_LABEL_KEY),
                new Font(REPORT_FONT, 9, BOLD, BLACK)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    row.addCell(cell);

    return row;
  }
}
