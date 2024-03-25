package ee.qrental.ui.controller.bonus;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.OBLIGATIONS_ROOT_PATH;

import ee.qrental.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(OBLIGATIONS_ROOT_PATH)
@AllArgsConstructor
public class ObligationCalculationUseCaseController {
  private final GetQWeekQuery qWeekQuery;
  private final ObligationCalculationAddUseCase addUseCase;
  private final GetObligationCalculationQuery obligationCalculationQuery;
  private final QDateFormatter qDateFormatter;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var obligationCalculationRequest = new ObligationCalculationAddRequest();
    obligationCalculationRequest.setComment("UI manually triggered");
    addAddRequestToModel(obligationCalculationRequest, model);
    model.addAttribute("weeks", getWeeks());

    return "forms/addObligationCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final ObligationCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      model.addAttribute("weeks", getWeeks());

      return "forms/addObligationCalculation";
    }

    return "redirect:" + OBLIGATIONS_ROOT_PATH + "/calculations";
  }

  private List<QWeekResponse> getWeeks() {
    final var lastCalculatedWeekId = obligationCalculationQuery.getLastCalculatedQWeekId();
    if (lastCalculatedWeekId == null) {
      return qWeekQuery.getAll();
    }

    return qWeekQuery.getAllAfterById(lastCalculatedWeekId);
  }

  private void addAddRequestToModel(
      final ObligationCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
