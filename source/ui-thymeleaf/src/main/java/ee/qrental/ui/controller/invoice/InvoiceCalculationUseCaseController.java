package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.common.core.utils.QWeek;
import ee.qrental.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceCalculationUseCaseController {

  private final InvoiceCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new InvoiceCalculationAddRequest(), model);
    model.addAttribute("years", List.of(2023));
    model.addAttribute("weeks", QWeek.values());

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

  private void addAddRequestToModel(
      final InvoiceCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
