package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceQueryController {

  private final GetInvoiceQuery invoiceQuery;

  @GetMapping
  public String getDriverView(final Model model) {
    model.addAttribute("invoices", invoiceQuery.getAll());
    return "invoices";
  }
}
