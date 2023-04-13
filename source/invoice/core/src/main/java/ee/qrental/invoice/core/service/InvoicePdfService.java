package ee.qrental.invoice.core.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import java.io.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoicePdfService implements InvoicePdfUseCase {

  private final InvoiceLoadPort loadPort;

  @Override
  public InputStream getPdfInputStreamById(final Long id) {
    final var invoice = loadPort.loadById(id);
    final var invoiceNumber = invoice.getNumber();
    final var invoicePdfDoc = new Document();
    final var invoicePdfOutputStream = new ByteArrayOutputStream();
    final var writer = PdfWriter.getInstance(invoicePdfDoc, invoicePdfOutputStream);
    invoicePdfDoc.open();
    invoicePdfDoc.add(new Paragraph("Draft invoice pdf! "));
    invoicePdfDoc.add(new Paragraph("Number: " + invoiceNumber));
    invoicePdfDoc.close();
    writer.close();

    return new ByteArrayInputStream(invoicePdfOutputStream.toByteArray());
  }
}
