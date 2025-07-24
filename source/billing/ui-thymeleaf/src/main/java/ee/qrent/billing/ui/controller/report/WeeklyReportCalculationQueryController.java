package ee.qrent.billing.ui.controller.report;

import static ee.qrent.billing.ui.controller.ControllerUtils.REPORT_ROOT_PATH;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;

import ee.qrent.billing.report.api.in.query.GetWeeklyReportCalculationQuery;
import ee.qrent.billing.report.api.in.query.GetWeeklyReportQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(REPORT_ROOT_PATH)
@AllArgsConstructor
public class WeeklyReportCalculationQueryController {

  private final GetWeeklyReportCalculationQuery weeklyReportCalculationQuery;
  private final GetWeeklyReportQuery weeklyReportQuery;
  private final QDateFormatter qDateFormatter;

  @GetMapping("/calculations")
  public String getCalculationsView(final Model model) {
    model.addAttribute("calculations", weeklyReportCalculationQuery.getAll());

    return "weeklyReportCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getCalculationView(@PathVariable("id") long id, final Model model) {
    final var calculation =  weeklyReportCalculationQuery.getById(id);
    model.addAttribute("calculation", calculation);
    model.addAttribute("weeklyReports", weeklyReportQuery.getAllByCalculationId(id));
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    return "detailView/weeklyReportCalculation";
  }
}
