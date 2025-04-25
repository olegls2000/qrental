package ee.qrent.billing.ui.controller.transaction.transaction;

import static ee.qrent.billing.ui.controller.ControllerUtils.TRANSACTION_TYPE_ROOT_PATH;

import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(TRANSACTION_TYPE_ROOT_PATH)
@AllArgsConstructor
public class TransactionTypeQueryController {

  private final GetTransactionTypeQuery transactionTypeQuery;

  @GetMapping()
  public String getTransactionTypeView(final Model model) {
    model.addAttribute("transactionTypes", transactionTypeQuery.getAll());
    return "transactionTypes";
  }
}
