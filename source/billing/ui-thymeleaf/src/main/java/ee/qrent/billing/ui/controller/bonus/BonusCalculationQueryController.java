package ee.qrent.billing.ui.controller.bonus;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.*;

import ee.qrent.billing.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(BONUS_ROOT_PATH)
@AllArgsConstructor
public class BonusCalculationQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetBonusCalculationQuery bonusCalculationQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;

  @GetMapping("/calculations")
  public String getCalculationsView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    addLatestDataToModel(model);
    model.addAttribute("calculations", bonusCalculationQuery.getAll());

    return "bonusCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var calculation = bonusCalculationQuery.getById(id);
    final var transactions = transactionQuery.getAllByBonusCalculationId(id);

    model.addAttribute("calculation", calculation);
    model.addAttribute("transactions", transactions);

    return "detailView/bonusCalculation";
  }

  private void addLatestDataToModel(final Model model) {
    final var latestCalculatedWeekId = balanceCalculationQuery.getLastCalculatedQWeekId();
    if (latestCalculatedWeekId == null) {
      model.addAttribute("latestBalanceWeek", "Bonus was not calculated");

      return;
    }

    final var latestCalculatedWeek = qWeekQuery.getById(latestCalculatedWeekId);
    final var latestBalanceWeekLabel =
        String.format("%d (%s)", latestCalculatedWeek.getNumber(), latestCalculatedWeek.getEnd());
    model.addAttribute("latestBalanceWeek", latestBalanceWeekLabel);
  }
}
