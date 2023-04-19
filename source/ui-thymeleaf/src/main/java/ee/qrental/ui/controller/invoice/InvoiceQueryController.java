package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.in.request.InvoiceSendByEmailRequest;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceSendByEmailUseCase;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceQueryController {

  private final GetInvoiceQuery invoiceQuery;
  private final InvoiceSendByEmailUseCase invoiceSendByEmailUseCase;
  private final InvoicePdfUseCase invoicePdfUseCase;

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
        .body(new InputStreamResource(invoicePdfUseCase.getPdfInputStreamById(id)));
  }

  @GetMapping(value = "/email/send-form/{id}")
  public String addForm(@PathVariable("id") long id, final Model model) {
    final var invoice = invoiceQuery.getById(id);
    model.addAttribute("emailSendRequest", InvoiceSendByEmailRequest.builder().id(id).build());

    return "forms/emailSendInvoice";
  }

  @PostMapping("/email/send")
  public String sendByEmail(final InvoiceSendByEmailRequest emailSendRequest) throws IOException {
    invoiceSendByEmailUseCase.sendByEmail(emailSendRequest);

    return "redirect:" + INVOICE_ROOT_PATH;
  }
}
