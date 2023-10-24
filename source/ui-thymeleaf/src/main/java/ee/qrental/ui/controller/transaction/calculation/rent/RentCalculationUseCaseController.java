package ee.qrental.ui.controller.transaction.calculation.rent;

import static ee.qrental.ui.controller.util.ControllerUtils.TRANSACTION_ROOT_PATH;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import ee.qrental.common.core.utils.QWeek;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import java.util.List;
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
public class RentCalculationUseCaseController {

  private final RentCalculationAddUseCase addUseCase;

  @GetMapping(value = "/rent-calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new RentCalculationAddRequest(), model);
    model.addAttribute("years", List.of(2023));
    model.addAttribute(
        "weeks", stream(QWeek.values()).filter(week -> week.getNumber() != 0).collect(toList()));

    return "forms/addRentCalculation";
  }

  @PostMapping(value = "/rent-calculations/add")
  public String addCalculation(
      @ModelAttribute final RentCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      return "forms/addRentCalculation";
    }

    return "redirect:" + TRANSACTION_ROOT_PATH + "/rent-calculations";
  }

  private void addAddRequestToModel(final RentCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
