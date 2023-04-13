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
  public InputStream getPdfInputStreamById(Long id)throws FileNotFoundException {
    final var invoice = loadPort.loadById(id);
    final var invoiceNumber = invoice.getNumber();
    final var myPDFDoc = new Document();
    final var fileName = "invoice_" + invoiceNumber + ".pdf";
    final var filePath = "./" + fileName;
    final var pdfOutputFile = new FileOutputStream(filePath);
    final var pdfWriter = PdfWriter.getInstance(myPDFDoc, pdfOutputFile);
    myPDFDoc.open();
    myPDFDoc.add(new Paragraph("Draft invoice pdf! "));
    myPDFDoc.add(new Paragraph("Number: "+ invoiceNumber));
    myPDFDoc.close();
    pdfWriter.close();
    return new FileInputStream(filePath);
  }
}
