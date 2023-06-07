package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.ControllerUtils.TRANSACTION_ROOT_PATH;
import static ee.qrental.ui.controller.transaction.TransactionFilterRequestUtils.addCleanFilterRequestToModel;
import static ee.qrental.ui.controller.transaction.TransactionFilterRequestUtils.addWeekOptionsToModel;

import ee.qrental.balance.api.in.request.BalanceCalculationAddRequest;
import ee.qrental.balance.api.in.usecase.BalanceCalculationAddUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(TRANSACTION_ROOT_PATH)
@AllArgsConstructor
public class BalanceCalculationUseCaseController {

  private final BalanceCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new BalanceCalculationAddRequest(), model);
    addWeekOptionsToModel(model);

    return "forms/addBalanceCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final BalanceCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      return "forms/addBalanceCalculation";
    }

    return "redirect:" + TRANSACTION_ROOT_PATH + "/calculations";
  }

  private void addAddRequestToModel(
      final BalanceCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
