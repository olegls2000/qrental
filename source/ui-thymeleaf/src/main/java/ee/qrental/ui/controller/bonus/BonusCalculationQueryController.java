package ee.qrental.ui.controller.bonus;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.*;

import ee.qrental.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrental.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
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
  public String getObligationCalculationsView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    addLatestDataToModel(model);
    model.addAttribute("calculations", bonusCalculationQuery.getAll());

    return "bonusCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getObligationCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var calculation = bonusCalculationQuery.getById(id);
    final var transactions = transactionQuery.getAllByRentCalculationId(id);

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
