package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.ControllerUtils.BALANCE_ROOT_PATH;
import static java.math.BigDecimal.ZERO;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.link.api.in.query.GetLinkQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.ui.controller.transaction.assembler.DriverBalanceAssembler;
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

  private final GetBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetDriverQuery driverQuery;
  private final GetLinkQuery linkQuery;
  private final DriverBalanceAssembler driverBalanceAssembler;

  @GetMapping
  public String getBalanceView(final Model model) {
    model.addAttribute("balances", driverBalanceAssembler.getDriversBalanceModels());

    return "balances";
  }

  @GetMapping(value = "/driver/{id}")
  public String getDriverTransactionsView(@PathVariable("id") long id, final Model model) {
    TransactionFilterRequestUtils.addCleanFilterRequestToModel(id, model);
    TransactionFilterRequestUtils.addWeekOptionsToModel(model);
    final var transactions = transactionQuery.getAllByDriverId(id);
    addTransactionDataToModel(id, transactions, model);
    addDriverDataToModel(id, model);

    return "detailView/balanceDriver";
  }

  @PostMapping(value = "/driver/{id}")
  public String getFilteredDriverTransactionsView(
      @PathVariable("id") long id,
      @ModelAttribute final YearAndWeekAndDriverFilter transactionFilterRequest,
      final Model model) {
    TransactionFilterRequestUtils.addWeekOptionsToModel(model);
    final var transactions = transactionQuery.getAllByFilter(transactionFilterRequest);
    addTransactionDataToModel(id, transactions, model);
    addDriverDataToModel(id, model);
    addBalancePeriodDataToModel(model, transactionFilterRequest, transactions);
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);

    return "detailView/balanceDriver";
  }

  private void addBalancePeriodDataToModel(
      final Model model,
      final YearAndWeekAndDriverFilter transactionFilterRequest,
      final List<TransactionResponse> periodTransactions) {

    final var driverId = transactionFilterRequest.getDriverId();
    final var year = transactionFilterRequest.getYear();
    final var weekNumber = transactionFilterRequest.getWeek().getNumber();
    final var previousWeek = weekNumber - 1;

    final var periodStartBalance =
        balanceQuery.getByDriverIdAndYearAndWeekNumber(driverId, year, previousWeek);
    final var periodStartBalanceAmount =
        periodStartBalance != null ? periodStartBalance.getAmount() : ZERO;
    model.addAttribute("periodStartAmount", periodStartBalanceAmount);

    final var periodEndBalance =
        balanceQuery.getByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber);
    final var periodEndBalanceAmount =
        periodEndBalance != null ? periodEndBalance.getAmount() : ZERO;
    model.addAttribute("periodEndAmount", periodEndBalanceAmount);

    final var periodTotalAmount = periodTransactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    model.addAttribute("periodTotalAmount", periodTotalAmount);
  }

  private void addTransactionDataToModel(
      final Long driverId, final List<TransactionResponse> transactions, final Model model) {
    model.addAttribute("transactions", transactions);
    model.addAttribute("rawTotal", balanceQuery.getRawBalanceTotalByDriver(driverId));
  }

  private void addDriverDataToModel(final Long driverId, final Model model) {
    final var driver = driverQuery.getById(driverId);
    final var carRegistrationNumber = getRegistrationNumber(driverId);
    model.addAttribute("driverId", driver.getId());
    model.addAttribute("driverFirstName", driver.getFirstName());
    model.addAttribute("driverLastName", driver.getLastName());
    model.addAttribute("driverCallSign", driver.getCallSign());
    model.addAttribute("driverPhone", driver.getPhone());
    model.addAttribute("driverDeposit", getDeposit(driver));
    model.addAttribute("carRegistrationNumber", carRegistrationNumber);
    model.addAttribute("rawBalanceTotal", balanceQuery.getRawBalanceTotalByDriver(driverId));
    model.addAttribute("feeTotal", balanceQuery.getFeeByDriver(driverId));
  }

  private String getRegistrationNumber(final Long driverId) {
    final var link = linkQuery.getActiveLinkByDriverId(driverId);
    if (link == null) {
      return "no car in renting";
    }
    return link.getRegistrationNumber();
  }

  private BigDecimal getDeposit(final DriverResponse driver) {
    final var deposit = driver.getDeposit();
    if (deposit == null) {
      return ZERO;
    }
    return deposit;
  }
}
