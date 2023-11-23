package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.BALANCE_ROOT_PATH;
import static java.math.BigDecimal.ZERO;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.common.core.utils.QWeek;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.FeeOption;
import ee.qrental.transaction.api.in.query.filter.WeekAndDriverFilter;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import ee.qrental.ui.controller.transaction.assembler.DriverBalanceAssembler;
import ee.qrental.ui.controller.util.TransactionFilterRequestUtils;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(BALANCE_ROOT_PATH)
@AllArgsConstructor
public class BalanceQueryController {

  private final QDateFormatter qDateFormatter;
  private final GetBalanceCalculationQuery balanceCalculationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetContractQuery contractQuery;
  private final GetCarLinkQuery linkQuery;
  private final DriverBalanceAssembler driverBalanceAssembler;

  @GetMapping
  public String getBalanceView(final Model model) {
    model.addAttribute("balances", driverBalanceAssembler.getDriversBalanceModels());
    addLatestDataToModel(model);

    return "balances";
  }

  @GetMapping(value = "/driver/{id}")
  public String getDriverTransactionsView(@PathVariable("id") long id, final Model model) {
    // TransactionFilterRequestUtils.addCleanFilterRequestToModel(id, model);
    // TransactionFilterRequestUtils.addWeekOptionsToModel(model);
    final var filter = new YearAndWeekAndDriverAndFeeFilter();
    filter.setDriverId(id);
    model.addAttribute("transactionFilterRequest", filter);
    model.addAttribute("weeks", qWeekQuery.getAll());
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var transactions = transactionQuery.getAllByDriverId(id);
    addTransactionDataToModel(id, transactions, model);
    addDriverDataToModel(id, model);
    addCallSignDataToModel(id, model);
    addContractDataToModel(id, model);
    addCarDataToModel(id, model);
    addTotalFinancialDataToModel(id, model);

    return "detailView/balanceDriver";
  }

  @PostMapping(value = "/driver/{id}")
  public String getFilteredDriverTransactionsView(
      @PathVariable("id") long id,
      @ModelAttribute final WeekAndDriverFilter transactionFilterRequest,
      final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    setUpFeeParameter(transactionFilterRequest);
    TransactionFilterRequestUtils.addWeekOptionsToModel(model);
    final var transactions = transactionQuery.getAllByFilter(transactionFilterRequest);

    addTransactionDataToModel(id, transactions, model);
    addDriverDataToModel(id, model);
    addCallSignDataToModel(id, model);
    addContractDataToModel(id, model);
    addCarDataToModel(id, model);
    addTotalFinancialDataToModel(id, model);
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    if (transactionFilterRequest.getQWeekId() != null) {
      addBalancePeriodDataToModel(model, transactionFilterRequest, transactions);
      addFeePeriodDataToModel(model, transactionFilterRequest);
    }

    return "detailView/balanceDriver";
  }

  private void setUpFeeParameter(final WeekAndDriverFilter transactionFilterRequest) {
    if (transactionFilterRequest.getQWeekId() != null) {
      transactionFilterRequest.setFeeOption(FeeOption.WITHOUT_FEE);
    }
  }

  private void addBalancePeriodDataToModel(
      final Model model,
      final WeekAndDriverFilter transactionFilterRequest,
      final List<TransactionResponse> periodTransactions) {
    final var driverId = transactionFilterRequest.getDriverId();
    final var year = transactionFilterRequest.getYear();
    final var weekNumber = transactionFilterRequest.getWeek().getNumber();
    final var previousWeek = weekNumber - 1;

    final var periodStartBalanceAmount =
        balanceQuery.getRawBalanceTotalByDriverIdAndYearAndWeekNumber(driverId, year, previousWeek);
    model.addAttribute("balancePeriodStartAmount", periodStartBalanceAmount);

    final var periodTotalAmount =
        periodTransactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    model.addAttribute("balancePeriodTotalAmount", periodTotalAmount);

    final var periodEndBalanceAmount = periodStartBalanceAmount.add(periodTotalAmount);
    model.addAttribute("balancePeriodEndAmount", periodEndBalanceAmount);
  }

  private void addFeePeriodDataToModel(
      final Model model, final YearAndWeekAndDriverAndFeeFilter transactionFilterRequest) {
    final var driverId = transactionFilterRequest.getDriverId();
    final var year = transactionFilterRequest.getYear();
    final var weekNumber = transactionFilterRequest.getWeek().getNumber();
    final var previousWeek = weekNumber - 1;
    final var latestBalance =
        balanceQuery.getLatestBalanceByDriverIdAndYearAndWeekNumber(driverId, year, previousWeek);
    var feeFromLatestBalance = ZERO;
    if (latestBalance != null) {
      feeFromLatestBalance = latestBalance.getFee();
    }
    model.addAttribute("feePeriodStartAmount", feeFromLatestBalance);

    transactionFilterRequest.setFeeOption(FeeOption.ONLY_FEE);
    final var periodFeeTransactions = transactionQuery.getAllByFilter(transactionFilterRequest);
    final var periodFeeTotalAmount =
        periodFeeTransactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    model.addAttribute("feePeriodTotalAmount", periodFeeTotalAmount);

    final var periodFeeEndBalanceAmount = feeFromLatestBalance.add(periodFeeTotalAmount);
    model.addAttribute("feePeriodEndAmount", periodFeeEndBalanceAmount);
  }

  private void addTransactionDataToModel(
      final Long driverId, final List<TransactionResponse> transactions, final Model model) {
    model.addAttribute("transactions", transactions);
    model.addAttribute("rawTotal", balanceQuery.getRawBalanceTotalByDriver(driverId));
  }

  private void addDriverDataToModel(final Long driverId, final Model model) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driverId", driver.getId());
    model.addAttribute("driverFirstName", driver.getFirstName());
    model.addAttribute("driverLastName", driver.getLastName());
    model.addAttribute("driverPhone", driver.getPhone());
    model.addAttribute("driverDeposit", getDeposit(driver));
    model.addAttribute("qFirmId", driver.getQFirmId());
  }

  private void addTotalFinancialDataToModel(final Long driverId, final Model model) {
    model.addAttribute("rawBalanceTotal", balanceQuery.getRawBalanceTotalByDriver(driverId));
    model.addAttribute("rawFeeTotal", balanceQuery.getRawFeeTotalByDriver(driverId));
    final var latestBalance = balanceQuery.getLatestBalanceByDriver(driverId);
    if (latestBalance == null) {
      model.addAttribute("latestBalanceWeek", "Balance was not calculated");

      return;
    }
    model.addAttribute("latestBalanceWeek", latestBalance.getWeekNumber());
  }

  private void addCallSignDataToModel(final Long driverId, final Model model) {
    final var callSignLink = callSignLinkQuery.getActiveCallSignLinkByDriverId(driverId);
    if (callSignLink == null) {
      model.addAttribute("callSign", "not assigned");
      model.addAttribute("callSignLinkId", null);

      return;
    }
    model.addAttribute("callSign", callSignLink.getCallSign());
    model.addAttribute("callSignLinkId", callSignLink.getId());
  }

  private void addContractDataToModel(final Long driverId, final Model model) {
    final var activeContract = contractQuery.getActiveContractByDriverId(driverId);
    if (activeContract == null) {
      model.addAttribute("activeContract", "absent");
      model.addAttribute("activeContractId", null);

      return;
    }
    model.addAttribute("activeContract", activeContract.getNumber());
    model.addAttribute("activeContractId", activeContract.getId());
  }

  private void addCarDataToModel(final Long driverId, final Model model) {
    final var link = linkQuery.getActiveLinkByDriverId(driverId);
    if (link == null) {
      model.addAttribute("carRegistrationNumber", "no car in renting");
      model.addAttribute("carLinkId", null);

      return;
    }
    model.addAttribute("carRegistrationNumber", link.getRegistrationNumber());
    model.addAttribute("carLinkId", link.getId());
  }

  private BigDecimal getDeposit(final DriverResponse driver) {
    final var deposit = driver.getDeposit();
    if (deposit == null) {
      return ZERO;
    }
    return deposit;
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
