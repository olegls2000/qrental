package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceQueryController {

  private final GetInvoiceQuery invoiceQuery;
  private final InvoicePdfUseCase pdfUseCase;

  @GetMapping
  public String getTableView(final Model model) {
    model.addAttribute("invoices", invoiceQuery.getAll());
    return "invoices";
  }

  @GetMapping("/pdf/{id}")
  @ResponseBody
  public ResponseEntity<InputStreamResource> getPdf(@PathVariable("id") long id)
          throws IOException {

    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(pdfUseCase.getPdfInputStreamById(id)));
  }
}
