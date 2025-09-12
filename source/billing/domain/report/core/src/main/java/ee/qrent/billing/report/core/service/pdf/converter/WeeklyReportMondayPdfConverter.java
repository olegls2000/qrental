package ee.qrent.billing.report.core.service.pdf.converter;

import static com.lowagie.text.Element.*;
import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportMondayPdfLabelProvider.*;
import static java.awt.Color.*;

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

import com.lowagie.text.pdf.draw.LineSeparator;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportPdfModel;
import ee.qrent.billing.report.domain.WeeklyReportType;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportMondayPdfConverter implements WeeklyReportPdfConversionStrategy {

  public static final int REPORT_FONT = HELVETICA;
  private static final Color GRAY_BACKGROUND_COLOR = new Color(241, 241, 241);
  private static final float FINANCIAL_DATA_CELL_HEIGHT = 22f;

  @Override
  public boolean canApply(final WeeklyReportType reportType) {

    return reportType == WeeklyReportType.MONDAY_REPORT;
  }

  @Override
  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var weeklyReportPdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
    final var weeklyReportPdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(weeklyReportPdfDoc, weeklyReportPdfOutputStream);
    weeklyReportPdfDoc.open();
    weeklyReportPdfDoc.add(getHeader(model.getLanguage()));
    weeklyReportPdfDoc.add(getEmptyRow());
    weeklyReportPdfDoc.add(getDriverMainData(model));
    weeklyReportPdfDoc.add(getFinancialCommentRow());
    weeklyReportPdfDoc.add(getDepositData());
    weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getBalanceData());
    weeklyReportPdfDoc.add(getLineSeparator());
    weeklyReportPdfDoc.add(getDebtAndObligationData());
    weeklyReportPdfDoc.add(getTransactionTableTitleRow());
    weeklyReportPdfDoc.add(getTransactionTable(model));
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }

  private PdfPCell getQpdfPCell(final Paragraph paragraph) {
    final var qCell = new PdfPCell(paragraph);
    qCell.setBorder(NO_BORDER);

    return qCell;
  }

  private PdfPTable getEmptyRow() {
    final var row = new PdfPTable(1);
    row.setWidthPercentage(100f);
    final var cell = getQpdfPCell(new Paragraph(" ", new Font(REPORT_FONT, 14, Font.BOLD)));
    cell.setFixedHeight((float) 18.0);
    row.addCell(cell);

    return row;
  }

  private Chunk getLineSeparator() {
    final var ls = new LineSeparator();
    ls.setLineColor(GRAY_BACKGROUND_COLOR);
    ls.setLineWidth(1f);

    return new Chunk(ls);
  }

  @SneakyThrows
  private PdfPTable getHeader(final String language) {
    final var header = new PdfPTable(4);
    header.setWidthPercentage(100f);

    Image img = Image.getInstance("Images/qRentalGroup_gorznt.png");
    img.scaleToFit(33, 14);

    PdfPCell imageCell = new PdfPCell(img, true);
    imageCell.setHorizontalAlignment(ALIGN_CENTER);
    imageCell.setVerticalAlignment(ALIGN_MIDDLE);
    imageCell.setFixedHeight(30f);
    imageCell.setBorder(NO_BORDER);
    header.addCell(imageCell);

    final var reportNameCell =
        getQpdfPCell(
            new Paragraph(
                getLabel(language, REPORT_NAME_KEY), new Font(REPORT_FONT, 14, Font.BOLD)));

    reportNameCell.setColspan(3);
    reportNameCell.setHorizontalAlignment(ALIGN_LEFT);
    reportNameCell.setPaddingLeft(65f);
    reportNameCell.setVerticalAlignment(ALIGN_MIDDLE);
    header.addCell(reportNameCell);

    return header;
  }

  private PdfPCell getDriverMainDataLabelCell(final String label) {
    final var labelCell = getQpdfPCell(new Paragraph(label + ":", new Font(REPORT_FONT, 14)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_MIDDLE);
    labelCell.setBackgroundColor(GRAY_BACKGROUND_COLOR);
    labelCell.setFixedHeight(25f);
    labelCell.setPaddingRight(2f);

    return labelCell;
  }

  private PdfPCell getDriverMainDataValueCell(final String value) {
    final var labelCell = getQpdfPCell(new Paragraph(value, new Font(REPORT_FONT, 14, Font.BOLD)));
    labelCell.setHorizontalAlignment(ALIGN_LEFT);
    labelCell.setVerticalAlignment(ALIGN_MIDDLE);
    labelCell.setBackgroundColor(GRAY_BACKGROUND_COLOR);
    labelCell.setFixedHeight(25f);
    labelCell.setPaddingLeft(8f);

    return labelCell;
  }

  private PdfPTable getDriverMainData(final WeeklyReportPdfModel model) {

    final var language = model.getLanguage();
    final var table = new PdfPTable(2);
    table.setWidthPercentage(100f);
    table.addCell(getDriverMainDataLabelCell(getLabel(language, DRIVER_LABEL_KEY)));

    final var driverName = "%s %s".formatted(model.getFirstName(), model.getLastName());
    table.addCell(getDriverMainDataValueCell(driverName));

    final var taxNumber = model.getTaxNumber().toString();
    table.addCell(getDriverMainDataLabelCell("Tax number"));
    table.addCell(getDriverMainDataValueCell(taxNumber));

    final var reportedWeek =
        "%d (%s - %s)"
            .formatted(
                99,
                formatDate(model.getPreviousWeekStart()),
                formatDate(model.getPreviousWeekEnd()));
    table.addCell(getDriverMainDataLabelCell("Reported Week"));
    table.addCell(getDriverMainDataValueCell(reportedWeek));

    table.addCell(getDriverMainDataLabelCell("Call sign"));
    table.addCell(getDriverMainDataValueCell(model.getCallSign().toString()));

    table.addCell(getDriverMainDataLabelCell("Rented Cars"));
    table.addCell(getDriverMainDataValueCell(model.getCarRegistrationNumber()));
    table.addCell(getDriverMainDataLabelCell("Created on"));
    table.addCell(getDriverMainDataValueCell(formatDate(model.getCurrentWeekStart())));

    return table;
  }

  private PdfPTable getFinancialCommentRow() {
    final var row = new PdfPTable(1);
    row.setWidthPercentage(100f);
    final var cell =
        getQpdfPCell(
            new Paragraph(
                "According to our data your financial state is:", new Font(REPORT_FONT, 12)));
    cell.setHorizontalAlignment(ALIGN_LEFT);
    cell.setVerticalAlignment(ALIGN_BOTTOM);
    cell.setFixedHeight(35f);
    row.addCell(cell);

    return row;
  }

  private PdfPTable getTransactionTableTitleRow() {
    final var row = new PdfPTable(1);
    row.setWidthPercentage(100f);

    final var paddingTopCell = getQpdfPCell(new Paragraph("", new Font(REPORT_FONT, 14, BOLD)));

    paddingTopCell.setFixedHeight(15f);
    row.addCell(paddingTopCell);

    final var cell =
        getQpdfPCell(new Paragraph("Reported Week Transactions", new Font(REPORT_FONT, 14, BOLD)));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setFixedHeight(40f);
    row.addCell(cell);

    return row;
  }

  private PdfPCell getFinancialDataLabelCell(final String label) {
    final var labelCell = getQpdfPCell(new Paragraph(label + ", EUR:", new Font(REPORT_FONT, 14)));
    labelCell.setHorizontalAlignment(ALIGN_RIGHT);
    labelCell.setVerticalAlignment(ALIGN_BOTTOM);
    labelCell.setFixedHeight(FINANCIAL_DATA_CELL_HEIGHT);
    labelCell.setPaddingLeft(8f);

    return labelCell;
  }

  private PdfPCell getFinancialDataValueCell(final BigDecimal value) {
    final var valueCell =
        getQpdfPCell(new Paragraph(formatAmount(value), new Font(REPORT_FONT, 14)));
    valueCell.setHorizontalAlignment(ALIGN_LEFT);
    valueCell.setVerticalAlignment(ALIGN_BOTTOM);
    valueCell.setFixedHeight(FINANCIAL_DATA_CELL_HEIGHT);
    valueCell.setPaddingRight(8f);

    return valueCell;
  }

  private PdfPTable getDepositData() {
    final var table = new PdfPTable(2);
    table.setWidthPercentage(100f);
    table.addCell(getFinancialDataLabelCell("Deposit"));
    table.addCell(getFinancialDataValueCell(BigDecimal.valueOf(500)));
    table.addCell(getFinancialDataExplanationRow(null));
    table.addCell(getFinancialDataExplanationRow(null));
    table.addCell(getFinancialDataLabelCell("Paid Deposit"));
    table.addCell(getFinancialDataValueCell(BigDecimal.ZERO));
    table.addCell(getFinancialDataExplanationRow(null));
    table.addCell(getFinancialDataExplanationRow(null));

    return table;
  }

  private PdfPTable getBalanceData() {
    final var table = new PdfPTable(2);
    table.setWidthPercentage(100f);
    table.addCell(getFinancialDataLabelCell("Balance"));
    table.addCell(getFinancialDataValueCell(BigDecimal.ZERO));
    table.addCell(getFinancialDataExplanationRow("on the end of Reported Week"));
    table.addCell(getFinancialDataExplanationRow(null));
    table.addCell(getFinancialDataLabelCell("Balance"));
    table.addCell(getFinancialDataValueCell(BigDecimal.ZERO));
    table.addCell(getFinancialDataExplanationRow("on Monday after Reported Week"));
    table.addCell(getFinancialDataExplanationRow(null));

    return table;
  }

  private PdfPCell getFinancialDataExplanationRow(final String explanation) {
    final var explanationText = explanation == null ? "" : "(" + explanation + ")";
    final var cell = getQpdfPCell(new Paragraph(explanationText, new Font(REPORT_FONT, 8, NORMAL)));
    cell.setHorizontalAlignment(ALIGN_RIGHT);
    cell.setVerticalAlignment(ALIGN_TOP);
    cell.setFixedHeight(13f);

    return cell;
  }

  private PdfPTable getDebtAndObligationData() {
    final var table = new PdfPTable(2);
    table.setWidthPercentage(100f);
    table.addCell(getFinancialDataLabelCell("Debt"));
    table.addCell(getFinancialDataValueCell(BigDecimal.ZERO));
    table.addCell(getFinancialDataExplanationRow(null));
    table.addCell(getFinancialDataExplanationRow(null));
    table.addCell(getFinancialDataLabelCell("Obligation"));
    table.addCell(getFinancialDataValueCell(BigDecimal.ZERO));
    table.addCell(getFinancialDataExplanationRow(null));
    table.addCell(getFinancialDataExplanationRow(null));

    return table;
  }

  private PdfPTable getTransactionTable(final WeeklyReportPdfModel model) {
    final var table = new PdfPTable(2);
    table.setWidthPercentage(100f);
    table.setWidths(new float[] {8f, 2f});
    table.addCell(getTransactionTableHeaderCell("Type"));
    table.addCell(getTransactionTableHeaderCell("Amount, EUR"));

    int i = 0;
    for (final var entry : model.getTransactionTypesVsAmount().entrySet()) {
      final var rowBackground = i++ % 2 == 0 ? WHITE : GRAY_BACKGROUND_COLOR;
      final var typeCell =
          getQpdfPCell(new Paragraph(entry.getKey(), new Font(REPORT_FONT, 12, NORMAL)));
      typeCell.setHorizontalAlignment(ALIGN_LEFT);
      typeCell.setVerticalAlignment(ALIGN_CENTER);
      typeCell.setFixedHeight(25f);
      typeCell.setPaddingLeft(8f);
      typeCell.setBackgroundColor(rowBackground);
      table.addCell(typeCell);

      final var value = entry.getValue();
      final var amountColor =
          value.compareTo(BigDecimal.ZERO) < 0 ? new Color(188, 7, 7) : new Color(3, 154, 36, 255);
      final var amountCell =
          getQpdfPCell(
              new Paragraph(formatAmount(value), new Font(REPORT_FONT, 12, NORMAL, amountColor)));
      amountCell.setHorizontalAlignment(ALIGN_CENTER);
      amountCell.setVerticalAlignment(ALIGN_CENTER);
      amountCell.setFixedHeight(25f);
      amountCell.setBackgroundColor(rowBackground);
      table.addCell(amountCell);
    }

    return table;
  }

  private PdfPCell getTransactionTableHeaderCell(final String columnName) {
    final var tableHeaderCell =
        getQpdfPCell(new Paragraph(columnName, new Font(REPORT_FONT, 14, NORMAL, WHITE)));
    tableHeaderCell.setHorizontalAlignment(ALIGN_CENTER);
    tableHeaderCell.setVerticalAlignment(ALIGN_CENTER);
    tableHeaderCell.setFixedHeight(30f);
    tableHeaderCell.setPaddingLeft(8f);
    tableHeaderCell.setBackgroundColor(new Color(240, 137, 39, 255));

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
    return String.format(Locale.US, "%,.2f", amount);
  }
}
