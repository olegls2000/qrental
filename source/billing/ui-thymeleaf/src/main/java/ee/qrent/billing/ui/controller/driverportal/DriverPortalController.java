package ee.qrent.billing.ui.controller.driverportal;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_FEE_DEBT_CODE;
import static ee.qrent.billing.ui.controller.ControllerUtils.DRIVER_PORTAL_PATH;
import static ee.qrent.billing.ui.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static java.math.BigDecimal.ZERO;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetAbsenceQuery;
import ee.qrent.billing.contract.api.in.query.GetAuthorizationQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.filter.DriverAndQWeekFilter;
import ee.qrent.billing.transaction.api.in.query.filter.DriverAndQWeekIntervalFilter;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(DRIVER_PORTAL_PATH)
@AllArgsConstructor
public class DriverPortalController {

  private static final String MODEL_ATTRIBUTE_TRANSACTION_FILTER_REQUEST =
      "transactionFilterRequest";

  private final QDateFormatter qDateFormatter;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetInsuranceCaseBalanceQuery insuranceCaseBalanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetContractQuery contractQuery;
  private final GetCarLinkQuery linkQuery;
  private final GetObligationQuery obligationQuery;
  private final GetAuthorizationQuery authorizationQuery;
  private final GetAbsenceQuery absenceQuery;
  private final GetDepositQuery depositQuery;

  @GetMapping(value = {"/week/{id}", "/{id}"})
  public String getDriverPortalWeekView(@PathVariable("id") long driverId, final Model model) {
    populateModelByStaticData(model, driverId);
    final var transactionFilterRequest = new DriverAndQWeekFilter();
    transactionFilterRequest.setDriverId(driverId);
    model.addAttribute(MODEL_ATTRIBUTE_TRANSACTION_FILTER_REQUEST, transactionFilterRequest);
    addTransactionDataToModel(transactionQuery.getAllByDriverId(driverId), model);

    return "detailView/driverPortalWeekSearch";
  }

  @GetMapping(value = "/interval/{id}")
  public String getDriverPortalIntervalView(@PathVariable("id") long driverId, final Model model) {
    populateModelByStaticData(model, driverId);
    final var transactionFilterRequest = new DriverAndQWeekIntervalFilter();
    transactionFilterRequest.setDriverId(driverId);
    model.addAttribute(MODEL_ATTRIBUTE_TRANSACTION_FILTER_REQUEST, transactionFilterRequest);
    addTransactionDataToModel(transactionQuery.getAllByDriverId(driverId), model);

    return "detailView/driverPortalIntervalSearch";
  }

  @PostMapping(value = "/week")
  public String getFilteredDriverPortalView(
      @ModelAttribute final DriverAndQWeekFilter transactionFilterRequest, final Model model) {
    final var driverId = transactionFilterRequest.getDriverId();
    populateModelByStaticData(model, driverId);
    model.addAttribute(MODEL_ATTRIBUTE_TRANSACTION_FILTER_REQUEST, transactionFilterRequest);
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
      addBalancePeriodDataToModel(
          model,
          rawBalanceContext.getPreviousWeekBalance(),
          rawBalanceContext.getRequestedWeekBalance(),
          getFeeAmountFromPeriod(transactions));
      addObligationPeriodDataToModel(model, driverId, requestedQWeekId);
      addInsuranceRequestedWeekBalance(model, driverId, requestedQWeekId);
    } else {
      transactions = transactionQuery.getAllByDriverId(driverId);
    }
    addTransactionDataToModel(transactions, model);

    return "detailView/driverPortalWeekSearch";
  }

  @PostMapping(value = "/interval")
  public String getFilteredByIntervalDriverPortalView(
      @ModelAttribute final DriverAndQWeekIntervalFilter transactionFilterRequest,
      final Model model) {
    final var driverId = transactionFilterRequest.getDriverId();
    populateModelByStaticData(model, driverId);
    model.addAttribute(MODEL_ATTRIBUTE_TRANSACTION_FILTER_REQUEST, transactionFilterRequest);
    final var intervalStartDate =
        qWeekQuery.getStartDateOrFirstDate(transactionFilterRequest.getStartQWeekId());
    final var intervalEndDate =
        qWeekQuery.getEndDateOrCurrent(transactionFilterRequest.getEndQWeekId());
    final var startBalance = balanceQuery.getRawByDriverAndDate(driverId, intervalStartDate);
    final var endBalance = balanceQuery.getRawByDriverAndDate(driverId, intervalEndDate);
    final var transactions = transactionQuery.getAllByFilter(transactionFilterRequest);
    addTransactionDataToModel(transactions, model);

    addBalancePeriodDataToModel(
        model, startBalance, endBalance, getFeeAmountFromPeriod(transactions));

    return "detailView/driverPortalIntervalSearch";
  }

  private BigDecimal getFeeAmountFromPeriod(final List<TransactionResponse> transactions) {
    final var optional =
        transactions.stream()
            .filter(transaction -> transaction.getTypeCode().equals(TRANSACTION_TYPE_FEE_DEBT_CODE))
            .findAny();
    var feeAmount = ZERO;
    if (optional.isPresent()) {
      feeAmount = optional.get().getRealAmount();
    }
    return feeAmount;
  }

  void populateModelByStaticData(final Model model, final Long driverId) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);
    model.addAttribute("weeks", qWeekQuery.getAll());
    addDriverDataToModel(driverId, model);
    addCallSignDataToModel(driverId, model);
    addContractDataToModel(driverId, model);
    addCarDataToModel(driverId, model);
    addTotalFinancialDataToModel(driverId, model);
    addInsuranceDataToModel(driverId, model);
    addObligationDataToModel(driverId, model);
    addAuthorisationDataToModel(driverId, model);
    addAbsencesDataToModel(driverId, model);
  }

  private void addBalancePeriodDataToModel(
      final Model model,
      final BalanceResponse startBalance,
      final BalanceResponse requestedWeekBalance,
      final BigDecimal periodFeeTransaction) {
    final var startWeekFeeAbleAmount = startBalance.getFeeAbleAmount();
    final var startWeekNonFeeAbleAmount = startBalance.getNonFeeAbleAmount();
    final var startWeekPositiveAmount = startBalance.getPositiveAmount();
    final var startWeekTotalAmount =
        startWeekFeeAbleAmount.add(startWeekNonFeeAbleAmount).add(startWeekPositiveAmount);
    model.addAttribute("balancePeriodStartAmount", startWeekTotalAmount);
    final var startWeekFeeAmount = startBalance.getFeeAmount();
    model.addAttribute("feePeriodStartAmount", startWeekFeeAmount);
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
        "balancePeriodTotalAmount", requestedWeekTotalAmount.subtract(startWeekTotalAmount));
    model.addAttribute(
        "feePeriodTotalAmount", startBalance.getFeeAmount().add(periodFeeTransaction));
  }

  private void addObligationPeriodDataToModel(
      final Model model, final Long driverId, final Long requestedQWeekId) {
    final var periodObligation =
        obligationQuery.getByDriverIdAndQWeekId(driverId, requestedQWeekId);
    if (periodObligation == null) {
      model.addAttribute("periodObligationAmount", "not calculated");
      model.addAttribute("periodObligationAmountPaid", "not calculated");
      model.addAttribute("periodObligationAmountLeftToPay", "not calculated");
      model.addAttribute("periodObligationMatchCount", 0);

      return;
    }
    final var periodObligationAmount = periodObligation.getAmount();
    final var periodObligationAmountAbs = periodObligationAmount.abs();
    model.addAttribute("periodObligationAmount", periodObligationAmountAbs);
    final var periodObligationAmountPaid = periodObligation.getPositiveAmount();
    model.addAttribute("periodObligationAmountPaid", periodObligationAmountPaid);
    final var periodObligationDiff = periodObligationAmountAbs.subtract(periodObligationAmountPaid);
    final var periodObligationAmountLeftToPay =
        periodObligationDiff.compareTo(ZERO) < 0 ? ZERO : periodObligationDiff;

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
    model.addAttribute("qFirmId", driver.getQFirmId());
    model.addAttribute("hasQKasko", driver.getHasQKasko());

    final var paidAmountOfDeposit = depositQuery.getPaidAmountByDriverId(driverId);
    model.addAttribute("paidAmountOfDeposit", paidAmountOfDeposit);
  }

  private void addInsuranceRequestedWeekBalance(
      final Model model, final Long driverId, final Long requestedQWeekId) {
    model.addAttribute(
        "insuranceBalanceTotalByDriverIdAndQWeekId",
        insuranceCaseBalanceQuery.getInsuranceBalanceTotalByDriverIdAndQWeekId(
            driverId, requestedQWeekId));
  }

  private void addInsuranceDataToModel(final Long driverId, final Model model) {
    model.addAttribute(
        "insuranceBalanceTotal",
        insuranceCaseBalanceQuery.getInsuranceBalanceTotalByDriverForCurrentWeek(driverId));
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
    model.addAttribute("total", rawBalanceTotal.add(rawFeeTotal));

    final var latestCalculatedBalance = balanceQuery.getLatest();
    if (latestCalculatedBalance == null) {
      model.addAttribute("latestBalanceWeek", "Balance was not calculated");

      model.addAttribute("latestBalanceYear", "not available");
      model.addAttribute("latestBalanceWeek", "not available");
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
    final var activeContract = contractQuery.getCurrentActiveByDriverId(driverId);
    if (activeContract == null) {
      model.addAttribute("activeContract", "no active contract");
      model.addAttribute("activeContractId", null);

      return;
    }
    model.addAttribute("activeContract", activeContract.getNumber());
    model.addAttribute("activeContractId", activeContract.getId());
    model.addAttribute("activeContractDuration", activeContract.getDuration());
    model.addAttribute("activeContractStartDate", activeContract.getDateStart());
    model.addAttribute("activeContractEndDate", activeContract.getDateEnd());
    model.addAttribute("activeContractWeeksToEnd", activeContract.getWeeksToEnd());
  }

  private void addCarDataToModel(final Long driverId, final Model model) {
    final var link = linkQuery.getActiveLinkByDriverId(driverId);
    String carRegistrationNumber = null;
    Long carLinkId = null;

    if (link != null) {
      carRegistrationNumber = link.getRegistrationNumber();
      carLinkId = link.getId();
    }
    model.addAttribute("carRegistrationNumber", carRegistrationNumber);
    model.addAttribute("carLinkId", carLinkId);
  }

  private void addAuthorisationDataToModel(final Long driverId, final Model model) {
    Long authorizationId = null;
    LocalDate authorizationCreateDate = null;
    final var latestAuthorisation = authorizationQuery.getLatestByDriverId(driverId);
    if (latestAuthorisation != null) {
      authorizationId = latestAuthorisation.getId();
      authorizationCreateDate = latestAuthorisation.getCreated();
    }
    model.addAttribute("authorizationId", authorizationId);
    model.addAttribute("authorizationCreateDate", authorizationCreateDate);
  }

  private void addAbsencesDataToModel(final Long driverId, final Model model) {
    final var actualAbsences = absenceQuery.getActualAbsencesByDriverId(driverId);
    model.addAttribute("actualAbsences", actualAbsences);
  }
}
