package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.Element.*;
import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.*;
import static java.awt.Color.*;
import static java.lang.String.format;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.lowagie.text.*;
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
public class WeeklyReportMondayPdfConverter implements WeeklyReportPdfConversionStrategy {

  public static final int REPORT_FONT = HELVETICA;
  private static final Color GRAY_BACKGROUND_COLOR = new Color(241, 241, 241);
  private static final Color ORANGE_BACKGROUND_COLOR = new Color(240, 137, 40);
  private static final Color BLUE_BACKGROUND_COLOR = new Color(65, 111, 177);

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
    weeklyReportPdfDoc.add(getHeader(language));
    weeklyReportPdfDoc.add(getEmptyRow());
    weeklyReportPdfDoc.add(getDriverMainData(model));
    weeklyReportPdfDoc.add(getFinancialCommentRow(model));
    weeklyReportPdfDoc.add(getFinancialCommentRowX(model));
    weeklyReportPdfDoc.add(getClarificationlabel(model));
    weeklyReportPdfDoc.add(getClarificationBlock1(model));
    weeklyReportPdfDoc.add(getClarificationBlock2(model));
    weeklyReportPdfDoc.add(getClarificationBlock3(model));
    weeklyReportPdfDoc.add(getClarificationBlock4(model));
    weeklyReportPdfDoc.add(getTotalBlock(model));
    weeklyReportPdfDoc.add(getCommentRow());
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }

  private PdfPTable getCommentRow() {

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
  private PdfPTable getHeader(final String language) {
    final var header = getQpdfTable(4);

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

    reportNameCell.setColspan(3);
    reportNameCell.setHorizontalAlignment(ALIGN_LEFT);
    reportNameCell.setPaddingLeft(65f);
    reportNameCell.setVerticalAlignment(ALIGN_MIDDLE);
    header.addCell(reportNameCell);

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

  private PdfPTable getDriverMainData(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(2);
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

    table.addCell(getDriverMainDataLabelCell("Weeks left till the end of Contract"));
    table.addCell(getDriverMainDataValueCell(model.getWeeksCountTillEnd().toString()));

    table.addCell(getDriverMainDataLabelCell("Deposit"));
    table.addCell(getDriverMainDataValueCell("99999"));

    table.addCell(getDriverMainDataLabelCell("Charged Deposit"));
    table.addCell(getDriverMainDataValueCell("99999"));

    return table;
  }

  private PdfPTable getFinancialCommentRow(WeeklyReportPdfModel model) {
    final var row = getQpdfTable(1);

    final var weekDaysFormatted =
        "(%s - %s)"
            .formatted(
                formatDate(model.getPreviousWeekStart()), formatDate(model.getPreviousWeekEnd()));
    final var label =
        "Согласно последним данным, внесенным в нашу программу на конец четверга прошлой недели, твои обязательства перед Q Takso Veod OÜ за прошлую неделю "
            + weekDaysFormatted;

    final var good =
        " «были выполнены своевременно и в полном объеме – согласно условиям твоего договора. Твоё сальдо за прошлую неделю на конец четверга  "
            + weekDaysFormatted
            + " составила: 999 евро в виде предоплаты. Эта предоплата учтена при рассчете твоих последующих обязательств";
    final var bonusActivated =
        "В знак нашей благодарности мы активировали все наши еженедельные бонусные кампании в твоем аккаунте";

    final var notGood =
        "«не были выполнены своевременно и в полном объеме. Твое сальдо на конец прошлой недели "
            + weekDaysFormatted
            + " составило: 999 евро в виде долга. ";
    final var bonusNotActivated =
        "К сожалению, по причине этого наши еженедельные бонусных кампании не будут для тебя доступны, а сам долг будет учтен при рассчете твоих последующих обязательств.";
    String labelF;

    if (model.getObligationStatus().equals("COMPLETED")) {
      labelF = label + good + bonusActivated;
    } else {
      labelF = label + notGood + bonusNotActivated;
    }

    final var cell = getQpdfPCell(new Paragraph(labelF, new Font(REPORT_FONT, 9)));
    cell.setHorizontalAlignment(ALIGN_LEFT);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    cell.setFixedHeight(55f);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getFinancialCommentRowX(WeeklyReportPdfModel model) {
    final var row = getQpdfTable(1);
    final var nextWeekDaysFormatted =
        "(%s - %s)"
            .formatted(formatDate(model.getNextWeekStart()), formatDate(model.getNextWeekEnd()));
    final var label =
        "В соответствии с этим твои обязательства перед Q Takso Veod OÜ за текущую неделю на текущий момент составляют: {???} евро. Пожалуйста, оплати эту сумму до 16:00 следующего дня {???}, и твои бонусные кампании на следующую неделю "
            + nextWeekDaysFormatted
            + " будут активированы";

    final var cell = getQpdfPCell(new Paragraph(label, new Font(REPORT_FONT, 9)));
    cell.setHorizontalAlignment(ALIGN_LEFT);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    // cell.setFixedHeight(45f);
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
                "Ниже, краткий обзор твоих обязательств за текущую "
                    + currentWeekDaysFormatted
                    + " неделю:\n",
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

  private PdfPCell getClarificationTableValueCell(final BigDecimal value, final String language) {
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var valueCell =
        getQpdfPCell(
            new Paragraph(
                formatAmount(value) + euroCurrency, new Font(REPORT_FONT, 10, NORMAL, BLACK)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);

    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(WHITE);

    return valueCell;
  }

  private PdfPTable getClarificationBlock1(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(2);
    table.addCell(
        getClarificationTableHeaderCell(
            "Твоя итоговая арендная плата за текущую неделю за вычетом бонусов по нашим кампаниям составила всего: 150,20 евро"));

    table.addCell(getClarificationTableLabelCell("Аренда за текущую неделю"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getClarificationTableLabelCell("Кампания «Надежный партнер»"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getClarificationTableLabelCell("Кампания «Поездки Bolt»"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(
        getClarificationTableLabelCell("Кампания «Приведи друга» ({driver's friend FN + LN}})"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPCell getEmptyRow() {
    final var emptyRow = getQpdfPCell(new Paragraph(" ", new Font(REPORT_FONT, 14, Font.BOLD)));
    emptyRow.setColspan(2);
    emptyRow.setBackgroundColor(WHITE);
    emptyRow.setFixedHeight((float) 8.0);
    return emptyRow;
  }

  private PdfPTable getClarificationBlock2(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(2);
    final var correctionOfRent = formatAmount(BigDecimal.valueOf(999L));
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var headerText =
        format(
            "Сумма коррекции аренды с твоего заработка в приложениях на текущей неделе составила: %s %s",
            correctionOfRent, euroCurrency);
    table.addCell(getClarificationTableHeaderCell(headerText));
    table.addCell(getClarificationTableLabelCell("Заработок Bolt"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getClarificationBlock3(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var otherObligations = formatAmount(BigDecimal.valueOf(999L));
    final var euroCurrency = getLabel(language, CURRENCY_NAME_KEY);
    final var otherObligationsText =
        format("Прочие обязательства: %s %s", otherObligations, euroCurrency);
    final var table = getQpdfTable(2);
    table.addCell(getClarificationTableHeaderCell(otherObligationsText));
    table.addCell(getClarificationTableLabelCell("Залог"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(
        getClarificationTableLabelCell(
            "ДВС за текущую неделю (дополнительное внутреннее страхование)"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(
        getClarificationTableLabelCell(
            "Доплата за отсутствие логотипов Q на автомобиле за текущую неделю"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getClarificationTableLabelCell("Штраф за парковку"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getClarificationTableLabelCell("Пени на конец прошлой недели"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getClarificationBlock4(final WeeklyReportPdfModel model) {
    final var language = model.getLanguage();
    final var table = getQpdfTable(2);
    table.addCell(
        getClarificationTableHeaderCell("Востребуемая часть общей задолженности: 999.00 евро"));
    table.addCell(getClarificationTableLabelCell("Пени"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(
        getClarificationTableLabelCell(
            "Текущая задолженность / предоплата по обязательствам за прошлый период"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getClarificationTableLabelCell("Ремонт / Сумма франшизы {CarNumberA}"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getClarificationTableLabelCell("Ремонт / Сумма франшизы {CarNumberB}"));
    table.addCell(getClarificationTableValueCell(BigDecimal.valueOf(999L), language));
    table.addCell(getEmptyRow());

    return table;
  }

  private PdfPTable getTotalBlock(final WeeklyReportPdfModel model) {
    final var table = getQpdfTable(2);
    final var labelCell =
        getQpdfPCell(new Paragraph("Итого к оплате:", new Font(REPORT_FONT, 14, NORMAL, WHITE)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_CENTER);
    labelCell.setFixedHeight(30f);

    labelCell.setPaddingRight(8f);
    labelCell.setBackgroundColor(ORANGE_BACKGROUND_COLOR);
    table.addCell(labelCell);

    final var valueCell =
        getQpdfPCell(
            new Paragraph(
                formatAmount(BigDecimal.valueOf(999)) + " EUR",
                new Font(REPORT_FONT, 14, NORMAL, WHITE)));
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
    tableHeaderCell.setVerticalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(35f);
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
