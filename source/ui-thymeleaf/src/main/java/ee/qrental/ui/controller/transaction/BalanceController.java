package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.ControllerUtils.BALANCE_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetDriverBalanceQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(BALANCE_ROOT_PATH)
@AllArgsConstructor
public class BalanceController {

  private final GetDriverBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetDriverQuery driverQuery;

  @GetMapping
  public String getBalanceView(final Model model) {
    model.addAttribute("balances", balanceQuery.getAll());
    return "balances";
  }

  @GetMapping(value = "/driver/{id}")
  public String getDriverTransactionsView(@PathVariable("id") long id, final Model model) {
    TransactionFilterRequestUtils.addCleanFilterRequestToModel(id, model);
    TransactionFilterRequestUtils.addFilterOptionsToModel(model);
    addTransactionDataToModel(transactionQuery.getAllByDriverId(id), model);
    addDriverDataToModel(id, model);

    return "detailView/balanceDriver";
  }

  @PostMapping(value = "/driver/{id}")
  public String getFilteredDriverTransactionsView(
      @PathVariable("id") long id,
      @ModelAttribute final YearAndWeekAndDriverFilter transactionFilterRequest,
      final Model model) {
    TransactionFilterRequestUtils.addFilterOptionsToModel(model);
    addTransactionDataToModel(transactionQuery.getAllByFilter(transactionFilterRequest), model);
    addDriverDataToModel(id, model);

    return "detailView/balanceDriver";
  }

  private void addTransactionDataToModel(
      final List<TransactionResponse> transactions, final Model model) {
    model.addAttribute("transactions", transactions);
    model.addAttribute("viewTotal", getSum(transactions));
  }

  private void addDriverDataToModel(final Long driverId, final Model model) {
    model.addAttribute("driverId", driverId);
    model.addAttribute("total", balanceQuery.getTotalByDriverId(driverId));
    model.addAttribute("driverInfo", driverQuery.getObjectInfo(driverId));
  }

  private BigDecimal getSum(final List<TransactionResponse> transactionResponses) {
    return transactionResponses.stream()
        .map(TransactionResponse::getRealAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
