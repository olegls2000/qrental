package ee.qrent.billing.ui.controller.transaction.calculation.balance;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.*;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(BALANCE_ROOT_PATH)
@AllArgsConstructor
public class BalanceCalculationQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;

  @GetMapping("/calculations")
  public String getCalculationView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    addLatestDataToModel(model);
    model.addAttribute("calculations", balanceCalculationQuery.getAll());

    return "balanceCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getBalanceCalculationView(@PathVariable("id") long id, final Model model) {

   final var calculation =  balanceCalculationQuery.getById(id);
    model.addAttribute("balances", calculation.getBalances());
    model.addAttribute("startDate", calculation.getStartDate());
    model.addAttribute("endDate", calculation.getEndDate());
    model.addAttribute("actionDate", calculation.getActionDate());

    return "detailView/balanceCalculation";
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
