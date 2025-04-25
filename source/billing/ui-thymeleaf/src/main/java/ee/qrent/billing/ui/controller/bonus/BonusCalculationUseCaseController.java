package ee.qrent.billing.ui.controller.bonus;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.BONUS_ROOT_PATH;

import ee.qrent.billing.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrent.billing.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrent.billing.bonus.api.in.usecase.BonusCalculationAddUseCase;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(BONUS_ROOT_PATH)
@AllArgsConstructor
public class BonusCalculationUseCaseController {
  private final GetQWeekQuery qWeekQuery;
  private final BonusCalculationAddUseCase addUseCase;
  private final GetBonusCalculationQuery bonusCalculationQuery;
  private final QDateFormatter qDateFormatter;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var calculationRequest = new BonusCalculationAddRequest();
    calculationRequest.setComment("UI manually triggered");
    addAddRequestToModel(calculationRequest, model);
    model.addAttribute("weeks", getWeeks());

    return "forms/addBonusCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final BonusCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      model.addAttribute("weeks", getWeeks());

      return "forms/addBonusCalculation";
    }

    return "redirect:" + BONUS_ROOT_PATH + "/calculations";
  }

  private List<QWeekResponse> getWeeks() {
    final var lastCalculatedWeekId = bonusCalculationQuery.getLastCalculatedQWeekId();
    if (lastCalculatedWeekId == null) {
      return qWeekQuery.getAll();
    }

    return qWeekQuery.getAllAfterById(lastCalculatedWeekId);
  }

  private void addAddRequestToModel(
      final BonusCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
