package ee.qrental.contract.core.service.pdf;

import static com.lowagie.text.PageSize.A4;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class ContractToPdfConverter {

  @SneakyThrows
  public InputStream getPdfInputStream(final ContractPdfModel model) {
    final var invoicePdfDoc = new Document(A4, 40f, 40f, 50f, 50f);
    final var invoicePdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(invoicePdfDoc, invoicePdfOutputStream);
    invoicePdfDoc.open();
    // TODO add a document content here ...

    invoicePdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(invoicePdfOutputStream.toByteArray());
  }
}
