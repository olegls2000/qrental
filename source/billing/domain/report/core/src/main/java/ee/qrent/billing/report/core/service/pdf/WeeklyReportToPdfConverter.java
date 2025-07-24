package ee.qrent.billing.report.core.service.pdf;

import static com.lowagie.text.PageSize.A4;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class WeeklyReportToPdfConverter {

  @SneakyThrows
  public InputStream getPdfInputStream(final WeeklyReportPdfModel model) {
    final var invoicePdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
    final var invoicePdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(invoicePdfDoc, invoicePdfOutputStream);
    // TODO add template with data
    invoicePdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(invoicePdfOutputStream.toByteArray());
  }
}