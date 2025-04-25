package ee.qrent.billing.ui.controller.bonus;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.*;

import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(OBLIGATIONS_ROOT_PATH)
@AllArgsConstructor
public class ObligationCalculationQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetObligationCalculationQuery obligationCalculationQuery;
  private final GetObligationQuery obligationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;

  @GetMapping("/calculations")
  public String getObligationCalculationsView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    addLatestDataToModel(model);
    model.addAttribute("calculations", obligationCalculationQuery.getAll());

    return "obligationCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getObligationCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var calculation = obligationCalculationQuery.getById(id);
    final var obligations = obligationQuery.getAllByCalculationId(id);

    model.addAttribute("calculation", calculation);
    model.addAttribute("obligations", obligations);

    return "detailView/obligationCalculation";
  }

  private void addLatestDataToModel(final Model model) {
    final var latestCalculatedWeekId = balanceCalculationQuery.getLastCalculatedQWeekId();
    if (latestCalculatedWeekId == null) {
      model.addAttribute("latestBalanceWeek", "Balance was not calculated");

      return;
    }

    final var latestCalculatedWeek = qWeekQuery.getById(latestCalculatedWeekId);
    final var latestBalanceWeekLabel =
        String.format("%d (%s)", latestCalculatedWeek.getNumber(), latestCalculatedWeek.getEnd());
    model.addAttribute("latestBalanceWeek", latestBalanceWeekLabel);
  }
}
