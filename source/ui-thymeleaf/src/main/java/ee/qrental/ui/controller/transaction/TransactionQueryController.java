package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.ControllerUtils.TRANSACTION_ROOT_PATH;
import static ee.qrental.ui.controller.transaction.TransactionFilterRequestUtils.addCleanFilterRequestToModel;
import static ee.qrental.ui.controller.transaction.TransactionFilterRequestUtils.addFilterOptionsToModel;

import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(TRANSACTION_ROOT_PATH)
@AllArgsConstructor
public class TransactionQueryController {

  private final GetTransactionQuery transactionQuery;

  @GetMapping
  public String getPageWithAllTransactions(final Model model) {
    addCleanFilterRequestToModel(model);
    addFilterOptionsToModel(model);
    addTransactionDataToModel(transactionQuery.getAll(), model);

    return "transactions";
  }

  @PostMapping
  public String getPageWithFilteredTransactions(
      @ModelAttribute final YearAndWeekAndDriverFilter filterRequest, final Model model) {
    addFilterOptionsToModel(model);
    addTransactionDataToModel(transactionQuery.getAllByFilter(filterRequest), model);

    return "transactions";
  }

  private void addTransactionDataToModel(
      final List<TransactionResponse> transactions, final Model model) {
    model.addAttribute("transactions", transactions);
  }
}
