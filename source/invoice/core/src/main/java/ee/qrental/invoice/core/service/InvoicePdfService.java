package ee.qrental.invoice.core.service;

import static com.lowagie.text.Font.BOLDITALIC;
import static com.lowagie.text.Font.COURIER;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.RIGHT;
import static java.awt.Color.white;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.SHORT;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceItem;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class InvoicePdfService implements InvoicePdfUseCase {

  private final InvoiceLoadPort loadPort;

  @SneakyThrows
  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var invoice = loadPort.loadById(id);
    final var number = invoice.getNumber();
    final var creationDate = invoice.getCreated().format(ofLocalizedDate(SHORT));
    final var weekNumber = invoice.getWeekNumber();
    final var year = invoice.getCreated().getYear();
    final var startDate =
        QTimeUtils.getFirstDayOfWeekInYear(year, weekNumber).format(ofLocalizedDate(SHORT));
    final var endDate =
        QTimeUtils.getLastDayOfWeekInYear(year, weekNumber).format(ofLocalizedDate(SHORT));
    final var driverCompany = invoice.getDriverCompany();
    final var driverInfo = invoice.getDriverInfo();
    final var driverCompanyRegNumber = invoice.getDriverCompanyRegNumber();
    final var driverCompanyAddress = invoice.getDriverCompanyAddress();
    final var qFirmEmail = invoice.getQFirmEmail();
    final var qFirmPostAddress = invoice.getQFirmPostAddress();
    final var qFirmPhone = invoice.getQFirmPhone();
    final var qFirmName = invoice.getQFirmName();
    final var qFirmRegNumber = invoice.getQFirmRegNumber();
    final var qFirmVatNumber = invoice.getQFirmVatNumber();
    final var qFirmIban = invoice.getQFirmIban();
    final var qFirmBank = invoice.getQFirmBank();
    final var invoiceTotalAmount =
        invoice.getItems().stream()
            .map(InvoiceItem::getAmount)
            .filter(amount -> amount.compareTo(BigDecimal.ZERO) < 0)
            .reduce(BigDecimal::add)
            .orElseThrow(() -> new RuntimeException("No Negative Transactions during Invoice period."));
    final var driverCompanyVat = invoice.getDriverCompanyVat();
    final var vatAmount = invoiceTotalAmount.divide(BigDecimal.valueOf(5));
    final var arveSum = invoiceTotalAmount.add(vatAmount);
    final var invoicePdfDoc = new Document(PageSize.A4, 40f, 40f, 50f, 50f);
    final var invoicePdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(invoicePdfDoc, invoicePdfOutputStream);
    final var header = getHeader(number, creationDate, startDate, endDate);
    final var requisites =
        getRequisites(
            driverCompany,
            driverCompanyAddress,
            driverCompanyRegNumber,
            driverCompanyVat,
            driverInfo);
    final var itemsTable = getItemsTable(invoice);
    final var invoiceSumma = getSumms(invoiceTotalAmount, vatAmount, arveSum);
    final var invoiceTotal = getTotal(arveSum);
    final var interest = getInterest();
    final var footer =
        getFooter(
            qFirmName,
            qFirmPostAddress,
            qFirmBank,
            qFirmRegNumber,
            qFirmEmail,
            qFirmIban,
            qFirmVatNumber,
            qFirmPhone);

    invoicePdfDoc.open();
    invoicePdfDoc.add(header);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(requisites);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(itemsTable);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(invoiceSumma);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(invoiceTotal);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(interest);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(footer);
    invoicePdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(invoicePdfOutputStream.toByteArray());
  }

  @SneakyThrows
  private Table getHeader(
      final String invoiceNumber,
      final String invoiceDate,
      final String startDate,
      final String endDate) {
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
    cell.setRowspan(3);
    cell.setBorder(NO_BORDER);
    header.addCell(cell);

    final var cell1 =
        new Cell(
            new Paragraph("Arve Nr.: " + invoiceNumber, new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
    cell1.setBorder(NO_BORDER);
    header.addCell(cell1);

    final var cell2 =
        new Cell(new Paragraph("Data : " + invoiceDate, new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
    cell2.setBorder(NO_BORDER);
    header.addCell(cell2);

    final var cell3 =
        new Cell(
            new Paragraph(
                format("Ajaperiood: %s - %s", startDate, endDate),
                new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell3.setBorder(NO_BORDER);
    header.addCell(cell3);

    return header;
  }

  private Table getRequisites(
      final String invoiceDriverCompany,
      final String invoiceDriverCompanyAddress,
      final String invoiceDriverCompanyRegNumber,
      final String driverCompanyVat,
      final String driverInfo) {
    final var requisites = new Table(2);
    requisites.setWidths(new float[] {1, 2});
    requisites.setPadding(0f);
    requisites.setSpacing(-1f);
    requisites.setWidth(60f);
    requisites.setHorizontalAlignment(HorizontalAlignment.LEFT);
    requisites.setBorder(NO_BORDER);
    requisites.addCell(getRequisitLabelCell("Maksja"));
    requisites.addCell(getRequisitValueCell(invoiceDriverCompany));
    requisites.addCell(getRequisitLabelCell("Adress"));
    requisites.addCell(getRequisitValueCell(invoiceDriverCompanyAddress));
    requisites.addCell(getRequisitLabelCell("Äriregistri nr."));
    requisites.addCell(getRequisitValueCell(invoiceDriverCompanyRegNumber));
    requisites.addCell(getRequisitLabelCell("KMKR nr."));
    requisites.addCell(getRequisitValueCell(driverCompanyVat));
    requisites.addCell(getRequisitLabelCell("Arvesaja"));
    requisites.addCell(getRequisitValueCell(driverInfo));

    return requisites;
  }

  private Table getItemsTable(final Invoice invoice) {
    final var table = new Table(2);
    table.setWidths(new float[] {3, 1});
    table.setPadding(2f);
    table.setSpacing(1f);
    table.setWidth(100f);
    table.setHorizontalAlignment(HorizontalAlignment.LEFT);
    table.setBorderColor(Color.BLACK);
    table.addCell(getItemTablHeaderCell("Selgitus"));
    table.addCell(getItemTablHeaderCell("Summa"));
    invoice.getItems().forEach(item -> addRow(item, table));

    return table;
  }

  private Table getSumms(
      final BigDecimal invoiceTotalAmount, final BigDecimal vatAmount, final BigDecimal arveSum) {
    final var table = new Table(2);
    table.setWidths(new float[] {2, 1});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(50f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);
    table.addCell(getSummLabelCell("Summa"));
    table.addCell(getSummValueCell(invoiceTotalAmount));
    table.addCell(getSummLabelCell("Käibemaks (20%)"));
    table.addCell(getSummValueCell(vatAmount));
    table.addCell(getSummLabelCell("Arve summa"));
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

  private Table getInterest() {
    final var table = new Table(2);
    table.setWidths(new float[] {2, 1});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(50f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);
    table.addCell(getTotalLabelCell("Vivised dd.MM.yyyy - dd.MM.yyyy perioodi eest"));
    table.addCell(getTotalValueCell(null));
    table.addCell(getTotalLabelCell("Viisiste üldsumma"));
    table.addCell(getTotalValueCell(null));
    table.addCell(getTotalLabelCell("Tasuda kokku (kohustused + viivised)"));
    table.addCell(getTotalValueCell(null));

    return table;
  }

  private Table getTotal(final BigDecimal arveSum) {
    final var table = new Table(2);
    table.setWidths(new float[] {2, 1});
    table.setSpacing(-1f);
    table.setPadding(0f);
    table.setWidth(50f);
    table.setHorizontalAlignment(RIGHT);
    table.setBorder(NO_BORDER);
    table.addCell(getTotalLabelCell("Eelmise perioodi ettemaks"));
    table.addCell(getTotalValueCell(null));
    table.addCell(getTotalLabelCell("Eelmise perioodi võlgnevus"));
    table.addCell(getTotalValueCell(null));
    table.addCell(getTotalLabelCell("Kohustuse summa kokku"));
    table.addCell(getTotalValueCell(null));
    table.addCell(getTotalLabelCell("Tasuda kokku"));
    table.addCell(getTotalValueCell(arveSum));

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
    table.addCell(getFooterCell(format("Adress: %s", qFirmAddress)));
    table.addCell(getFooterCell(format("Äriregistri nr.: %s", qFirmBank)));
    table.addCell(getFooterCell(format("Äriregistri nr.: %s", qFirmRegNumber)));
    table.addCell(getFooterCell(format("E-mail: %s", qFirmEmail)));
    table.addCell(getFooterCell(format("IBAN: %s", qFirmIban)));
    table.addCell(getFooterCell(format("KMKR: %s", qFirmVatNumber)));
    table.addCell(getFooterCell(format("Telefon: %s", qFirmPhone)));

    return table;
  }

  private Cell getFooterCell(final String text) {
    final var cell = new Cell(new Paragraph(text, new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
    cell.setBorder(NO_BORDER);
    cell.setHorizontalAlignment(HorizontalAlignment.LEFT);

    return cell;
  }

  private void addRow(final InvoiceItem item, final Table table) {
    final var descriptionCell =
        new Cell(new Paragraph(item.getType(), new Font(Font.TIMES_ROMAN, 12, Font.NORMAL)));
    descriptionCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
    table.addCell(descriptionCell);
    final var sumCell =
        new Cell(
            new Paragraph(
                getFormattedString(item.getAmount()), new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    sumCell.setHorizontalAlignment(RIGHT);
    table.addCell(sumCell);
  }

  private Cell getItemTablHeaderCell(final String value) {
    final var cell = new Cell(new Phrase(value, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell.setHeader(true);
    cell.setBackgroundColor(Color.LIGHT_GRAY);
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
      return "---- eur";
    }
    final var numberFinal = number.setScale(2, BigDecimal.ROUND_DOWN).abs();
    DecimalFormat df = new DecimalFormat();
    df.setMaximumFractionDigits(2);
    df.setMinimumFractionDigits(2);
    df.setGroupingUsed(false);

    return df.format(numberFinal) + " eur";
  }
}
