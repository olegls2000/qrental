package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.invoice.api.in.query.GetInvoiceCalculationQuery;
import ee.qrental.invoice.api.in.usecase.*;
import java.io.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceCalculationQueryController {

  private final GetInvoiceCalculationQuery invoiceCalculationQuery;

  @GetMapping("/calculations")
  public String getCalculationView(final Model model) {
    model.addAttribute("calculations", invoiceCalculationQuery.getAll());

    return "invoiceCalculations";
  }
}
