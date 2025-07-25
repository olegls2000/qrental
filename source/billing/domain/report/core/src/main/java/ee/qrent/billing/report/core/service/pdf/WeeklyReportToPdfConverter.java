package ee.qrent.billing.report.core.service.pdf;

import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.Rectangle.NO_BORDER;
import static com.lowagie.text.alignment.HorizontalAlignment.RIGHT;
import static java.awt.Color.white;
import static java.lang.String.format;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportToPdfConverter {

  private static String getTextOrEmpty(final String text) {
    if (text == null || text.isBlank()) {
      return "---";
    }
    return text;
  }

  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var weeklyReportPdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
    final var weeklyReportPdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(weeklyReportPdfDoc, weeklyReportPdfOutputStream);
    final var header = getHeader(model.getFirstName(), model.getLastName(), model.getTaxNumber(), model.getCallSign());

    weeklyReportPdfDoc.open();
    weeklyReportPdfDoc.add(header);
    weeklyReportPdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(weeklyReportPdfOutputStream.toByteArray());
  }
    @SneakyThrows
    private Table getHeader(
    final String firsrName, final String lastName, final Long taxNumber, final Integer callSign) {
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
                      new Paragraph("Dear" + firsrName + lastName, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
      cell1.setBorder(NO_BORDER);
      header.addCell(cell1);

      final var cell2 =
              new Cell(
                      new Paragraph("Isikukood: " + taxNumber, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
      cell2.setBorder(NO_BORDER);
      header.addCell(cell2);

      final var cell3 =
              new Cell(
                      new Paragraph("Callsign: " + callSign, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
      cell3.setBorder(NO_BORDER);
      header.addCell(cell3);

      return header;
    }

    // TODO add template with data



  }
