package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.Element.*;
import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProviderVer1.*;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.*;
import static java.awt.Color.*;
import static java.lang.String.format;

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
  private static final Color GRAY_BACKGROUND_COLOR = new Color(241, 241, 241);
  private static final Color ORANGE_BACKGROUND_COLOR = new Color(240, 137, 40);
  private static final Color BLUE_BACKGROUND_COLOR = new Color(65, 111, 177);
  private static final Color CUSTOM_GREEN_COLOR = new Color(0, 100, 0); // Forest Green
  private static final Color CUSTOM_RED_COLOR = new Color(150, 30, 0); // Forest Green
  private static final Color CUSTOM_DARK_BLUE_COLOR = new Color(0, 40, 120); // Dark Blue
  private static final Color CUSTOM_DARK_GRAY_COLOR = new Color(50, 50, 50); // Dark Blue

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
    weeklyReportPdfDoc.add(getFinancialOutcomeAboutPreviousWeekTable(model));
    weeklyReportPdfDoc.add(getObligationOutcomeAboutCurrentWeekTable(model));
    weeklyReportPdfDoc.add(getClarificationlabel(model));
    weeklyReportPdfDoc.add(getRentClarificationTable(model));
    weeklyReportPdfDoc.add(getExternalSystemsIncomeClarificationTable(model));
    weeklyReportPdfDoc.add(getOtherPaymentClarificationTable(model));
    weeklyReportPdfDoc.add(getClarificationBlock4(model));
    weeklyReportPdfDoc.add(getTotalBlock(model));
    weeklyReportPdfDoc.add(getCommentRowTable());
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
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
    labelCell.setBackgroundColor(GRAY_BACKGROUND_COLOR);
    labelCell.setFixedHeight(20f);
    labelCell.setPaddingRight(2f);

    return labelCell;
  }

  private PdfPCell getDriverMainDataValueCell(final String value) {
    final var labelCell = getQpdfPCell(new Paragraph(value, new Font(REPORT_FONT, 12, Font.BOLD)));
    labelCell.setHorizontalAlignment(ALIGN_LEFT);
    labelCell.setVerticalAlignment(ALIGN_MIDDLE);
    labelCell.setBackgroundColor(GRAY_BACKGROUND_COLOR);
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


   /* table.addCell(getDriverMainDataLabelCell("До конца действия договора (календарных недель)"));
    table.addCell(getDriverMainDataValueCell(model.getWeeksCountTillEnd().toString()));

    table.addCell(getDriverMainDataLabelCell("Твой общий залог"));
    table.addCell(getDriverMainDataValueCell("N/A"));

    table.addCell(getDriverMainDataLabelCell("Востребованная часть залога"));
    table.addCell(getDriverMainDataValueCell("N/A"));*/

    return table;
  }

  private PdfPTable getFinancialOutcomeAboutPreviousWeekTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var row = getQpdfTable(1);

    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 14, BOLD)));
    row.addCell(paddingTopCell);
    final var currency = getLabel(language, CURRENCY_NAME_KEY);
    final var thursdayNet = format("%s %s", formatAmount(model.getNetAmountOnThursday()), currency);

    final var weekDaysFormatted =
        "(%s - %s)"
            .formatted(
                formatDate(model.getPreviousWeekStart()), formatDate(model.getPreviousWeekEnd()));
    final var labelBeginning =
        format(
            "Согласно последним данным, внесенным в нашу программу на конец четверга прошлой недели, твои обязательства перед 'Q Takso Veod OÜ' за прошлую неделю %s ",
            weekDaysFormatted);

    final var obligationMatchText =
        format(
            "были выполнены своевременно и в полном объеме – согласно условиям твоего договора. Твоё сальдо на конец четверга прошлой недели %s составило: %s в виде предоплаты. Эта предоплата учтена при рассчете твоих последующих обязательств. В знак нашей благодарности мы активировали все наши еженедельные бонусные кампании в твоем аккаунте",
            weekDaysFormatted, thursdayNet);

    final var obligationMissMatchText =
        format(
            "не были выполнены своевременно и в полном объеме. Твое сальдо на конец прошлой недели %s составило: %s в виде долга. К сожалению, по причине этого наши еженедельные бонусных кампании не будут для тебя доступны, а сам долг будет учтен при рассчете твоих последующих обязательств.",
            weekDaysFormatted, thursdayNet);
    final var obligationText =
        model.getObligationStatus().equals("COMPLETED")
            ? labelBeginning.concat(obligationMatchText)
            : labelBeginning.concat(obligationMissMatchText);
    final var cell = getQpdfPCell(new Paragraph(obligationText, new Font(REPORT_FONT, 9)));
    cell.setHorizontalAlignment(ALIGN_LEFT);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    cell.setFixedHeight(55f);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getObligationOutcomeAboutCurrentWeekTable(final WeeklyReportPdfModel model) {
    final var row = getQpdfTable(1);
    final var nextWeekDaysFormatted =
        "(%s - %s)"
            .formatted(formatDate(model.getNextWeekStart()), formatDate(model.getNextWeekEnd()));

    final var tomorrow = formatDate(model.getCurrentWeekStart().plusDays(1));

    final var currentObligationAmountFormatted = formatAmount(model.getCurrentObligationAmount());
    final var label =
        format(
            "В соответствии с этим твои обязательства перед Q Takso Veod OÜ за текущую неделю на текущий момент составляют: %s %s. Пожалуйста, оплати эту сумму до 16:00 следующего дня (%s) , и твои бонусные кампании на следующую неделю %s будут активированы",
            currentObligationAmountFormatted,
            getLabel(model.getLanguage(), CURRENCY_NAME_KEY),
            tomorrow,
            nextWeekDaysFormatted);

    final var cell = getQpdfPCell(new Paragraph(label, new Font(REPORT_FONT, 9)));
    cell.setHorizontalAlignment(ALIGN_LEFT);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getClarificationlabel(final WeeklyReportPdfModel model) {
    final var row = getQpdfTable(1);
    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 14, BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);
    final var currentWeekDaysFormatted =
        "(%s - %s)"
            .formatted(
                formatDate(model.getCurrentWeekStart()), formatDate(model.getCurrentWeekEnd()));

    final var cell =
        getQpdfPCell(
            new Paragraph(
                format("Ниже детальная информация по твоим обязательствам:", currentWeekDaysFormatted),
                new Font(REPORT_FONT, 14, BOLD)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  private PdfPCell getClarificationTableLabelCell(final String label) {
    final var labelCell =
        getQpdfPCell(new Paragraph(label + ":", new Font(REPORT_FONT, 10, NORMAL, BLACK)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(WHITE);

    return labelCell;
  }

  private PdfPCell getClarificationTableLabelCellDarkBlue(final String label) {
    final var labelCell =
            getQpdfPCell(new Paragraph(label + ":", new Font(REPORT_FONT, 10, NORMAL, CUSTOM_DARK_BLUE_COLOR)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(WHITE);

    return labelCell;
  }

  private PdfPCell getClarificationTableValueCell(final BigDecimal value, final String language) {
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var formattedValue = format("%s %s", formatAmount(value), euroCurrency);
    final var valueCell =
        getQpdfPCell(new Paragraph(formattedValue, new Font(REPORT_FONT, 10, NORMAL, BLACK)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(WHITE);

    return valueCell;
  }

  private PdfPCell getClarificationTableValueCellRed(final BigDecimal value, final String language) {
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var formattedValue = format("%s %s", formatAmount(value), euroCurrency);
    final var valueCell =
        getQpdfPCell(new Paragraph(formattedValue, new Font(REPORT_FONT, 10, BOLD, CUSTOM_RED_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(WHITE);

    return valueCell;
  }

  private PdfPCell getClarificationTableValueCellGreen(final BigDecimal value, final String language) {
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var formattedValue = format("%s %s", formatAmount(value), euroCurrency);
    final var valueCell =
            getQpdfPCell(new Paragraph(formattedValue, new Font(REPORT_FONT, 10, NORMAL, CUSTOM_GREEN_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(WHITE);

    return valueCell;
  }

  private PdfPCell getClarificationTableValueCellDarkBlue(final BigDecimal value, final String language) {
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var formattedValue = format("%s %s", formatAmount(value), euroCurrency);
    final var valueCell =
            getQpdfPCell(new Paragraph(formattedValue, new Font(REPORT_FONT, 10, NORMAL, CUSTOM_DARK_BLUE_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(WHITE);

    return valueCell;
  }

  private PdfPCell getClarificationTableValueCellDarkGray(final BigDecimal value, final String language) {
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var formattedValue = format("%s %s", formatAmount(value), euroCurrency);
    final var valueCell =
            getQpdfPCell(new Paragraph(formattedValue, new Font(REPORT_FONT, 10, NORMAL, CUSTOM_DARK_GRAY_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(WHITE);

    return valueCell;
  }


  private PdfPCell getClarificationTableLabelCellMultiColor(final String text, final String language) {
    final var paragraph = new Paragraph();
    
    // Добавляем "Заработок" темно-синим цветом
    final var earningsText = new Chunk("Заработок ", new Font(REPORT_FONT, 10, NORMAL, CUSTOM_DARK_BLUE_COLOR));
    paragraph.add(earningsText);
    
    // Добавляем "Bolt" зеленым цветом
    final var boltText = new Chunk("Bolt", new Font(REPORT_FONT, 10, NORMAL, CUSTOM_GREEN_COLOR));
    paragraph.add(boltText);
    
    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(WHITE);

    return labelCell;
  }

  private PdfPCell getClarificationTableLabelCellCampaign(final String campaignName, final String language) {
    final var paragraph = new Paragraph();
    
    // Добавляем "Кампания " черным цветом
    final var campaignText = new Chunk("Кампания ", new Font(REPORT_FONT, 10, NORMAL, BLACK));
    paragraph.add(campaignText);
    
    // Добавляем название кампании в кавычках темно-синим цветом
    final var campaignNameText = new Chunk(campaignName, new Font(REPORT_FONT, 10, BOLD, CUSTOM_DARK_BLUE_COLOR));
    paragraph.add(campaignNameText);
    
    final var labelCell = getQpdfPCell(paragraph);
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(18f);
    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(WHITE);

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

    final var totalRentAmount = formatAmount(model.getTotalRentAmount());
    final var table = getClarificationTable();
    table.addCell(
        getClarificationTableHeaderCell(
            format(
                "Арендная плата всего: %s %s ", totalRentAmount, currency)+ " Состоит из:"));
    table.addCell(getClarificationTableLabelCellDarkBlue("Аренда за текущую неделю"));
    table.addCell(getClarificationTableValueCellRed(rentAmount, language));
    table.addCell(getClarificationTableLabelCellCampaign("«Надежный партнер»", language));
    table.addCell(getClarificationTableValueCellGreen(bonusReliablePartnerAmount, language));
    table.addCell(getClarificationTableLabelCellCampaign("«Поездки Bolt»", language));
    table.addCell(getClarificationTableValueCellGreen(bonusBoltAmount, language));
    table.addCell(getClarificationTableLabelCellCampaign("«Приведи друга»", language));
    table.addCell(getClarificationTableValueCellGreen(bonusFriendAmount, language));
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
        format(
            "Коррекция аренды: %s %s",
            correctionOfRent, euroCurrency) + " Список коррекций ниже:";

    final var boltPlusAmount =
        model.getTransactionTypesVsAmount().get(TRANSACTION_TYPE_BOLT_PLUS_CODE);
    table.addCell(getClarificationTableHeaderCell(headerText));
    table.addCell(getClarificationTableLabelCellMultiColor("Заработок Bolt", language));
    table.addCell(getClarificationTableValueCellDarkGray(boltPlusAmount, language));
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getOtherPaymentClarificationTable(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var otherObligations = formatAmount(model.getTotalOtherPaymentAmount());
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var otherObligationsText =
        format("Прочие обязательства: %s %s", otherObligations, euroCurrency) + " Список этик обязательств:";
    final var table = getClarificationTable();
    table.addCell(getClarificationTableHeaderCell(otherObligationsText));
    table.addCell(getClarificationTableLabelCellDarkBlue("Залог"));
    final var depositAmount =
        model
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_DEPOSIT_CODE, BigDecimal.ZERO);
    table.addCell(getClarificationTableValueCell(depositAmount, language));
    table.addCell(
        getClarificationTableLabelCellDarkBlue(
            "ДВС за текущую неделю (дополнительное внутреннее страхование)"));
    final var innerInsuranceAmount =
        model
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE, BigDecimal.ZERO);
    table.addCell(getClarificationTableValueCellRed(innerInsuranceAmount, language));
    table.addCell(
        getClarificationTableLabelCellDarkBlue(
            "Доплата за отсутствие логотипов Q на автомобиле за текущую неделю"));
    final var nonLabelFineAmount =
        model
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_NO_LABEL_FINE_CODE, BigDecimal.ZERO);
    table.addCell(getClarificationTableValueCellRed(nonLabelFineAmount, language));
    table.addCell(getClarificationTableLabelCellDarkBlue("Штраф за парковку"));
    final var parkingFineAmount =
        model
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_PARKING_FINE_CODE, BigDecimal.ZERO);
    table.addCell(getClarificationTableValueCellRed(parkingFineAmount, language));
    table.addCell(getClarificationTableLabelCellDarkBlue("Пени на конец прошлой недели"));
    final var feeAmount =
        model
            .getTransactionTypesVsAmount()
            .getOrDefault(TRANSACTION_TYPE_FEE_DEBT_CODE, BigDecimal.ZERO);
    table.addCell(getClarificationTableValueCellRed(feeAmount, language));
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
        format("Требование в счет обязательств на начало текущей недели: %s %s", damagePayment, euroCurrency) + " Список этих обязательств:";
    table.addCell(getClarificationTableHeaderCell(headerText));
    table.addCell(getClarificationTableLabelCellDarkBlue("Пени"));
    table.addCell(
        getClarificationTableValueCellRed(model.getFeeAmountAtCalculationMoment(), language));

    final var netAmountOnTuesday = model.getNetAmountOnThursday();
    final var netType =
        netAmountOnTuesday.compareTo(BigDecimal.ZERO) < 0 ? "задолженность" : "предоплата";

    table.addCell(
        getClarificationTableLabelCell(
            "Общий долг (без учета ремонтов)")); // из баланса на понедельник ( при условии что есть долг)
    table.addCell(getClarificationTableValueCell(model.getNetAmountOnThursday(), language));

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
                  getClarificationTableValueCellDarkGray(insuranceCase.getDamageRemaining(), language));
            });
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getTotalBlock(final WeeklyReportPdfModel model) {
    final var table = getQpdfTable(2);
    final var language = model.getLanguage();
    final var labelCell =
        getQpdfPCell(new Paragraph("Итого к оплате:", new Font(REPORT_FONT, 14, BOLD, WHITE)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(30f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(ORANGE_BACKGROUND_COLOR);
    table.addCell(labelCell);

    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var totalPaymentAmount = formatAmount(model.getTotalPaymentAmount());
    final var totalPaymentAmountFormatted = format("%s %s", totalPaymentAmount, euroCurrency);

    final var valueCell =
        getQpdfPCell(
            new Paragraph(totalPaymentAmountFormatted, new Font(REPORT_FONT, 14, BOLD, WHITE)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(30f);

    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(ORANGE_BACKGROUND_COLOR);

    table.addCell(valueCell);

    return table;
  }

  private PdfPCell getClarificationTableHeaderCell(final String headerText) {
    final var tableHeaderCell =
        getQpdfPCell(new Paragraph(headerText, new Font(REPORT_FONT, 12, NORMAL, WHITE)));
    tableHeaderCell.setHorizontalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(35f);
    tableHeaderCell.setPaddingTop(9f);
    tableHeaderCell.setColspan(2);
    tableHeaderCell.setBackgroundColor(BLUE_BACKGROUND_COLOR);

    return tableHeaderCell;
  }

  private static String formatDate(final LocalDate date) {
    if (date == null) {
      return "";
    }
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
    return date.format(formatter);
  }

  private static String formatAmount(final BigDecimal amount) {
    if (amount == null) {
      return "0.00";
    }
    return format(Locale.US, "%,.2f", amount);
  }
}
