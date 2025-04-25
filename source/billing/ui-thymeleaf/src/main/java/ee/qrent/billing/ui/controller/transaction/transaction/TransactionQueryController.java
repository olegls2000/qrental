package ee.qrent.billing.ui.controller.transaction.transaction;

import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrent.billing.ui.controller.ControllerUtils.TRANSACTION_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.transaction.api.in.query.filter.WeekFilter;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.ui.formatter.QDateFormatter;
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

  private final QDateFormatter qDateFormatter;
  private final GetTransactionQuery transactionQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;
  private final GetQWeekQuery qWeekQuery;

  @GetMapping
  public String getPageWithAllTransactions(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("transactionFilterRequest", new WeekFilter());
    model.addAttribute("weeks", qWeekQuery.getAll());
    addTransactionDataToModel(transactionQuery.getAll(), model);
    addLatestDataToModel(model);

    return "transactions";
  }

  @PostMapping
  public String getPageWithFilteredTransactions(
      @ModelAttribute final WeekFilter transactionFilterRequest, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("weeks", qWeekQuery.getAll());
    //todo move to the service
    if (transactionFilterRequest.getQWeekId() == null) {
      addTransactionDataToModel(transactionQuery.getAll(), model);
    } else {
      addTransactionDataToModel(
          transactionQuery.getAllByQWeekId(transactionFilterRequest.getQWeekId()), model);
    }

    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    addLatestDataToModel(model);

    return "transactions";
  }

  private void addTransactionDataToModel(
      final List<TransactionResponse> transactions, final Model model) {
    model.addAttribute("transactions", transactions);
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
