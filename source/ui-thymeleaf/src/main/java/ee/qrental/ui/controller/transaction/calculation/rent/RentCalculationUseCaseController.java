package ee.qrental.ui.controller.transaction.calculation.rent;

import static ee.qrental.ui.controller.util.ControllerUtils.RENTS_ROOT_PATH;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
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
@RequestMapping(RENTS_ROOT_PATH)
@AllArgsConstructor
public class RentCalculationUseCaseController {

  private final GetRentCalculationQuery rentCalculationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final RentCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new RentCalculationAddRequest(), model);
    model.addAttribute("weeks", getWeeks());

    return "forms/addRentCalculation";
  }

  private List<QWeekResponse> getWeeks() {
    final var lastCalculatedWeekId = rentCalculationQuery.getLastCalculatedQWeekId();
    if (lastCalculatedWeekId == null) {
      return qWeekQuery.getAll();
    }

    return qWeekQuery.getAllAfterById(lastCalculatedWeekId);
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final RentCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      return "forms/addRentCalculation";
    }

    return "redirect:" + RENTS_ROOT_PATH + "/calculations";
  }

  private void addAddRequestToModel(final RentCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
