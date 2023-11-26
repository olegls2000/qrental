package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.BALANCE_ROOT_PATH;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.QWeekAndDriverFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import ee.qrental.ui.controller.transaction.assembler.DriverBalanceAssembler;
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
  public String getDriverPortalView(@PathVariable("id") long driverId, final Model model) {
    model.addAttribute("weeks", qWeekQuery.getAll());
    final var transactionFilterRequest = new QWeekAndDriverFilter();
    transactionFilterRequest.setDriverId(driverId);
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    final var transactions = transactionQuery.getAllByDriverId(driverId);
    addTransactionDataToModel(driverId, transactions, model);
    addDriverDataToModel(driverId, model);
    addCallSignDataToModel(driverId, model);
    addContractDataToModel(driverId, model);
    addCarDataToModel(driverId, model);
    addTotalFinancialDataToModel(driverId, model);

    return "detailView/balanceDriver";
  }

  @PostMapping(value = "/driver/{id}")
  public String getFilteredDriverPortalView(
      @ModelAttribute final QWeekAndDriverFilter transactionFilterRequest, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("weeks", qWeekQuery.getAll());
    final var driverId = transactionFilterRequest.getDriverId();

    final var requestedQWekId = transactionFilterRequest.getQWeekId();

    final var transactions =
        requestedQWekId == null
            ? transactionQuery.getAllByDriverId(driverId)
            : transactionQuery.getAllByDriverIdAndQWeekId(driverId, requestedQWekId);

    addTransactionDataToModel(driverId, transactions, model);
    addDriverDataToModel(driverId, model);
    addCallSignDataToModel(driverId, model);
    addContractDataToModel(driverId, model);
    addCarDataToModel(driverId, model);
    addTotalFinancialDataToModel(driverId, model);
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    if (requestedQWekId != null) {
      final var previousQWeek = qWeekQuery.getOneBeforeById(requestedQWekId);
      final var previousQWeekId = previousQWeek.getId();
      if (previousQWeek != null) {
        addBalancePeriodDataToModel(model, driverId, requestedQWekId, previousQWeekId);
        addFeePeriodDataToModel(model, driverId, requestedQWekId, previousQWeekId);
      }
    }

    return "detailView/balanceDriver";
  }

  private void addBalancePeriodDataToModel(
      final Model model,
      final Long driverId,
      final Long requestedQWeekId,
      final Long previousQWeekId) {
    final var periodStartBalanceAmount =
        balanceQuery.getRawBalanceTotalByDriverIdAndQWeekId(driverId, previousQWeekId);
    model.addAttribute("balancePeriodStartAmount", periodStartBalanceAmount);
    final var periodTotalAmount =
        balanceQuery.getPeriodAmountByDriverAndQWeek(driverId, requestedQWeekId);
    model.addAttribute("balancePeriodTotalAmount", periodTotalAmount);
    final var periodEndBalanceAmount = periodStartBalanceAmount.add(periodTotalAmount);
    model.addAttribute("balancePeriodEndAmount", periodEndBalanceAmount);
  }

  private void addFeePeriodDataToModel(
      final Model model,
      final Long driverId,
      final Long requestedQWeekId,
      final Long previousQWeekId) {
    final var feeFromLatestBalance =
        balanceQuery.getFeeByDriverIdAndQWeekId(driverId, previousQWeekId);
    model.addAttribute("feePeriodStartAmount", feeFromLatestBalance);
    final var periodFeeTotalAmount =
        balanceQuery.getPeriodFeeByDriverAndQWeek(driverId, requestedQWeekId);
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
    model.addAttribute("driverDeposit", driver.getDeposit());
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
