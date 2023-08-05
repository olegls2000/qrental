package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.ControllerUtils.TRANSACTION_ROOT_PATH;
import static ee.qrental.ui.controller.ControllerUtils.setQDateFormatter;
import static ee.qrental.ui.controller.transaction.TransactionFilterRequestUtils.addCleanFilterRequestToModel;
import static ee.qrental.ui.controller.transaction.TransactionFilterRequestUtils.addWeekOptionsToModel;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
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
  private final GetBalanceQuery balanceQuery;

  @GetMapping
  public String getPageWithAllTransactions(final Model model) {
    setQDateFormatter(model);
    addCleanFilterRequestToModel(model);
    addWeekOptionsToModel(model);
    addTransactionDataToModel(transactionQuery.getAll(), model);
    addLatestDataToModel(model);

    return "transactions";
  }

  @PostMapping
  public String getPageWithFilteredTransactions(
          @ModelAttribute final YearAndWeekAndDriverAndFeeFilter transactionFilterRequest,
          final Model model) {
    addWeekOptionsToModel(model);
    addTransactionDataToModel(transactionQuery.getAllByFilter(transactionFilterRequest), model);
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    addLatestDataToModel(model);

    return "transactions";
  }

  private void addTransactionDataToModel(
      final List<TransactionResponse> transactions, final Model model) {
    model.addAttribute("transactions", transactions);
  }

  private void addLatestDataToModel( final Model model) {
    final var latestCalculatedDate = balanceQuery.getLatestCalculatedDate();
    if(latestCalculatedDate == null){
      model.addAttribute("latestBalanceWeek", "Balance was not calculated");

      return;
    }
    final var latestCalculatedWeek = QTimeUtils.getWeekNumber(latestCalculatedDate);
    final var value = String.format("%d (%s)", latestCalculatedWeek, latestCalculatedDate);
    model.addAttribute("latestBalanceWeek", value);
  }
}
