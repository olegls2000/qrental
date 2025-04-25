package ee.qrent.billing.invoice.core.service.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import static com.lowagie.text.Font.BOLDITALIC;
import static com.lowagie.text.Font.COURIER;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.RIGHT;
import static ee.qrent.common.utils.QNumberUtils.qRound;
import static java.awt.Color.white;
import static java.lang.String.format;

@AllArgsConstructor
public class InvoiceToPdfConverter {

  private static String getTextOrEmpty(final String text) {
    if (text == null || text.isBlank()) {
      return "---";
    }
    return text;
  }

  @SneakyThrows
  public InputStream getPdfInputStream(final InvoicePdfModel model) {
    final var invoicePdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
    final var invoicePdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(invoicePdfDoc, invoicePdfOutputStream);
    final var header = getHeader(model.getNumber(), model.getStartDate(), model.getEndDate());
    final var requisites =
        getRequisites(
            model.getDriverCompany(),
            model.getDriverCompanyAddress(),
            model.getDriverCompanyRegNumber(),
            model.getDriverCompanyVat(),
            model.getDriverInfo());
    final var itemsTable = getItemsTable(model.getItems());
    final var block1 =
        getBlock1(
            model.getSum(), model.getVatPercentage(), model.getVatAmount(), model.getSumWithVat());
    final var block2 =
        getBlock2(
            model.getBlock2A(), model.getBlock2B(), model.getAdvancePayment(), model.getDebt());
    final var block3 = getBlock3(model.getSum(), model.getTotal());
    final var block4 =
        getBlock4(
            model.getFeeStartDate(),
            model.getFeeEndDate(),
            model.getCurrentWeekFee(),
            model.getPreviousWeekBalanceFee(),
            model.getTotalWithFee());
    final var block5 = getBlock5(model.getTotalWithFee());
    final var footer =
        getFooter(
            model.getQFirmName(),
            model.getQFirmPostAddress(),
            model.getQFirmBank(),
            model.getQFirmRegNumber(),
            model.getQFirmEmail(),
            model.getQFirmIban(),
            model.getQFirmVatNumber(),
            model.getQFirmPhone());
    invoicePdfDoc.open();
    invoicePdfDoc.add(header);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(requisites);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(itemsTable);
    invoicePdfDoc.add(block1);
    invoicePdfDoc.add(block2);
    invoicePdfDoc.add(block3);
    invoicePdfDoc.add(block4);
    invoicePdfDoc.add(block5);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(footer);
    invoicePdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(invoicePdfOutputStream.toByteArray());
  }

  @SneakyThrows
  private Table getHeader(
      final String invoiceNumber, final String startDate, final String endDate) {
    final var header = new Table(2);
    header.setPadding(0f);
    header.setSpacing(0f);
    header.setWidth(100f);
    header.setBorderColor(white);
    header.setHorizontalAlignment(RIGHT);
    header.setBorder(NO_BORDER);
    header.setBorder(NO_BORDER);

    final var logo = Image.getInstance("Images/qRentalGroup_gorznt.png");
    logo.scaleAbsolute(150f, 60f);
    final var cell = new Cell(logo);
    cell.setRowspan(4);
    cell.setBorder(NO_BORDER);
    header.addCell(cell);

    final var cell1 =
        new Cell(
            new Paragraph("Arve Nr.: " + invoiceNumber, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell1.setBorder(NO_BORDER);
    header.addCell(cell1);

    final var cell2 =
        new Cell(
            new Paragraph("Arve kuupäev: " + startDate, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell2.setBorder(NO_BORDER);
    header.addCell(cell2);

    final var cell3 =
        new Cell(
            new Paragraph("Maksetähtpäev: " + endDate, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell3.setBorder(NO_BORDER);
    header.addCell(cell3);

    final var cell4 =
        new Cell(
            new Paragraph(
                format("Ajaperiood: %s - %s", startDate, endDate),
                new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell4.setBorder(NO_BORDER);
    header.addCell(cell4);

    return header;
  }

  private Table getRequisites(
      final String invoiceDriverCompany,
      final String invoiceDriverCompanyAddress,
      final String invoiceDriverCompanyRegNumber,
      final String driverCompanyVat,
      final String driverInfo) {
    final var requisites = new Table(2);
    requisites.setWidths(new float[] {1, 4});
    requisites.setPadding(0f);
    requisites.setSpacing(-1f);
    requisites.setWidth(100f);
    requisites.setHorizontalAlignment(HorizontalAlignment.LEFT);
    requisites.setBorder(NO_BORDER);
    requisites.addCell(getRequisitLabelCell("Maksja"));
    requisites.addCell(getRequisitValueCell(getTextOrEmpty(invoiceDriverCompany)));
    requisites.addCell(getRequisitLabelCell("Aadress"));
    requisites.addCell(getRequisitValueCell(getTextOrEmpty(invoiceDriverCompanyAddress)));
    requisites.addCell(getRequisitLabelCell("Äriregistri nr."));
    requisites.addCell(getRequisitValueCell(getTextOrEmpty(invoiceDriverCompanyRegNumber)));
    requisites.addCell(getRequisitLabelCell("KMKR nr."));
    requisites.addCell(getRequisitValueCell(getTextOrEmpty(driverCompanyVat)));
    requisites.addCell(getRequisitLabelCell("Arve saaja"));
    requisites.addCell(getRequisitValueCell(getTextOrEmpty(driverInfo)));

    return requisites;
  }

  private Table getItemsTable(final Map<String, BigDecimal> items) {
    final var table = new Table(2);
    table.setWidths(new float[] {3, 1});
    table.setPadding(2f);
    table.setSpacing(1f);
    table.setWidth(100f);
    table.setHorizontalAlignment(HorizontalAlignment.LEFT);
    table.setBorderColor(Color.DARK_GRAY);
    table.setBorderWidth(1f);
    table.addCell(getItemTablHeaderCell("Selgitus"));
    table.addCell(getItemTablHeaderCell("Summa"));
    items.entrySet().forEach(item -> addRow(item, table));

    return table;
  }

  private Table getBlock1(
      final BigDecimal invoiceTotalAmount,
      final BigDecimal vatPercentage,
      final BigDecimal vatAmount,
      final BigDecimal arveSum) {
    final var table = new Table(2);
    table.setWidths(new float[] {8, 2});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(70f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);
    table.addCell(getSummLabelCell("Jooksvate kohustuste summa"));
    table.addCell(getSummValueCell(invoiceTotalAmount));
    final var vatLabel = "Käibemaks (" + vatPercentage + "%)";
    table.addCell(getSummLabelCell(vatLabel));
    table.addCell(getSummValueCell(vatAmount));
    table.addCell(getSummLabelCell("Jooksvate kohustuste summa + km"));
    table.addCell(getSummValueCell(arveSum));

    return table;
  }

  private Cell getSummLabelCell(final String label) {
    final var labelFinal = format("%s: ", label);
    final var cell = new Cell(new Paragraph(labelFinal, new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(RIGHT);

    return cell;
  }

  private Cell getSummValueCell(final BigDecimal value) {
    final var cell =
        new Cell(
            new Paragraph(getFormattedString(value), new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(RIGHT);

    return cell;
  }

  private Table getBlock4(
      final String feeStartPeriod,
      final String feeEndPeriod,
      final BigDecimal currentWeekFee,
      final BigDecimal previousWeekBalanceFee,
      final BigDecimal totalWithFee) {
    final var totalFeeAmount = previousWeekBalanceFee.add(currentWeekFee);
    final var table = new Table(2);
    table.setWidths(new float[] {8, 2});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(70f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);
    table.addCell(
        getTotalLabelCell(
            String.format("Viivised %s - %s perioodi eest", feeStartPeriod, feeEndPeriod)));
    table.addCell(getTotalValueCell(currentWeekFee));
    table.addCell(
        getTotalLabelCell(String.format("Kuni %s kogunenud viiviste üldsumma", feeStartPeriod)));
    table.addCell(getTotalValueCell(previousWeekBalanceFee));
    table.addCell(getTotalLabelCell("Viiviste üldsumma"));
    table.addCell(getTotalValueCell(totalFeeAmount));

    return table;
  }

  private Table getBlock5(final BigDecimal totalWithFee) {
    final var table = new Table(2);
    table.setWidths(new float[] {8, 2});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(70f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);

    table.addCell(getTotalLabelCell("Tasuda kokku (kohustused + viivised)"));
    final var finalTotalWithFee =
        totalWithFee.compareTo(BigDecimal.ZERO) > 0 ? totalWithFee : BigDecimal.ZERO;
    table.addCell(getTotalValueCell(finalTotalWithFee));

    return table;
  }

  private Table getBlock2(
      final BigDecimal a,
      final BigDecimal b,
      final BigDecimal advancePayment,
      final BigDecimal debt) {
    final var table = new Table(2);
    table.setWidths(new float[] {8, 2});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(70f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);

    table.addCell(getTotalLabelCell("Eelmise perioodi kohustused"));
    table.addCell(getTotalValueCell(a));
    table.addCell(getTotalLabelCell("Eelmise perioodi sissetulekud"));
    table.addCell(getTotalValueCell(b));

    table.addCell(getTotalLabelCell("Eelmise perioodi ettemaks"));
    table.addCell(getTotalValueCell(advancePayment));
    table.addCell(getTotalLabelCell("Eelmise perioodi võlgnevus"));
    table.addCell(getTotalValueCell(debt));

    return table;
  }

  private Table getBlock3(final BigDecimal arveSum, final BigDecimal total) {
    final var table = new Table(2);
    table.setWidths(new float[] {8, 2});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(70f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);

    table.addCell(getTotalLabelCell("Jooksvate kohustuste summa"));
    table.addCell(getTotalValueCell(arveSum));
    table.addCell(getTotalLabelCell("Arve summa + km(jooksvad kohustused kaasa arvatud võlgnevust/ettemaksu)"));
    table.addCell(getTotalValueCell(total));

    return table;
  }

  private Cell getTotalLabelCell(final String label) {
    final var labelFinal = format("%s: ", label);
    final var cell = new Cell(new Paragraph(labelFinal, new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);

    return cell;
  }

  private Cell getTotalValueCell(final BigDecimal value) {
    final var cell =
        new Cell(
            new Paragraph(getFormattedString(value), new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);

    return cell;
  }

  private Table getFooter(
      final String qFirmName,
      final String qFirmAddress,
      final String qFirmBank,
      final String qFirmRegNumber,
      final String qFirmEmail,
      final String qFirmIban,
      final String qFirmVatNumber,
      final String qFirmPhone) {
    final var table = new Table(3);
    table.setSpacing(-2f);
    table.setPadding(0f);
    table.setWidth(100f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);

    table.addCell(getFooterCell(qFirmName));
    table.addCell(getFooterCell(format("Aadress: %s", getTextOrEmpty(qFirmAddress))));
    table.addCell(getFooterCell(format("Bank: %s", getTextOrEmpty(qFirmBank))));
    table.addCell(getFooterCell(format("Äriregistri nr.: %s", getTextOrEmpty(qFirmRegNumber))));
    table.addCell(getFooterCell(format("E-post: %s", getTextOrEmpty(qFirmEmail))));
    table.addCell(getFooterCell(format("IBAN: %s", getTextOrEmpty(qFirmIban))));
    table.addCell(getFooterCell(format("KMKR: %s", getTextOrEmpty(qFirmVatNumber))));
    table.addCell(getFooterCell(format("Telefon: %s", getTextOrEmpty(qFirmPhone))));

    return table;
  }

  private Cell getFooterCell(final String text) {
    final var cell = new Cell(new Paragraph(text, new Font(COURIER, 10, Font.NORMAL)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(HorizontalAlignment.LEFT);

    return cell;
  }

  private void addRow(final Map.Entry<String, BigDecimal> item, final Table table) {
    final var descriptionCell =
        new Cell(new Paragraph(item.getKey(), new Font(Font.TIMES_ROMAN, 12, Font.NORMAL)));
    descriptionCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
    descriptionCell.setBorderColor(Color.DARK_GRAY);
    descriptionCell.setBorderWidth(1f);
    table.addCell(descriptionCell);
    final var sumCell =
        new Cell(
            new Paragraph(
                getFormattedString(item.getValue().negate()),
                new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    sumCell.setHorizontalAlignment(RIGHT);
    sumCell.setBorderColor(Color.DARK_GRAY);
    sumCell.setBorderWidth(1f);
    table.addCell(sumCell);
  }

  private Cell getItemTablHeaderCell(final String value) {
    final var cell = new Cell(new Phrase(value, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell.setHeader(true);
    cell.setBackgroundColor(Color.LIGHT_GRAY);
    cell.setBorderColor(Color.DARK_GRAY);
    cell.setBorderWidth(1f);
    cell.setVerticalAlignment(VerticalAlignment.CENTER);
    cell.setHorizontalAlignment(HorizontalAlignment.CENTER);

    return cell;
  }

  private Cell getRequisitValueCell(final String value) {
    final var cell = new Cell(new Paragraph(value, new Font(COURIER, 11, BOLDITALIC)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(HorizontalAlignment.LEFT);

    return cell;
  }

  private Cell getRequisitLabelCell(final String label) {
    final var labelFinal = format("%s: ", label);
    final var cell = new Cell(new Paragraph(labelFinal, new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(HorizontalAlignment.LEFT);

    return cell;
  }

  private String getFormattedString(final BigDecimal number) {
    if (number == null) {
      return "-- eur";
    }
    final var numberFinal = qRound(number);
    final DecimalFormat df = new DecimalFormat();
    df.setMaximumFractionDigits(2);
    df.setMinimumFractionDigits(2);
    df.setGroupingUsed(false);

    return df.format(numberFinal) + " eur";
  }
}
