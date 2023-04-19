package ee.qrental.invoice.core.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceItem;
import lombok.SneakyThrows;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;import java.util.ArrayList;

public class InvoiceToPdfConverter {
  private PdfWriter getPdfWriter(final ByteArrayOutputStream outputStream) {
    final var invoicePdfDoc = new Document(PageSize.A4, 40f, 40f, 50f, 50f);

    return PdfWriter.getInstance(invoicePdfDoc, outputStream);
  }

  @SneakyThrows
  public InputStream convert(final Invoice invoice) {
    final var invoiceNumber = invoice.getNumber();
    final var invoiceDate = invoice.getCreated();
    final var invoiceDriverCompany = invoice.getDriverCompany();
    final var invoiceDriverCompanyRegNumber = invoice.getDriverCompanyRegNumber();
    final var invoiceDriverCompanyAddress = invoice.getDriverCompanyAddress();
    final var invoiceQFirmName = invoice.getQFirmName();
    final var invoiceQFirmRegNumber = invoice.getQFirmRegNumber();
    final var invoiceQFirmVatNumber = invoice.getQFirmVatNumber();
    final var invoiceQFirmIban = invoice.getQFirmIban();
    final var invoiceQFirmBank = invoice.getQFirmBank();
    final var invoiceTotalAmount =
        invoice.getItems().stream()
            .map(InvoiceItem::getAmount)
            .filter(amount -> amount.compareTo(BigDecimal.ZERO) < 0)
            .reduce(BigDecimal::add)
            .get();
    final var driverCompanyVat = invoice.getDriverCompanyVat();
    final var vatAmount = invoiceTotalAmount.divide(java.math.BigDecimal.valueOf(5));
    final var arveSum = invoiceTotalAmount.add(vatAmount);

    final var pdfDocument = new Document(PageSize.A4, 40f, 40f, 50f, 50f);
    final var invoicePdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(pdfDocument, invoicePdfOutputStream);

    Table invoiceTable1 = new Table(2);
    invoiceTable1.setPadding(0f);
    invoiceTable1.setSpacing(0f);
    invoiceTable1.setWidth(100f);
    invoiceTable1.setBorderColor(Color.white);
    invoiceTable1.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTable1.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    invoiceTable1.setBorder(Cell.NO_BORDER);

    float[] widthsTable2 = {1, 2};
    Table invoiceTable2 = new Table(2); // 2 columns
    invoiceTable2.setWidths(widthsTable2);
    invoiceTable2.setPadding(0f);
    invoiceTable2.setSpacing(-1f);
    invoiceTable2.setWidth(60f);
    invoiceTable2.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    invoiceTable2.setBorder(Cell.NO_BORDER);

    float[] widths = {3, 1};
    Table invoiceMainTable = new Table(2);
    invoiceMainTable.setWidths(widths);
    invoiceMainTable.setPadding(2f);
    invoiceMainTable.setSpacing(1f);
    invoiceMainTable.setWidth(100f);
    invoiceMainTable.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceMainTable.setBorderColor(Color.BLACK);

    // header of the Main table
    ArrayList<String> headerTable = new ArrayList<>();
    headerTable.add("Selgitus");
    headerTable.add("Summa");

    headerTable.forEach(
        e -> {
          Cell current = new Cell(new Phrase(e));
          current.setHeader(true);
          current.setBackgroundColor(Color.LIGHT_GRAY);
          current.setVerticalAlignment(VerticalAlignment.CENTER);
          current.setHorizontalAlignment(HorizontalAlignment.CENTER);
          invoiceMainTable.addCell(current);
        });

    float[] widths1 = {2, 1};
    Table invoiceSumma = new Table(2);
    invoiceSumma.setWidths(widths1);
    invoiceSumma.setSpacing(-1f);
    invoiceSumma.setPadding(0f);
    invoiceSumma.setWidth(50f);
    invoiceSumma.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.setBorder(com.lowagie.text.Rectangle.NO_BORDER);

    float[] widths2 = {2, 1};
    Table invoiceTotal = new Table(2);
    invoiceTotal.setWidths(widths2);
    invoiceTotal.setSpacing(-1f);
    invoiceTotal.setPadding(0f);
    invoiceTotal.setWidth(50f);
    invoiceTotal.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.setBorder(com.lowagie.text.Rectangle.NO_BORDER);

    Table invoiceQFirm = new Table(3);
    invoiceQFirm.setSpacing(-2f);
    invoiceQFirm.setPadding(0f);
    invoiceQFirm.setWidth(100f);
    invoiceQFirm.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceQFirm.setBorder(com.lowagie.text.Rectangle.NO_BORDER);

    pdfDocument.open();

    com.lowagie.text.Image logo = Image.getInstance("Images/qRentalGroup_gorznt.png");
    logo.scaleAbsolute(150f, 60f);

    // Table 1
    Cell cell = new Cell(logo);
    cell.setRowspan(3);
    cell.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell);

    Cell cell1 =
        new Cell(
            new Paragraph(
                "Arve Nr.: " + invoiceNumber,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 14, com.lowagie.text.Font.BOLD)));
    cell1.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell1);

    Cell cell2 =
        new Cell(
            new Paragraph(
                "Data : " + invoiceDate,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 14, com.lowagie.text.Font.BOLD)));
    cell2.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell2);

    Cell cell3 =
        new Cell(
            new Paragraph(
                "Ajaperiood: " + "Date-Date/Month/Year",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD)));
    cell3.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell3);

    // Table 2
    Cell cell21 =
        new Cell(
            new Paragraph(
                "Maksja: ",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell21.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell21.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell21);

    Cell cell22 =
        new Cell(
            new Paragraph(
                invoiceDriverCompany,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.COURIER, 11, com.lowagie.text.Font.BOLDITALIC)));
    cell22.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell22.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell22);

    Cell cell23 =
        new Cell(
            new Paragraph(
                "Adress: ",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell23.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell23.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell23);

    Cell cell24 =
        new Cell(
            new Paragraph(
                invoiceDriverCompanyAddress,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.COURIER, 11, com.lowagie.text.Font.BOLDITALIC)));
    cell24.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell24.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell24);

    Cell cell25 =
        new Cell(
            new Paragraph(
                "Äriregistri nr: ",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell25.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell25.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell25);

    Cell cell26 =
        new Cell(
            new Paragraph(
                invoiceDriverCompanyRegNumber,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.COURIER, 11, com.lowagie.text.Font.BOLDITALIC)));
    cell26.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell26.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell26);

    Cell cell27 =
        new Cell(
            new Paragraph(
                "KMKR nr.: ",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell27.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell27.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell27);

    Cell cell28 =
        new Cell(
            new Paragraph(
                driverCompanyVat,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.COURIER, 11, com.lowagie.text.Font.BOLDITALIC)));
    cell28.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell28.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell28);

    // Main Table
    for (InvoiceItem item : invoice.getItems()) {
      if (item.getAmount().compareTo(BigDecimal.ZERO) > 0) {
        continue;
      }
      Cell cell31 =
          new Cell(
              new Paragraph(
                  item.getType(),
                  new com.lowagie.text.Font(
                      com.lowagie.text.Font.TIMES_ROMAN, 12, com.lowagie.text.Font.NORMAL)));
      cell31.setHorizontalAlignment(HorizontalAlignment.LEFT);
      invoiceMainTable.addCell(cell31);

      Cell cell32 =
          new Cell(
              new Paragraph(
                  getFormattedString(item.getAmount()),
                  new com.lowagie.text.Font(
                      com.lowagie.text.Font.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD)));
      cell32.setHorizontalAlignment(HorizontalAlignment.RIGHT);
      invoiceMainTable.addCell(cell32);
    }

    // Summa Table
    Cell cell4 =
        new Cell(
            new Paragraph(
                "Summa:",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell4.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell4.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell4);

    Cell cell5 =
        new Cell(
            new Paragraph(
                invoiceTotalAmount.toString(),
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell5.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell5.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell5);

    Cell cell6 =
        new Cell(
            new Paragraph(
                "Käibemaks %:",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell6.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell6.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell6);

    Cell cell7 =
        new Cell(
            new Paragraph(
                getFormattedString(vatAmount),
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell7.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell7.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell7);

    Cell cell8 =
        new Cell(
            new Paragraph(
                "Arve summa EUR:",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell8.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell8.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell8);

    Cell cell9 =
        new Cell(
            new Paragraph(
                arveSum.toString(),
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell9.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell9.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell9);

    // Total Table
    Cell cell10 =
        new Cell(
            new Paragraph(
                "Eelmise perioodi ettemaks:",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell10.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell10.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell10);

    Cell cell11 =
        new Cell(
            new Paragraph(
                "pole saadaval",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell11.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell11.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell11);

    Cell cell12 =
        new Cell(
            new Paragraph(
                "Eelmise perioodi võlgnevus:",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell12.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell12.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell12);

    Cell cell13 =
        new Cell(
            new Paragraph(
                "pole saadaval",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell13.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell13.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell13);

    Cell cell14 =
        new Cell(
            new Paragraph(
                "Vivis:",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell14.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell14.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell14);

    Cell cell15 =
        new Cell(
            new Paragraph(
                "pole saadaval",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell15.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell15.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell15);

    Cell cell16 =
        new Cell(
            new Paragraph(
                "Tasuda kokku:",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell16.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell16.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell16);

    Cell cell17 =
        new Cell(
            new Paragraph(
                getFormattedString(arveSum),
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 11, com.lowagie.text.Font.BOLD)));
    cell17.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell17.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell17);

    // Q Firm  Table
    Cell cell41 =
        new Cell(
            new Paragraph(
                invoiceQFirmName,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 10, com.lowagie.text.Font.BOLD)));
    cell41.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell41.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell41);

    Cell cell42 =
        new Cell(
            new Paragraph(
                "invoiceQFirmAddress",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 10, com.lowagie.text.Font.NORMAL)));
    cell42.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell42.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell42);

    Cell cell43 =
        new Cell(
            new Paragraph(
                invoiceQFirmBank,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 10, com.lowagie.text.Font.NORMAL)));
    cell43.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell43.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell43);

    Cell cell44 =
        new Cell(
            new Paragraph(
                "Äriregistri nr.: " + invoiceQFirmRegNumber,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 10, com.lowagie.text.Font.BOLD)));
    cell44.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell44.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell44);

    Cell cell45 =
        new Cell(
            new Paragraph(
                "E-mail: " + "invoiceQFirmRegEmail",
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 10, com.lowagie.text.Font.NORMAL)));
    cell45.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell45.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell45);

    Cell cell46 =
        new Cell(
            new Paragraph(
                "IBAN: " + invoiceQFirmIban,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 10, com.lowagie.text.Font.BOLD)));
    cell46.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell46.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell46);

    Cell cell47 =
        new Cell(
            new Paragraph(
                "KMKR :" + invoiceQFirmVatNumber,
                new com.lowagie.text.Font(
                    com.lowagie.text.Font.TIMES_ROMAN, 10, com.lowagie.text.Font.BOLD)));
    cell47.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    cell47.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell47);

    Cell cell48 =
        new Cell(
            new Paragraph(
                "Telefon: " + "invoiceQFirmPhone",
                new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_ROMAN, 10, Font.NORMAL)));
    cell48.setBorder(Rectangle.NO_BORDER);
    cell48.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell48);

    pdfDocument.add(invoiceTable1);
    pdfDocument.add(new Paragraph("\n"));
    pdfDocument.add(invoiceTable2);
    pdfDocument.add(new Paragraph("\n"));
    pdfDocument.add(invoiceMainTable);
    pdfDocument.add(new Paragraph("\n"));
    pdfDocument.add(invoiceSumma);
    pdfDocument.add(new Paragraph("\n"));
    pdfDocument.add(invoiceTotal);
    pdfDocument.add(new Paragraph("\n"));
    pdfDocument.add(invoiceQFirm);

    pdfDocument.close();
    writer.close();

    return new ByteArrayInputStream(invoicePdfOutputStream.toByteArray());
  }

  private String getFormattedString(BigDecimal number) {
    number = number.setScale(2, BigDecimal.ROUND_DOWN);
    DecimalFormat df = new DecimalFormat();
    df.setMaximumFractionDigits(2);
    df.setMinimumFractionDigits(0);
    df.setGroupingUsed(false);

    return df.format(number);
  }
}
