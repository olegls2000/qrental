package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.ControllerUtils.*;


import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(TRANSACTION_ROOT_PATH)
@AllArgsConstructor
public class BalanceCalculationQueryController {

  private final GetBalanceCalculationQuery balanceCalculationQuery;

  @GetMapping("/calculations")
  public String getCalculationView(final Model model) {
    setQDateFormatter(model);
    model.addAttribute("calculations", balanceCalculationQuery.getAll());

    return "balanceCalculations";
  }

  @GetMapping(value = "/calculations/{id}")
  public String getBalanceCalculationView(@PathVariable("id") long id, final Model model) {
    model.addAttribute("balances", balanceCalculationQuery.getById(id).getBalances());

    return "detailView/balanceCalculation";
  }
}
