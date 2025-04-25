package ee.qrent.billing.bonus.core.service;

import static ee.qrent.common.utils.QTimeUtils.getWeekNumber;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.bonus.domain.BonusProgram;
import ee.qrent.billing.bonus.domain.Obligation;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class NewDriverBonusStrategy extends AbstractBonusStrategy {
  private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.25);
  private static final Integer NEW_DRIVER_PERIOD_IN_DAYS = 28;
  private final GetCarLinkQuery carLinkQuery;
  private final GetContractQuery contractQuery;
  private final GetQWeekQuery qWeekQuery;
  private final QDateTime qDateTime;

  public NewDriverBonusStrategy(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetCarLinkQuery carLinkQuery,
      final GetContractQuery contractQuery,
      final GetQWeekQuery qWeekQuery,
      QDateTime qDateTime) {
    super(transactionQuery, transactionTypeQuery);
    this.carLinkQuery = carLinkQuery;
    this.contractQuery = contractQuery;
    this.qWeekQuery = qWeekQuery;
    this.qDateTime = qDateTime;
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive() && STRATEGY_NEW_DRIVER_CODE.equals(bonusProgram.getCode());
  }

  private boolean isDriverNew(final Long driverId, final Long obligationQWeekId) {
    final var firstCarLink = carLinkQuery.getFirstLinkByDriverId(driverId);
    if (firstCarLink == null) {
      System.out.println(
          "Driver with id: " + driverId + " did not have Car Link, hence no Rent Contracts");
      return false;
    }

    final var dateStart = firstCarLink.getDateStart();
    LocalDate firstMonday = dateStart;
    if (dateStart.getDayOfWeek() != DayOfWeek.MONDAY) {
      final var dateStartYear = dateStart.getYear();
      final var dateStartWeekNumber = getWeekNumber(dateStart);
      final var carLinkStartWeek = qWeekQuery.getByYearAndNumber(dateStartYear, dateStartWeekNumber);
      final var carLinkStartWeekNext = qWeekQuery.getOneAfterById(carLinkStartWeek.getId());
      firstMonday = carLinkStartWeekNext.getStart();
    }

    final var today = qWeekQuery.getOneAfterById(obligationQWeekId).getStart();
    final var activeDates = DAYS.between(firstMonday, today);
    if (activeDates < NEW_DRIVER_PERIOD_IN_DAYS) {
      System.out.println("Driver with id: " + driverId + " has Car Link, less then 4 entire weeks");
      return true;
    }

    System.out.println(
        "Driver with id: " + driverId + " has active Car Link, more then 4 entire weeks");
    return false;
  }

  final boolean hasActiveContractFor12Weeks(final Long driverId) {
    final var contract = contractQuery.getLatestContractByDriverId(driverId);
    if (contract == null) {
      System.out.println("Driver with id: " + driverId + " has no active Contract");

      return false;
    }
    final var duration = contract.getDuration();
    if (duration == 12) {
      System.out.println("Driver with id: " + driverId + " has active Contract for 12 weeks");

      return true;
    }
    System.out.println("Driver with id: " + driverId + " has no active Contract for 12 weeks");

    return false;
  }

  /**
   * First Car link start date, and do not count partial first week-> if Wednesday is start date, we
   * calculate weeks from next Monday Driver is new during first 4 entire weeks
   *
   * @param obligation
   * @param weekPositiveAmount
   * @return
   */
  @Override
  public List<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {
    final var driverId = obligation.getDriverId();
    final var qWeekId = obligation.getQWeekId();
    if (obligation.getMatchCount() <= 0) {
      System.out.println(
          "Obligation was not matched for current Week, New Driver Bonus is not allowed for Driver with id: "
              + driverId);
      return emptyList();
    }

    final var isNew = isDriverNew(driverId, qWeekId);
    if (!isNew) {

      return emptyList();
    }

    final var hasActiveContractFor12Weeks = hasActiveContractFor12Weeks(driverId);
    if (!hasActiveContractFor12Weeks) {

      return emptyList();
    }

    final var rentAndNonLabelFineAmountAbs =
        getRentAndNonLabelFineTransactionsAbsAmount(driverId, qWeekId);
    final var nextWeek = qWeekQuery.getOneAfterById(qWeekId);
    final var totalDiscount = rentAndNonLabelFineAmountAbs.multiply(DISCOUNT_RATE);
    final var bonusTransaction = new TransactionAddRequest();

    bonusTransaction.setDate(nextWeek.getStart());
    bonusTransaction.setComment(getComment());
    bonusTransaction.setDriverId(driverId);
    bonusTransaction.setAmount(totalDiscount);
    bonusTransaction.setTransactionTypeId(getBonusTransactionTypeId());

    return singletonList(bonusTransaction);
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_NEW_DRIVER_CODE;
  }
}
