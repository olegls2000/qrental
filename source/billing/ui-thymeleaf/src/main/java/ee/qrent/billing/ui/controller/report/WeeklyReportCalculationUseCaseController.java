package ee.qrent.billing.ui.controller.report;

import static ee.qrent.billing.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;
import static ee.qrent.billing.ui.controller.ControllerUtils.WEEKLY_REPORT_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.invoice.api.in.query.GetInvoiceCalculationQuery;
import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceCalculationAddUseCase;
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
@RequestMapping(WEEKLY_REPORT_ROOT_PATH)
@AllArgsConstructor
public class WeeklyReportCalculationUseCaseController {

  private final GetQWeekQuery qWeekQuery;
  private final GetWeeklyReportCalculationQuery weeklyReportCalculationQuery;
  private final WeeklyReportCalculationAddUseCase addUseCase;

  @GetMapping(value = "/calculations/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new WeeklyReportCalculationAddRequest(), model);
    addWeeksToModel(model);
    addAddTypesToModel(model);

    return "forms/addWeeklyReportCalculation";
  }

  @PostMapping(value = "/calculations/add")
  public String addCallSignLink(
      @ModelAttribute final WeeklyReportCalculationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      addWeeksToModel(model);
      addAddTypesToModel(model);

      return "forms/addCalculation";
    }

    return "redirect:" + INVOICE_ROOT_PATH + "/calculations";
  }

  private List<QWeekResponse> getWeeks() {
    // final var lastCalculatedWeekId = weeklyReportCalculationQuery.getLastCalculatedQWeekId();
    final var lastCalculatedWeekId = 118L;
    // if (lastCalculatedWeekId == null) {
    //  return qWeekQuery.getAll();
    // }

    return qWeekQuery.getAllAfterById(lastCalculatedWeekId);
  }

  private void addAddRequestToModel(
      final WeeklyReportCalculationAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }

  private void addWeeksToModel(final Model model) {
    model.addAttribute("weeks", getWeeks());
  }

  private void addAddTypesToModel(final Model model) {
    model.addAttribute("types", weeklyReportCalculationQuery.getWeeklyReportTypes());
  }
}
