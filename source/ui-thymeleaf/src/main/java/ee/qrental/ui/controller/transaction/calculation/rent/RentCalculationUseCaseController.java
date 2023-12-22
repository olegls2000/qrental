package ee.qrental.ui.controller.transaction.calculation.rent;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.RENTS_ROOT_PATH;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(RENTS_ROOT_PATH)
@AllArgsConstructor
public class RentCalculationUseCaseController {

  private final GetRentCalculationQuery rentCalculationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final RentCalculationAddUseCase addUseCase;
  private final QDateFormatter qDateFormatter;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var nextWeek = getFirstPossibleWeek();
    final var rentCalculationRequest = new RentCalculationAddRequest();
    rentCalculationRequest.setQWeekId(nextWeek.getId());
    addAddRequestToModel(rentCalculationRequest, model);
    model.addAttribute("nextWeek", getFirstPossibleWeek());

    return "forms/addRentCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final RentCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
      addAddRequestToModel(addRequest, model);
      model.addAttribute("nextWeek", getFirstPossibleWeek());

      return "forms/addRentCalculation";
    }

    return "redirect:" + RENTS_ROOT_PATH + "/calculations";
  }

  private void addAddRequestToModel(final RentCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }

  private QWeekResponse getFirstPossibleWeek() {
    final var firstPossibleQWeekIdForRentCalculation =
        rentCalculationQuery.getLastCalculatedQWeekId();

    return qWeekQuery.getOneAfterById(firstPossibleQWeekIdForRentCalculation);
  }
}
