package ee.qrent.billing.ui.controller.transaction.calculation.rent;

import static ee.qrent.common.utils.QTimeUtils.getWeekNumber;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.RENTS_ROOT_PATH;


import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import java.time.LocalDate;
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
  private final GetQWeekQuery qWeekQuery;
  private final RentCalculationAddUseCase addUseCase;
  private final QDateFormatter qDateFormatter;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var rentCalculationRequest = new RentCalculationAddRequest();
    final var currentWeek = qWeekQuery.getCurrentWeek();
    rentCalculationRequest.setQWeekId(currentWeek.getId());
    addAddRequestToModel(rentCalculationRequest, model);
    model.addAttribute("nextWeek", currentWeek);

    return "forms/addRentCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCalculation(
      @ModelAttribute final RentCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
      addAddRequestToModel(addRequest, model);
      final LocalDate currentDate = LocalDate.now();

      //TODO replace with qWeekQuery.getCurrentWeek()
      final var currentWeek = qWeekQuery.getByYearAndNumber(currentDate.getYear(), getWeekNumber(currentDate));

      model.addAttribute("nextWeek", currentWeek);

      return "forms/addRentCalculation";
    }

    return "redirect:" + RENTS_ROOT_PATH + "/calculations";
  }

  private void addAddRequestToModel(final RentCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
