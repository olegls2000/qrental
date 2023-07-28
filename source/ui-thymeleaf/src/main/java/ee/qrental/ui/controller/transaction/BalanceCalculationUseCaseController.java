package ee.qrental.ui.controller.transaction;

import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
import ee.qrental.common.core.utils.QWeek;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ee.qrental.ui.controller.ControllerUtils.TRANSACTION_ROOT_PATH;

@Controller
@RequestMapping(TRANSACTION_ROOT_PATH)
@AllArgsConstructor
public class BalanceCalculationUseCaseController {

  private final BalanceCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new BalanceCalculationAddRequest(), model);
    model.addAttribute("years", List.of(2023));
    model.addAttribute("weeks", Arrays.stream(QWeek.values()).filter(week -> week.getNumber() != 0).collect(Collectors.toList()));

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
