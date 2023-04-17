package ee.qrental.invoice.core.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoicePdfService implements InvoicePdfUseCase {

  private final InvoiceLoadPort loadPort;

  @Override
  public InputStream getPdfInputStreamById(final Long id) throws IOException {
    final var invoice = loadPort.loadById(id);
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

    final var invoicePdfDoc =
        new Document(
            PageSize.A4,
            40f, // left
            40f, // right
            50f, // top
            50f); // down);
    final var invoicePdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(invoicePdfDoc, invoicePdfOutputStream);

    Table invoiceTable1 = new Table(2);
    invoiceTable1.setPadding(0f);
    invoiceTable1.setSpacing(0f);
    invoiceTable1.setWidth(100f);
    invoiceTable1.setBorderColor(Color.white);
    invoiceTable1.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTable1.setBorder(Rectangle.NO_BORDER);
    invoiceTable1.setBorder(Cell.NO_BORDER);

    float[] widthsTable2 = {1, 2};
    Table invoiceTable2 = new Table(2); // 2 columns
    invoiceTable2.setWidths(widthsTable2);
    invoiceTable2.setPadding(0f);
    invoiceTable2.setSpacing(-1f);
    invoiceTable2.setWidth(60f);
    invoiceTable2.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.setBorder(Rectangle.NO_BORDER);
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
    invoiceSumma.setBorder(Rectangle.NO_BORDER);

    float[] widths2 = {2, 1};
    Table invoiceTotal = new Table(2);
    invoiceTotal.setWidths(widths2);
    invoiceTotal.setSpacing(-1f);
    invoiceTotal.setPadding(0f);
    invoiceTotal.setWidth(50f);
    invoiceTotal.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.setBorder(Rectangle.NO_BORDER);

    Table invoiceQFirm = new Table(3);
    invoiceQFirm.setSpacing(-2f);
    invoiceQFirm.setPadding(0f);
    invoiceQFirm.setWidth(100f);
    invoiceQFirm.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceQFirm.setBorder(Rectangle.NO_BORDER);

    invoicePdfDoc.open();

    Image logo = Image.getInstance("Images/qRentalGroup_gorznt.png");
    logo.scaleAbsolute(150f, 60f);

    // Table 1
    Cell cell = new Cell(logo);
    cell.setRowspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell);

    Cell cell1 =
        new Cell(
            new Paragraph("Arve Nr.: " + invoiceNumber, new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
    cell1.setBorder(Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell1);

    Cell cell2 =
        new Cell(new Paragraph("Data : " + invoiceDate, new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
    cell2.setBorder(Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell2);

    Cell cell3 =
        new Cell(
            new Paragraph(
                "Ajaperiood: " + "Date-Date/Month/Year",
                new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell3.setBorder(Rectangle.NO_BORDER);
    invoiceTable1.addCell(cell3);

    // Table 2
    Cell cell21 = new Cell(new Paragraph("Maksja: ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell21.setBorder(Rectangle.NO_BORDER);
    cell21.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell21);

    Cell cell22 =
        new Cell(new Paragraph(invoiceDriverCompany, new Font(Font.COURIER, 11, Font.BOLDITALIC)));
    cell22.setBorder(Rectangle.NO_BORDER);
    cell22.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell22);

    Cell cell23 = new Cell(new Paragraph("Aadress: ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell23.setBorder(Rectangle.NO_BORDER);
    cell23.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell23);

    Cell cell24 =
        new Cell(
            new Paragraph(
                invoiceDriverCompanyAddress, new Font(Font.COURIER, 11, Font.BOLDITALIC)));
    cell24.setBorder(Rectangle.NO_BORDER);
    cell24.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell24);

    Cell cell25 =
        new Cell(new Paragraph("Äriregistri nr: ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell25.setBorder(Rectangle.NO_BORDER);
    cell25.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell25);

    Cell cell26 =
        new Cell(
            new Paragraph(
                invoiceDriverCompanyRegNumber, new Font(Font.COURIER, 11, Font.BOLDITALIC)));
    cell26.setBorder(Rectangle.NO_BORDER);
    cell26.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell26);

    Cell cell27 = new Cell(new Paragraph("KMKR nr. : ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell27.setBorder(Rectangle.NO_BORDER);
    cell27.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell27);

    Cell cell28 = new Cell(new Paragraph("...... ", new Font(Font.COURIER, 11, Font.BOLDITALIC)));
    cell28.setBorder(Rectangle.NO_BORDER);
    cell28.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceTable2.addCell(cell28);

    // Main Table

    Cell cell31 =
        new Cell(new Paragraph("Renditasu ", new Font(Font.TIMES_ROMAN, 12, Font.NORMAL)));
    cell31.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceMainTable.addCell(cell31);

    Cell cell32 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
    cell32.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceMainTable.addCell(cell32);

    // Summa Table

    Cell cell4 = new Cell(new Paragraph("Summa:", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell4.setBorder(Rectangle.NO_BORDER);
    cell4.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell4);

    Cell cell5 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell5.setBorder(Rectangle.NO_BORDER);
    cell5.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell5);

    Cell cell6 = new Cell(new Paragraph("Käibemaks %:", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell6.setBorder(Rectangle.NO_BORDER);
    cell6.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell6);

    Cell cell7 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell7.setBorder(Rectangle.NO_BORDER);
    cell7.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell7);

    Cell cell8 =
        new Cell(new Paragraph("Arve summa EUR:", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell8.setBorder(Rectangle.NO_BORDER);
    cell8.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell8);

    Cell cell9 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell9.setBorder(Rectangle.NO_BORDER);
    cell9.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceSumma.addCell(cell9);

    // Total Table
    Cell cell10 =
        new Cell(
            new Paragraph("Eelmise perioodi ettemaks:", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell10.setBorder(Rectangle.NO_BORDER);
    cell10.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell10);

    Cell cell11 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell11.setBorder(Rectangle.NO_BORDER);
    cell11.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell11);

    Cell cell12 =
        new Cell(
            new Paragraph(
                "Eelmise perioodi võlgnevus:", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell12.setBorder(Rectangle.NO_BORDER);
    cell12.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell12);

    Cell cell13 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell13.setBorder(Rectangle.NO_BORDER);
    cell13.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell13);

    Cell cell14 = new Cell(new Paragraph("Vivis:", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell14.setBorder(Rectangle.NO_BORDER);
    cell14.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell14);

    Cell cell15 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell15.setBorder(Rectangle.NO_BORDER);
    cell15.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell15);

    Cell cell16 =
        new Cell(new Paragraph("Tasuda kokku:", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell16.setBorder(Rectangle.NO_BORDER);
    cell16.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell16);

    Cell cell17 = new Cell(new Paragraph("...... ", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
    cell17.setBorder(Rectangle.NO_BORDER);
    cell17.setHorizontalAlignment(HorizontalAlignment.RIGHT);
    invoiceTotal.addCell(cell17);

    // Q Firm  Table
    Cell cell41 =
        new Cell(new Paragraph(invoiceQFirmName, new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
    cell41.setBorder(Rectangle.NO_BORDER);
    cell41.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell41);

    Cell cell42 =
        new Cell(new Paragraph("invoiceQFirmAddress", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
    cell42.setBorder(Rectangle.NO_BORDER);
    cell42.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell42);

    Cell cell43 =
        new Cell(new Paragraph(invoiceQFirmBank, new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
    cell43.setBorder(Rectangle.NO_BORDER);
    cell43.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell43);

    Cell cell44 =
        new Cell(
            new Paragraph(
                "Äriregistri nr.: " + invoiceQFirmRegNumber,
                new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
    cell44.setBorder(Rectangle.NO_BORDER);
    cell44.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell44);

    Cell cell45 =
        new Cell(
            new Paragraph(
                "E-mail: " + "invoiceQFirmRegEmail", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
    cell45.setBorder(Rectangle.NO_BORDER);
    cell45.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell45);

    Cell cell46 =
        new Cell(
            new Paragraph("IBAN: " + invoiceQFirmIban, new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
    cell46.setBorder(Rectangle.NO_BORDER);
    cell46.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell46);

    Cell cell47 =
        new Cell(
            new Paragraph(
                "KMKR :" + invoiceQFirmVatNumber, new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
    cell47.setBorder(Rectangle.NO_BORDER);
    cell47.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell47);

    Cell cell48 =
        new Cell(
            new Paragraph(
                "Telefon: " + "invoiceQFirmPhone", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
    cell48.setBorder(Rectangle.NO_BORDER);
    cell48.setHorizontalAlignment(HorizontalAlignment.LEFT);
    invoiceQFirm.addCell(cell48);

    invoicePdfDoc.add(invoiceTable1);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(invoiceTable2);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(invoiceMainTable);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(invoiceSumma);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(invoiceTotal);
    invoicePdfDoc.add(new Paragraph("\n"));
    invoicePdfDoc.add(invoiceQFirm);

    invoicePdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(invoicePdfOutputStream.toByteArray());
  }
}
