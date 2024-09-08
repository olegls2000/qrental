package ee.qrental.ui.controller.insurance;

import static ee.qrental.ui.controller.util.ControllerUtils.INSURANCE_ROOT_PATH;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(INSURANCE_ROOT_PATH)
@AllArgsConstructor
public class InsuranceCalculationUseCaseController {

  private final GetBalanceCalculationQuery balanceCalculationQuery;
  private final InsuranceCalculationAddUseCase addUseCase;
  private final GetInsuranceCalculationQuery insuranceCalculationQuery;
  private final GetQWeekQuery qWeekQuery;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new InsuranceCalculationAddRequest(), model);
    model.addAttribute("weeks", getWeeks());

    return "forms/addInsuranceCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final InsuranceCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      model.addAttribute("weeks", getWeeks());

      return "forms/addInsuranceCalculation";
    }

    return "redirect:" + INSURANCE_ROOT_PATH + "/calculations";
  }

  private void addAddRequestToModel(
      final InsuranceCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }

  private List<QWeekResponse> getWeeks() {

    return qWeekQuery.getAllBetweenByIdsDefaultOrder(
        insuranceCalculationQuery.getStartQWeekId(), insuranceCalculationQuery.getEndQWeekId());
  }
}
