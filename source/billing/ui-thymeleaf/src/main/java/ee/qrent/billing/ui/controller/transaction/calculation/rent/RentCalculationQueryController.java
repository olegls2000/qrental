package ee.qrent.billing.ui.controller.transaction.calculation.rent;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.*;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(RENTS_ROOT_PATH)
@AllArgsConstructor
public class RentCalculationQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetRentCalculationQuery rentCalculationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;

  private final GetTransactionQuery transactionQuery;

  @GetMapping("/calculations")
  public String getRentCalculationView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    addLatestDataToModel(model);
    model.addAttribute("calculations", rentCalculationQuery.getAll());

    return "rentCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getRentCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var rentCalculation = rentCalculationQuery.getById(id);
    final var rentTransactions = transactionQuery.getAllByRentCalculationId(id);

    model.addAttribute("rentCalculation", rentCalculation);
    model.addAttribute("rentTransactions", rentTransactions);

    return "detailView/rentCalculation";
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
