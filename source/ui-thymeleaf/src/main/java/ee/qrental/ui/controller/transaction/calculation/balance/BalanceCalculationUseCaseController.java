package ee.qrental.ui.controller.transaction.calculation.balance;

import static ee.qrental.ui.controller.util.ControllerUtils.TRANSACTION_ROOT_PATH;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
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

  private final GetQWeekQuery qWeekQuery;
  private final BalanceCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new BalanceCalculationAddRequest(), model);
    model.addAttribute("weeks", qWeekQuery.getAll());

    return "forms/addBalanceCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final BalanceCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      model.addAttribute("weeks", qWeekQuery.getAll());
      return "forms/addBalanceCalculation";
    }

    return "redirect:" + TRANSACTION_ROOT_PATH + "/calculations";
  }

  private void addAddRequestToModel(
      final BalanceCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
