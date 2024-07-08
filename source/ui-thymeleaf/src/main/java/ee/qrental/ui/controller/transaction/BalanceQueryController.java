package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.BALANCE_ROOT_PATH;

import ee.qrental.bonus.api.in.query.GetObligationQuery;
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
import ee.qrental.transaction.api.in.response.balance.BalanceRawContextResponse;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import ee.qrental.ui.controller.transaction.assembler.DriverBalanceAssembler;
import java.math.BigDecimal;
import java.util.Collection;
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
  private final GetObligationQuery obligationQuery;
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
    addTransactionDataToModel(transactions, model);
    addDriverDataToModel(driverId, model);
    addCallSignDataToModel(driverId, model);
    addContractDataToModel(driverId, model);
    addCarDataToModel(driverId, model);
    addTotalFinancialDataToModel(driverId, model);
    addObligationDataToModel(driverId, model);

    return "detailView/balanceDriver";
  }

  @PostMapping(value = "/driver/{id}")
  public String getFilteredDriverPortalView(
      @ModelAttribute final QWeekAndDriverFilter transactionFilterRequest, final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("weeks", qWeekQuery.getAll());
    final var driverId = transactionFilterRequest.getDriverId();
    addDriverDataToModel(driverId, model);
    addCallSignDataToModel(driverId, model);
    addContractDataToModel(driverId, model);
    addCarDataToModel(driverId, model);
    addTotalFinancialDataToModel(driverId, model);
    addObligationDataToModel(driverId, model);
    model.addAttribute("transactionFilterRequest", transactionFilterRequest);
    List<TransactionResponse> transactions;

    final var requestedQWeekId = transactionFilterRequest.getQWeekId();

    if (requestedQWeekId != null) {
      final var rawBalanceContext =
          balanceQuery.getRawContextByDriverIdAndQWeekId(driverId, requestedQWeekId);
      transactions =
          rawBalanceContext.getTransactionsByKind().values().stream()
              .flatMap(Collection::stream)
              .filter(transactionResponse -> transactionResponse.getId() != null)
              .toList();
      addBalancePeriodDataToModel(model, rawBalanceContext);
      addObligationPeriodDataToModel(model, driverId, requestedQWeekId);
    } else {
      transactions = transactionQuery.getAllByDriverId(driverId);
    }
    addTransactionDataToModel(transactions, model);

    return "detailView/balanceDriver";
  }

  private void addBalancePeriodDataToModel(
      final Model model, BalanceRawContextResponse rawBalanceContext) {

    final var previousWeekBalance = rawBalanceContext.getPreviousWeekBalance();

    final var previousWeekFeeAbleAmount = previousWeekBalance.getFeeAbleAmount();
    final var previousWeekNonFeeAbleAmount = previousWeekBalance.getNonFeeAbleAmount();
    final var previousWeekPositiveAmount = previousWeekBalance.getPositiveAmount();
    final var previousWeekTotalAmount =
        previousWeekFeeAbleAmount.add(previousWeekNonFeeAbleAmount).add(previousWeekPositiveAmount);
    model.addAttribute("balancePeriodStartAmount", previousWeekTotalAmount);
    final var previousWeekFeeAmount = previousWeekBalance.getFeeAmount();
    model.addAttribute("feePeriodStartAmount", previousWeekFeeAmount);

    final var requestedWeekBalance = rawBalanceContext.getRequestedWeekBalance();

    final var requestedWeekFeeAbleAmount = requestedWeekBalance.getFeeAbleAmount();
    final var requestedWeekNonFeeAbleAmount = requestedWeekBalance.getNonFeeAbleAmount();
    final var requestedWeekPositiveAmount = requestedWeekBalance.getPositiveAmount();
    final var requestedWeekTotalAmount =
        requestedWeekFeeAbleAmount
            .add(requestedWeekNonFeeAbleAmount)
            .add(requestedWeekPositiveAmount);
    final var requestedWeekFeeAmount = requestedWeekBalance.getFeeAmount();

    model.addAttribute("balancePeriodEndAmount", requestedWeekTotalAmount);
    model.addAttribute("feePeriodEndAmount", requestedWeekFeeAmount);

    model.addAttribute(
        "balancePeriodTotalAmount", requestedWeekTotalAmount.subtract(previousWeekTotalAmount));

    final var feeTransactions = rawBalanceContext.getTransactionsByKind().get("F");
    var feePeriodTotalAmount = BigDecimal.ZERO;
    if (feeTransactions != null) {
      feePeriodTotalAmount =
          rawBalanceContext.getTransactionsByKind().get("F").stream()
              .map(TransactionResponse::getRealAmount)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    model.addAttribute("feePeriodTotalAmount", feePeriodTotalAmount);
  }

  private void addObligationPeriodDataToModel(
      final Model model, final Long driverId, final Long requestedQWeekId) {
    final var periodObligation =
        obligationQuery.getByQWeekIdAndDriverId(requestedQWeekId, driverId);
    if (periodObligation == null) {
      model.addAttribute("periodObligationAmount", "not calculated");
      model.addAttribute("periodObligationAmountPaid", "not calculated");
      model.addAttribute("periodObligationAmountLeftToPay", "not calculated");

      return;
    }

    final var periodObligationAmount = periodObligation.getAmount();
    final var periodObligationAmountAbs = periodObligationAmount.abs();

    model.addAttribute("periodObligationAmount", periodObligationAmountAbs);
    final var periodObligationAmountPaid = periodObligation.getPositiveAmount();
    model.addAttribute("periodObligationAmountPaid", periodObligationAmountPaid);

    final var periodObligationDiff = periodObligationAmountAbs.subtract(periodObligationAmountPaid);
    final var periodObligationAmountLeftToPay =
        periodObligationDiff.compareTo(BigDecimal.ZERO) < 0
            ? BigDecimal.ZERO
            : periodObligationDiff;

    final var periodObligationMatchCount = periodObligation.getMatchCount();
    model.addAttribute("periodObligationAmountLeftToPay", periodObligationAmountLeftToPay);
    model.addAttribute("periodObligationMatchCount", periodObligationMatchCount);
  }

  private void addTransactionDataToModel(
      final List<TransactionResponse> transactions, final Model model) {
    model.addAttribute("transactions", transactions);
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
    final var latestDerivedRawBalance = balanceQuery.getRawCurrentByDriver(driverId);
    final var feeAbleTotal = latestDerivedRawBalance.getFeeAbleAmount();
    final var nonFeeAbleTotal = latestDerivedRawBalance.getNonFeeAbleAmount();
    final var positiveTotal = latestDerivedRawBalance.getPositiveAmount();
    final var rawBalanceTotal = feeAbleTotal.add(nonFeeAbleTotal).add(positiveTotal);
    final var rawFeeTotal = latestDerivedRawBalance.getFeeAmount();
    model.addAttribute("rawBalanceTotal", rawBalanceTotal);
    model.addAttribute("rawFeeTotal", rawFeeTotal);
    model.addAttribute("rawRepairment", latestDerivedRawBalance.getRepairmentAmount());
    model.addAttribute("total", rawBalanceTotal.add(rawFeeTotal));
    model.addAttribute(
        "rawRepairmentWithQKasko", balanceQuery.getAmountRepairmentByDriverWithQKasko(driverId));

    final var latestCalculatedBalance = balanceQuery.getLatest();
    if (latestCalculatedBalance == null) {
      model.addAttribute("latestBalanceWeek", "Balance was not calculated");

      return;
    }
    model.addAttribute("latestBalanceYear", latestCalculatedBalance.getYear());
    model.addAttribute("latestBalanceWeek", latestCalculatedBalance.getWeekNumber());
  }

  private void addObligationDataToModel(final Long driverId, final Model model) {
    final var rawObligationAmount =
        obligationQuery.getRawObligationAmountForCurrentWeekByDriverId(driverId);
    model.addAttribute("obligationAmount", rawObligationAmount);

    final var preCurrentWeekObligation =
        obligationQuery.getObligationAmountForPreCurrentWeekByDriverId(driverId);
    if (preCurrentWeekObligation == null) {
      model.addAttribute("obligationMatchCount", "not calculated");
      return;
    }
    model.addAttribute("obligationMatchCount", preCurrentWeekObligation.getMatchCount().toString());
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
    model.addAttribute("activeContractDuration", activeContract.getDuration());
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
