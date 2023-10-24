package ee.qrental.ui.controller.transaction.calculation.balance;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.*;

import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(TRANSACTION_ROOT_PATH)
@AllArgsConstructor
public class BalanceCalculationQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetBalanceCalculationQuery balanceCalculationQuery;

  @GetMapping("/balance-calculations")
  public String getCalculationView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    model.addAttribute("calculations", balanceCalculationQuery.getAll());

    return "balanceCalculations";
  }

  @GetMapping(value = "/balance-calculations/{id}")
  public String getBalanceCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute("balances", balanceCalculationQuery.getById(id).getBalances());

    return "detailView/balanceCalculation";
  }
}
