package ee.qrental.bonus.core.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class NewDriverBonusStrategy extends AbstractBonusStrategy {
  private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.25);
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetContractQuery contractQuery;
  private final GetQWeekQuery qWeekQuery;

  public NewDriverBonusStrategy(
          final GetTransactionQuery transactionQuery,
          final GetTransactionTypeQuery transactionTypeQuery,
          final GetCallSignLinkQuery callSignLinkQuery,
          final GetContractQuery contractQuery,
          final GetQWeekQuery qWeekQuery) {
    super(transactionQuery, transactionTypeQuery);
    this.callSignLinkQuery = callSignLinkQuery;
    this.contractQuery = contractQuery;
      this.qWeekQuery = qWeekQuery;
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive() && STRATEGY_NEW_DRIVER_CODE.equals(bonusProgram.getCode());
  }

  private boolean isDriverNew(final Long driverId) {

    //TODO:   Fix Unit Test
    // TODO: new driver if:
    // First Car link  start date, and do not count partial first week-> if Wednesday is start date,
    // we calculate weeks from next Monday
    // Driver is new during first 4 entire weeks
    final var callSignLinks = callSignLinkQuery.getCallSignLinksByDriverId(driverId);
    final var callSignLinksCount = callSignLinks.size();
    if (callSignLinksCount > 1) {
      System.out.println(
          "Driver with id: "
              + driverId
              + " is not new, it has "
              + callSignLinksCount
              + " Call Signs");
      return false;
    }

    if (callSignLinksCount == 0) {
      System.out.println(
          "Driver with id: " + driverId + " does not have Call Signs, hence no Rent Contracts");
      return false;
    }
    final var callSignLink = callSignLinks.get(0);

    final var endDate = callSignLink.getDateEnd();
    if (endDate == null || endDate.isAfter(LocalDate.now())) {
      System.out.println(
          "Driver with id: " + driverId + " has active Call Sign Link, hence Rent Contract");
      return true;
    }
    System.out.println(
        "Driver with id: " + driverId + " has no active Call Sign Link, hence no Rent Contract");
    return false;
  }

  final boolean hasActiveContractFor12Weeks(final Long driverId) {
    final var contract = contractQuery.getActiveContractByDriverId(driverId);
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

  @Override
  public List<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {

    final var driverId = obligation.getDriverId();

    final var isNew = isDriverNew(driverId);
    if (!isNew) {

      return emptyList();
    }

    final var hasActiveContractFor12Weeks = hasActiveContractFor12Weeks(driverId);
    if (!hasActiveContractFor12Weeks) {

      return emptyList();
    }

    final var qWeekId = obligation.getQWeekId();
    final var rentAndNonLabelFineAmountAbs =
        getRentAndNonLabelFineTransactionsAbsAmount(driverId, qWeekId);
    final var nextWeek = qWeekQuery.getOneAfterById(qWeekId);
    final var totalDiscount = rentAndNonLabelFineAmountAbs.multiply(DISCOUNT_RATE);
    final var bonusTransaction = new TransactionAddRequest();

    //TODO:   Fix Unit Test
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
