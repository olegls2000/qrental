package ee.qrent.billing.ui.controller.report;

import static ee.qrent.billing.ui.controller.ControllerUtils.REPORT_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;

import java.util.List;

import ee.qrent.billing.report.api.in.query.GetWeeklyReportCalculationQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(REPORT_ROOT_PATH)
@AllArgsConstructor
public class WeeklyReportCalculationUseCaseController {

  private final GetQWeekQuery qWeekQuery;
  private final GetWeeklyReportCalculationQuery weeklyReportCalculationQuery;
  private final WeeklyReportCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new WeeklyReportCalculationAddRequest(), model);
    addWeeksToModel(model);
    addTypesToModel(model);

    return "forms/addWeeklyReportCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCallSignLink(
      @ModelAttribute final WeeklyReportCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      addWeeksToModel(model);
      addTypesToModel(model);

      return "forms/addWeeklyReportCalculation";
    }

    return "redirect:" + REPORT_ROOT_PATH + "/calculations";
  }

  private List<QWeekResponse> getWeeks() {
    final var lastCalculatedWeekId = weeklyReportCalculationQuery.getLastCalculatedQWeekId();
    if (lastCalculatedWeekId == null) {
      return qWeekQuery.getAll();
    }
    final var beforeCalculationQWeek = qWeekQuery.getOneBeforeById(lastCalculatedWeekId);

    return qWeekQuery.getAllAfterById(beforeCalculationQWeek.getId());
  }

  private void addAddRequestToModel(
      final WeeklyReportCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }

  private void addWeeksToModel(final Model model) {
    model.addAttribute("weeks", getWeeks());
  }

  private void addTypesToModel(final Model model) {
    model.addAttribute("types", weeklyReportCalculationQuery.getWeeklyReportTypes());
  }
}
