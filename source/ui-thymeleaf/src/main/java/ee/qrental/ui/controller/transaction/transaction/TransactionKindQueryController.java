package ee.qrental.ui.controller.transaction.transaction;

import static ee.qrental.ui.controller.util.ControllerUtils.TRANSACTION_KIND_ROOT_PATH;

import ee.qrental.transaction.api.in.query.kind.GetTransactionKindQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(TRANSACTION_KIND_ROOT_PATH)
@AllArgsConstructor
public class TransactionKindQueryController {

  private final GetTransactionKindQuery transactionKindQuery;

  @GetMapping()
  public String getTransactionKindsView(final Model model) {
    model.addAttribute("transactionKinds", transactionKindQuery.getAll());

    return "transactionKinds";
  }
}
