package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class NewDriverBonusStrategy extends AbstractBonusStrategy {
  private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.25);
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetContractQuery contractQuery;

  public NewDriverBonusStrategy(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetCallSignLinkQuery callSignLinkQuery,
      final GetContractQuery contractQuery) {
    super(transactionQuery, transactionTypeQuery);
    this.callSignLinkQuery = callSignLinkQuery;
    this.contractQuery = contractQuery;
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive() && STRATEGY_NEW_DRIVER_CODE.equals(bonusProgram.getCode());
  }

  private boolean isDriverNew(final Long driverId) {
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
  public Optional<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {

    final var driverId = obligation.getDriverId();

    final var isNew = isDriverNew(driverId);
    if (!isNew) {

      return Optional.empty();
    }

    final var hasActiveContractFor12Weeks = hasActiveContractFor12Weeks(driverId);
    if (!hasActiveContractFor12Weeks) {

      return Optional.empty();
    }

    final var qWeekId = obligation.getQWeekId();
    final var rentAndNonLabelFineAmountAbs =
        getRentAndNonLabelFineTransactionsAbsAmount(driverId, qWeekId);

    final var totalDiscount = rentAndNonLabelFineAmountAbs.multiply(DISCOUNT_RATE);
    final var comment = format("Bonus Transaction for %s Strategy", STRATEGY_NEW_DRIVER_CODE);
    final var transactionTypeId = getBonusTransactionTypeId();
    final var bonusTransaction = new TransactionAddRequest();
    bonusTransaction.setDate(LocalDate.now());
    bonusTransaction.setComment(comment);
    bonusTransaction.setDriverId(driverId);
    bonusTransaction.setAmount(totalDiscount);
    bonusTransaction.setTransactionTypeId(transactionTypeId);

    return Optional.of(bonusTransaction);
  }

  private BigDecimal getRentAndNonLabelFineTransactionsAbsAmount(
      final Long driverId, final Long qWeekId) {
    return getTransactionQuery().getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(
            transaction ->
                List.of(TRANSACTION_TYPE_NAME_WEEKLY_RENT, TRANSACTION_TYPE_NO_LABEL_FINE)
                    .contains(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_NEW_DRIVER_CODE;
  }
}
