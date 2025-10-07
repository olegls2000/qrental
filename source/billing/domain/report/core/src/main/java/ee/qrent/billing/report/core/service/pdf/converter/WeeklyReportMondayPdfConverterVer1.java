package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.Element.*;
import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProviderVer1.*;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.*;
import static java.awt.Color.*;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.lowagie.text.*;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
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
public class WeeklyReportMondayPdfConverterVer1 implements WeeklyReportPdfConversionStrategy {

  public static final int REPORT_FONT = HELVETICA;
  private static final Color REPORT_GRAY_BACKGROUND_COLOR = new Color(241, 241, 241);
  private static final Color REPORT_WHITE_BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color REPORT_GREEN_COLOR = new Color(0, 100, 0); // Green
  private static final Color REPORT_RED_COLOR = new Color(150, 30, 0); // Red
  private static final Color REPORT_DARK_BLUE_COLOR = new Color(0, 40, 120); // Dark Blue
  private static final Color REPORT_DARK_GRAY_COLOR = new Color(50, 50, 50); // Dark Gray
  private static final Color REPORT_MIDDLE_GRAY_COLOR = new Color(100, 100, 100); // Middle Gray
  private static final Color REPORT_PURPLE_COLOR = new Color(128, 0, 128); // Purple

  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.MONDAY_REPORT;
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
    weeklyReportPdfDoc.add(getClarificationHeaderRow("Данные на конец четверга прошлой недели"));
    //weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getBonusStatus(model));
    weeklyReportPdfDoc.add(getObligationOutcomeAboutCurrentWeekTable(model));
    weeklyReportPdfDoc.add(
        getClarificationHeaderRowColored("Ниже детальная информация по ", "твоим обязательствам"));
   // weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getRentClarificationTable(model));
   // weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getExternalSystemsIncomeClarificationTable(model));
  //  weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getOtherPaymentClarificationTable(model));
  //  weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getClarificationBlock4(model));
  //  weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getTotalBlock(model));
    weeklyReportPdfDoc.add(getCommentRowTable());
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }

  private PdfPTable getLineSeparator() {
    final var lineSeparator = getQpdfTable(1);
    final var qCell = new PdfPCell();
    qCell.setFixedHeight(1);
    qCell.setBackgroundColor(REPORT_MIDDLE_GRAY_COLOR);
    qCell.setBorder(1);
    lineSeparator.addCell(qCell);
    return lineSeparator;
  }

  private PdfPTable getCommentRowTable() {
    final var row = getQpdfTable(1);

    final var cell =
        getQpdfPCell(
            new Paragraph(
                "Данные в этой рассылке являются информативными, сами по себе не налагают ни на одну из сторон никаких обязательств и меняются по мере занесения их в систему",
                new Font(REPORT_FONT, 9)));
    cell.setHorizontalAlignment(ALIGN_LEFT);
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

    Image img = Image.getInstance("Images/qRentalGroup_gorznt.png");
    img.scaleToFit(33, 14);

    PdfPCell imageCell = new PdfPCell(img, true);
    imageCell.setHorizontalAlignment(ALIGN_LEFT);
    imageCell.setVerticalAlignment(ALIGN_MIDDLE);
    imageCell.setFixedHeight(20f);
    imageCell.setBorder(NO_BORDER);
    header.addCell(imageCell);
    final var reportNameCell =
        getQpdfPCell(
            new Paragraph(getLabel(language, REPORT_NAME_KEY), new Font(REPORT_FONT, 14, BOLD)));

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
    table.addCell(getDriverMainDataLabelCell(getLabel(language, ID_NUMBER_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(taxNumber));

    table.addCell(getDriverMainDataLabelCell(getLabel(language, CALL_SIGN_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(model.getCallSign().toString()));

    table.addCell(getDriverMainDataLabelCell(getLabel(language, RENTED_CAR_LABEL_KEY)));
    table.addCell(getDriverMainDataValueCell(model.getCarRegistrationNumber()));

    return table;
  }

  private Chunk getNormalChunk(final String text) {
    return new Chunk(text + " ", new Font(REPORT_FONT, 10, NORMAL));
  }

  private Chunk getBoldChunk(final String text) {
    return new Chunk(text + " ", new Font(REPORT_FONT, 10, BOLD));
  }

  private PdfPTable getBonusStatus(final WeeklyReportPdfModel model) {
    final var table = getQpdfTable(1);

    final var paragraph1 = new Paragraph();
    paragraph1.add(getNormalChunk("Ты"));
    paragraph1.add(
        model.getObligationStatus().equals("COMPLETED")
            ? getBoldChunk("выполнил")
            : getBoldChunk("не выполнил"));

    paragraph1.add(getNormalChunk("свои обязательства за прошлую неделю"));
    final var weekDaysFormatted =
        getInterval(model.getPreviousWeekStart(), model.getPreviousWeekEnd());
    paragraph1.add(getBoldChunk(weekDaysFormatted));
    paragraph1.add(getNormalChunk("своевременно и в полном объеме."));

    final var cell1 = getQpdfPCell(paragraph1);
    cell1.setHorizontalAlignment(ALIGN_CENTER);
    cell1.setVerticalAlignment(ALIGN_MIDDLE);
  //  cell1.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    table.addCell(cell1);

    final var paragraph2 = new Paragraph();
    paragraph2.add(
        model.getObligationStatus().equals("COMPLETED")
            ? getNormalChunk("Твоя предоплата")
            : getNormalChunk("Твой долг"));
    paragraph2.add(getNormalChunk("на конец четверга прошлой недели:"));
    final var language = model.getLanguage();
    final var netAmountOnThursday = amountWithCurrency(model.getNetAmountOnThursday(), language);

    final var amountColor =
        model.getNetAmountOnThursday().compareTo(ZERO) >= 0 ? REPORT_RED_COLOR : REPORT_GREEN_COLOR;
    paragraph2.add(new Chunk(netAmountOnThursday, new Font(REPORT_FONT, 10, NORMAL, amountColor)));
    final var cell2 = getQpdfPCell(paragraph2);
    cell2.setHorizontalAlignment(ALIGN_CENTER);
    cell2.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cell2);

    final var paragraphWithBonuses = new Paragraph();
    paragraphWithBonuses.add(getNormalChunk("В знак нашей благодарности"));
    paragraphWithBonuses.add(getBoldChunk("мы активировали"));
    paragraphWithBonuses.add(getNormalChunk("все наши"));
    paragraphWithBonuses.add(getBoldChunk("бонусные кампании"));
    paragraphWithBonuses.add(getNormalChunk("на текущей неделе."));

    final var paragraphWithoutBonuses = new Paragraph();
    paragraphWithoutBonuses.add(getNormalChunk("К сожалению, наши"));
    paragraphWithoutBonuses.add(getBoldChunk("бонусные кампании не будут"));
    paragraphWithoutBonuses.add(getNormalChunk("для тебя"));
    paragraphWithoutBonuses.add(getBoldChunk("доступны"));
    paragraphWithoutBonuses.add(getNormalChunk("на текущей неделе."));

    final var paragraph =
        model.getObligationStatus().equals("COMPLETED")
            ? paragraphWithBonuses
            : paragraphWithoutBonuses;

    final var cell3 = getQpdfPCell(paragraph);
    cell3.setHorizontalAlignment(ALIGN_CENTER);
    cell3.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cell3);

    final var paragraphEmpty = new Paragraph();
    final var cellEmpty = getQpdfPCell(paragraphEmpty);
    cellEmpty.setHorizontalAlignment(ALIGN_CENTER);
    cellEmpty.setVerticalAlignment(ALIGN_MIDDLE);
    table.addCell(cellEmpty);

    final var row = getQpdfTable(1);

    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 14, BOLD)));
    row.addCell(paddingTopCell);

    final var labelBeginning =
        format(
            "Согласно последним данным, внесенным в нашу программу на конец четверга прошлой недели, твои обязательства перед 'Q Takso Veod OÜ' за прошлую неделю %s ",
            weekDaysFormatted);

    final var obligationMatchText =
        format(
            "были выполнены своевременно и в полном объеме – согласно условиям твоего договора. Твоё сальдо на конец четверга прошлой недели %s составило: %s в виде предоплаты. Эта предоплата учтена при рассчете твоих последующих обязательств. В знак нашей благодарности мы активировали все наши еженедельные бонусные кампании в твоем аккаунте",
            weekDaysFormatted, netAmountOnThursday);

    final var obligationMissMatchText =
        format(
            "не были выполнены своевременно и в полном объеме. Твое сальдо на конец прошлой недели %s составило: %s в виде долга. К сожалению, по причине этого наши еженедельные бонусных кампании не будут для тебя доступны, а сам долг будет учтен при рассчете твоих последующих обязательств.",
            weekDaysFormatted, netAmountOnThursday);
    final var obligationText =
        model.getObligationStatus().equals("COMPLETED")
            ? labelBeginning.concat(obligationMatchText)
            : labelBeginning.concat(obligationMissMatchText);
    final var cell = getQpdfPCell(new Paragraph(obligationText, new Font(REPORT_FONT, 9)));
    cell.setHorizontalAlignment(ALIGN_LEFT);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    cell.setFixedHeight(55f);
    row.addCell(cell);

    return table;
  }

  private PdfPTable getObligationOutcomeAboutCurrentWeekTable(final WeeklyReportPdfModel model) {
    final var row = getQpdfTable(1);
    final var nextWeekDaysFormatted = getInterval(model.getNextWeekStart(), model.getNextWeekEnd());
    final var paragraph = new Paragraph();
    paragraph.add(new Chunk("Cейчас ", new Font(REPORT_FONT, 10, BOLD)));
    paragraph.add(
        new Chunk("твои обязательства", new Font(REPORT_FONT, 10, BOLD, REPORT_PURPLE_COLOR)));
    paragraph.add(new Chunk(" за текущую неделю составляют: ", new Font(REPORT_FONT, 10, BOLD)));
    paragraph.add(
        new Chunk(
            amountWithCurrency(model.getTotalPaymentAmount(), model.getLanguage()),
            new Font(REPORT_FONT, 10)));
    paragraph.add(
        new Chunk(
            "\nПожалуйста, оплати эту сумму до 16:00 завтрашнего дня, чтобы активировать бонусные кампании  \n",
            new Font(REPORT_FONT, 10)));
    paragraph.add(
        new Chunk(
            format("на следующую неделю %s", nextWeekDaysFormatted), new Font(REPORT_FONT, 10)));

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getClarificationHeaderRow(final String text) {
    final var row = getQpdfTable(1);
    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 13, BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);
    final var cell = getQpdfPCell(new Paragraph(text + ":", new Font(REPORT_FONT, 13, BOLD)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
   // cell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getClarificationHeaderRowColored(
      final String prefixText, final String purpleText) {
    final var row = getQpdfTable(1);
    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 13, BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);

    final var paragraph = new Paragraph();
    paragraph.add(new Chunk(prefixText, new Font(REPORT_FONT, 13, BOLD)));
    paragraph.add(new Chunk(purpleText, new Font(REPORT_FONT, 13, BOLD, REPORT_PURPLE_COLOR)));
    paragraph.add(new Chunk(":", new Font(REPORT_FONT, 13, BOLD)));

    final var cell = getQpdfPCell(paragraph);
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  private PdfPCell getClarificationTableLabelCell(final String label) {
    final var labelCell =
        getQpdfPCell(new Paragraph(label + ":", new Font(REPORT_FONT, 11, BOLD, BLACK)));
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
            new Paragraph(label + ":", new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_BLUE_COLOR)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);;

    return labelCell;
  }

  private PdfPCell getClarificationTableValueCell(final BigDecimal value, final String language) {
    final var nonNullValue = value == null ? ZERO : value;
    final var color = nonNullValue.compareTo(ZERO) >= 0 ? REPORT_GREEN_COLOR : REPORT_RED_COLOR;
    final var valueCell =
        getQpdfPCell(
            new Paragraph(
                amountWithCurrency(value, language), new Font(REPORT_FONT, 10, BOLD, color)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);
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
                formattedValue, new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_GRAY_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);;

    return valueCell;
  }

  private PdfPCell getClarificationTableLabelCellMultiColor(
      final String text, final String language) {
    final var paragraph = new Paragraph();

    // Добавляем "Заработок" темно-синим цветом
    final var earningsText =
        new Chunk(" - Заработок ", new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_BLUE_COLOR));
    paragraph.add(earningsText);

    // Добавляем "Bolt" зеленым цветом
    final var boltText = new Chunk("Bolt", new Font(REPORT_FONT, 10, BOLD, REPORT_GREEN_COLOR));
    paragraph.add(boltText);

    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

    return labelCell;
  }

  private PdfPCell getClarificationTableLabelCellCampaign(
      final String campaignName, final String language) {
    final var paragraph = new Paragraph();

    // Добавляем "Кампания " черным цветом
    final var campaignText = new Chunk(" - Кампания ", new Font(REPORT_FONT, 10, NORMAL, BLACK));
    paragraph.add(campaignText);

    // Добавляем название кампании в кавычках темно-синим цветом
    final var campaignNameText =
        new Chunk(campaignName, new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_BLUE_COLOR));
    paragraph.add(campaignNameText);

    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

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
    final var bonusFriendAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BONUS_FRIEND_CODE);

    final var totalRentAmount = formatAmount(model.getTotalRentAmount().abs());

    final var table = getClarificationTable();
    final var headerPhrase = new com.lowagie.text.Phrase();
    headerPhrase.add(
        new com.lowagie.text.Chunk(
                " * " + "Арендная плата всего: ", new Font(REPORT_FONT, 12, Font.BOLD , BLACK)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            format("%s %s" , totalRentAmount, currency), new Font(REPORT_FONT, 12, Font.BOLD , REPORT_RED_COLOR)));
    headerPhrase.add(
        new com.lowagie.text.Chunk(
            " * ", new Font(REPORT_FONT, 12, BOLD, BLACK)));


    table.addCell(getClarificationTableHeaderCell(headerPhrase));

    final var weekRentLabelCell =
        getClarificationTableLabelCellDarkBlue("- Аренда за текущую неделю");

    final var rentValueCell = getClarificationTableValueCell(rentAmount, language);
    addRowIfValueIsNonZero(rentAmount, weekRentLabelCell, rentValueCell, table);

    final var bonusReliablePartnerLabelCell =
        getClarificationTableLabelCellCampaign("«Надежный партнер»", language);
    final var bonusReliablePartnerValueCell =
        getClarificationTableValueCell(bonusReliablePartnerAmount, language);
    addRowIfValueIsNonZero(
        bonusReliablePartnerAmount,
        bonusReliablePartnerLabelCell,
        bonusReliablePartnerValueCell,
        table);
    final var bonusBoltLabelCell =
        getClarificationTableLabelCellCampaign("«Поездки Bolt»", language);
    final var bonusBoltValueCell = getClarificationTableValueCell(bonusBoltAmount, language);
    addRowIfValueIsNonZero(bonusBoltAmount, bonusBoltLabelCell, bonusBoltValueCell, table);
    final var bonusFriendLabelCell =
        getClarificationTableLabelCellCampaign("«Приведи друга»", language);
    final var bonusFriendValueCell = getClarificationTableValueCell(bonusFriendAmount, language);
    addRowIfValueIsNonZero(bonusFriendAmount, bonusFriendLabelCell, bonusFriendValueCell, table);
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPCell getEmptyRow() {
    final var emptyRow = getQpdfPCell(new Paragraph(" ", new Font(REPORT_FONT, 14, Font.BOLD)));
    emptyRow.setColspan(2);
    emptyRow.setFixedHeight((float) 8.0);

    return emptyRow;
  }

  private PdfPTable getExternalSystemsIncomeClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getClarificationTable();
    final var correctionOfRent = formatAmount(model.getTotalExternalSystemsIncomeAmount());
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var headerText =
        format("Коррекция аренды: %s %s", correctionOfRent, euroCurrency)
            + " ";
    final var boltPlusAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BOLT_PLUS_CODE);
    table.addCell(getClarificationTableHeaderCell(headerText));
    final var boltPlusLabelCell =
        getClarificationTableLabelCellMultiColor( " Заработок Bolt  ", language);
    final var boltPlusValueCell = getClarificationTableValueCellDarkGray(boltPlusAmount, language);
    addRowIfValueIsNonZero(boltPlusAmount, boltPlusLabelCell, boltPlusValueCell, table);
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
    final var otherObligationsText =
        format("Прочие обязательства: %s %s . ", otherObligations, euroCurrency)
            + "";
    final var table = getClarificationTable();
    table.addCell(getClarificationTableHeaderCell(otherObligationsText));
    final var depositAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_DEPOSIT_CODE, ZERO);
    final var depositLabelCell = getClarificationTableLabelCellDarkBlue("Залог");
    final var depositValueCell = getClarificationTableValueCell(depositAmount, language);
    addRowIfValueIsNonZero(depositAmount, depositLabelCell, depositValueCell, table);

    final var innerInsuranceAmount =
        model
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE, ZERO);
    final var innerInsuranceLabelCell =
        getClarificationTableLabelCellDarkBlue(
            "ДВС за текущую неделю (дополнительное внутреннее страхование)");
    final var innerInsuranceValueCell =
        getClarificationTableValueCell(innerInsuranceAmount, language);
    addRowIfValueIsNonZero(
        innerInsuranceAmount, innerInsuranceLabelCell, innerInsuranceValueCell, table);

    final var nonLabelFineAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_NO_LABEL_FINE_CODE, ZERO);
    final var nonLabelFineLabelCell =
        getClarificationTableLabelCellDarkBlue(
            "Доплата за отсутствие логотипов Q на автомобиле за текущую неделю");

    final var nonLabelFineValueCell = getClarificationTableValueCell(nonLabelFineAmount, language);
    addRowIfValueIsNonZero(nonLabelFineAmount, nonLabelFineLabelCell, nonLabelFineValueCell, table);

    final var parkingFineAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_PARKING_FINE_CODE, ZERO);
    final var parkingFineLabelCell = getClarificationTableLabelCellDarkBlue("Штраф за парковку");
    final var parkingFineValueCell = getClarificationTableValueCell(parkingFineAmount, language);
    addRowIfValueIsNonZero(parkingFineAmount, parkingFineLabelCell, parkingFineValueCell, table);

    final var feeAmount =
        model.getTransactionTypesVsAmount().getOrDefault(TRANSACTION_TYPE_FEE_DEBT_CODE, ZERO);
    final var feeLabelCell = getClarificationTableLabelCellDarkBlue("Пени на конец прошлой недели");
    final var feeValueCell = getClarificationTableValueCell(feeAmount, language);
    addRowIfValueIsNonZero(feeAmount, feeLabelCell, feeValueCell, table);

    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getClarificationBlock4(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getClarificationTable();
    final var damagePayment =
        formatAmount(model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_DAMAGE_PAYMENT_CODE));
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var headerText =
        format(
                "Требование в счет обязательств на начало недели: %s %s",
                damagePayment, euroCurrency)
            + " ";
    table.addCell(getClarificationTableHeaderCell(headerText));
    table.addCell(getClarificationTableLabelCellDarkBlue("Пени"));
    table.addCell(
        getClarificationTableValueCell(model.getFeeAmountAtCalculationMoment(), language));

    table.addCell(
        getClarificationTableLabelCellDarkBlue(
            "Общий долг (без учета ремонтов)")); // из баланса на понедельник ( при условии что есть
    // долг)
    table.addCell(
        getClarificationTableValueCell(model.getBalanceAmountAtCalculationMoment(), language));

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
        getQpdfPCell(new Paragraph("Итого к оплате:", new Font(REPORT_FONT, 14, BOLD, BLACK)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(30f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);
    table.addCell(labelCell);
    final var totalPaymentAmountFormatted =
        amountWithCurrency(model.getTotalPaymentAmount(), language);
    final var valueCell =
        getQpdfPCell(
            new Paragraph(totalPaymentAmountFormatted, new Font(REPORT_FONT, 14, BOLD, REPORT_RED_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(30f);

    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    table.addCell(valueCell);

    return table;
  }

  private PdfPCell getClarificationTableHeaderCell(final String headerText) {
    final var tableHeaderCell =
        getQpdfPCell(new Paragraph(headerText, new Font(REPORT_FONT, 12, BOLD, BLACK)));
    tableHeaderCell.setHorizontalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(35f);
    tableHeaderCell.setPaddingTop(9f);
    tableHeaderCell.setColspan(2);
    tableHeaderCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

    return tableHeaderCell;
  }

  private PdfPCell getClarificationTableHeaderCell(final com.lowagie.text.Phrase headerPhrase) {
    final var tableHeaderCell =
            getQpdfPCell(new Paragraph(headerPhrase));
    tableHeaderCell.setHorizontalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(35f);
    tableHeaderCell.setPaddingTop(9f);
    tableHeaderCell.setColspan(2);
    tableHeaderCell.setBackgroundColor(REPORT_WHITE_BACKGROUND_COLOR);

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
