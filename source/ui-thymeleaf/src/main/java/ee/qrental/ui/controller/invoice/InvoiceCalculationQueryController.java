package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.invoice.api.in.query.GetInvoiceCalculationQuery;
import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceCalculationQueryController {

  private final GetInvoiceCalculationQuery invoiceCalculationQuery;
  private final GetInvoiceQuery invoiceQuery;
  private final QDateFormatter qDateFormatter;

  @GetMapping("/calculations")
  public String getCalculationView(final Model model) {
    model.addAttribute("calculations", invoiceCalculationQuery.getAll());

    return "invoiceCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getBalanceCalculationView(@PathVariable("id") long id, final Model model) {
    final var calculation =  invoiceCalculationQuery.getById(id);
    model.addAttribute("invoices", invoiceQuery.getAllByCalculationId(id));
    model.addAttribute("calculation", calculation);
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "detailView/invoiceCalculation";
  }
}
