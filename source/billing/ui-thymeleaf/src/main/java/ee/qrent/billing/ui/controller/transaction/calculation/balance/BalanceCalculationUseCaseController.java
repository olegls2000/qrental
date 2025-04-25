package ee.qrent.billing.ui.controller.transaction.calculation.balance;

import static ee.qrent.billing.ui.controller.ControllerUtils.BALANCE_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(BALANCE_ROOT_PATH)
@AllArgsConstructor
public class BalanceCalculationUseCaseController {

  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;
  private final BalanceCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new BalanceCalculationAddRequest(), model);
    model.addAttribute("weeks", getWeeks());

    return "forms/addBalanceCalculation";
  }

  private List<QWeekResponse> getWeeks() {
    final var lastCalculatedWeekId = balanceCalculationQuery.getLastCalculatedQWeekId();
    if (lastCalculatedWeekId == null) {
      return qWeekQuery.getAll();
    }

    return qWeekQuery.getAllAfterById(lastCalculatedWeekId);
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final BalanceCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      model.addAttribute("weeks", getWeeks());

      return "forms/addBalanceCalculation";
    }

    return "redirect:" + BALANCE_ROOT_PATH + "/calculations";
  }

  private void addAddRequestToModel(
      final BalanceCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
