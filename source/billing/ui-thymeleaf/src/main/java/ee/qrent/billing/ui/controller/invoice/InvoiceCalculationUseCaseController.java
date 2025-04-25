package ee.qrent.billing.ui.controller.invoice;

import static ee.qrent.billing.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.invoice.api.in.query.GetInvoiceCalculationQuery;
import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceCalculationUseCaseController {

  private final GetQWeekQuery qWeekQuery;
  private final GetInvoiceCalculationQuery invoiceCalculationQuery;
  private final InvoiceCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new InvoiceCalculationAddRequest(), model);
    model.addAttribute("weeks", getWeeks());

    return "forms/addInvoiceCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCallSignLink(
      @ModelAttribute final InvoiceCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      return "forms/addCalculation";
    }

    return "redirect:" + INVOICE_ROOT_PATH + "/calculations";
  }

  private List<QWeekResponse> getWeeks() {
    final var lastCalculatedWeekId = invoiceCalculationQuery.getLastCalculatedQWeekId();
    if (lastCalculatedWeekId == null) {
      return qWeekQuery.getAll();
    }

    return qWeekQuery.getAllAfterById(lastCalculatedWeekId);
  }

  private void addAddRequestToModel(
      final InvoiceCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
