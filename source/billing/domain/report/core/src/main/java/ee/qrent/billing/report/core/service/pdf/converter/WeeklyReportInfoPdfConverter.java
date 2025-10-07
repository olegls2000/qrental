package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.Element.*;
import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportInfoPdfLabelProvider.INFO_LABEL_KEY;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProviderVer1.*;
import static java.awt.Color.*;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import com.lowagie.text.*;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.core.service.pdf.label.WeeklyReportInfoPdfLabelProvider;
import ee.qrent.billing.report.domain.WeeklyReportType;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportInfoPdfConverter implements WeeklyReportPdfConversionStrategy {

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

    return reportType == WeeklyReportType.INFO_REPORT;
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
    final var row = getQpdfTable(1);
    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 13, BOLD)));
    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);
    final var text = WeeklyReportInfoPdfLabelProvider.getLabel(language, INFO_LABEL_KEY);
    final var cell = getQpdfPCell(new Paragraph(text + ":", new Font(REPORT_FONT, 13, BOLD)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setFixedHeight(40f);
    row.addCell(cell);
    weeklyReportPdfDoc.add(row);
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
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
    labelCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

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
            new Paragraph(formattedValue, new Font(REPORT_FONT, 10, BOLD, REPORT_DARK_GRAY_COLOR)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_CENTER);
    valueCell.setFixedHeight(18f);
    valueCell.setPaddingLeft(8f);
    valueCell.setBackgroundColor(REPORT_GRAY_BACKGROUND_COLOR);

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

  private PdfPCell getEmptyRow() {
    final var emptyRow = getQpdfPCell(new Paragraph(" ", new Font(REPORT_FONT, 14, Font.BOLD)));
    emptyRow.setColspan(2);
    emptyRow.setFixedHeight((float) 8.0);

    return emptyRow;
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
