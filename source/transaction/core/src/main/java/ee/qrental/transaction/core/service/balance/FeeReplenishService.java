package ee.qrental.transaction.core.service.balance;

import static ee.qrental.transaction.api.in.TransactionConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeReplenishService {

  private final GetQWeekQuery qWeekQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final TransactionAddUseCase transactionAddUseCase;
  private final BalanceLoadPort balanceLoadPort;

  public void replenish(final QWeekResponse week, final Long driverId) {
    var feeBalanceFromPreviousWeek = getFeeAmountFromPreviousWeek(driverId, week.getId());
    if (feeBalanceFromPreviousWeek.compareTo(ZERO) >= 0) {
      System.out.println("Fee replenish is not required in Week: " + week.getNumber());

      return;
    }
    var feeAmountToReplenish = feeBalanceFromPreviousWeek.abs();
    final var donorTransactions = getListOfPositiveNonFeeTransactions(week, driverId);
    for (final TransactionResponse donorTransaction : donorTransactions) {
      feeAmountToReplenish =
          replenishAndGetNewFeeAmountToReplenish(week, donorTransaction, feeAmountToReplenish);
      if (feeAmountToReplenish.compareTo(ZERO) <= 0) {

        return;
      }
    }
  }

  private BigDecimal getFeeAmountFromPreviousWeek(final Long driverId, final Long qWeekId) {
    final var previousQWeek = qWeekQuery.getPrevious(qWeekId);
    if (previousQWeek == null) {
      return ZERO;
    }
    final var balanceFromPreviousWeek =
        balanceLoadPort.loadByDriverIdAndQWeekId(driverId, previousQWeek.getId());
    var feeBalanceFromPreviousWeek = balanceFromPreviousWeek.getFee();

    return feeBalanceFromPreviousWeek;
  }

  private List<TransactionResponse> getListOfPositiveNonFeeTransactions(
      final QWeekResponse week, final Long driverId) {
    final var filter =
        PeriodAndDriverFilter.builder()
            .dateStart(week.getStart())
            .dateEnd(week.getEnd())
            .driverId(driverId)
            .build();

    return transactionQuery.getAllByFilter(filter).stream()
        .filter(tx -> isNotFeeType(tx.getType()))
        .filter(transaction -> transaction.getRealAmount().compareTo(ZERO) > 0)
        .sorted((tx1, tx2) -> tx2.getRealAmount().compareTo(tx1.getRealAmount()))
        .toList();
  }

  private BigDecimal replenishAndGetNewFeeAmountToReplenish(
      final QWeekResponse week,
      final TransactionResponse donorTransaction,
      final BigDecimal feeAmountToReplenish) {
    final var transactionAmount = donorTransaction.getRealAmount();
    if (transactionAmount.compareTo(ZERO) <= 0) {
      throw new RuntimeException(
          "You are trying to replenish Fee Debt from Negative Transaction. Check Transaction selection Logic!");
    }

    final var leftoverToReplenish = feeAmountToReplenish.subtract(transactionAmount);
    if (leftoverToReplenish.compareTo(ZERO) >= 0) {
      final var possibleReplenishmentAmount = transactionAmount;
      replenishAndCompensate(
          possibleReplenishmentAmount,
          donorTransaction.getDriverId(),
          week,
          donorTransaction.getId());

      return leftoverToReplenish;
    }

    final var possibleReplenishmentAmount = feeAmountToReplenish;
    replenishAndCompensate(
        possibleReplenishmentAmount,
        donorTransaction.getDriverId(),
        week,
        donorTransaction.getId());

    return leftoverToReplenish;
  }

  private void replenishAndCompensate(
      final BigDecimal feeAmountToReplenish,
      final Long driverId,
      final QWeekResponse week,
      final Long donorTransactionId) {
    final var feeReplenishTransaction =
        getFeeReplenishTransaction(feeAmountToReplenish, driverId, week);
    final var positiveFeeTransactionId = transactionAddUseCase.add(feeReplenishTransaction);

    final var compensationTransaction =
        getCompensationTransaction(
            feeAmountToReplenish, driverId, week, positiveFeeTransactionId, donorTransactionId);
    transactionAddUseCase.add(compensationTransaction);
  }

  private TransactionAddRequest getCompensationTransaction(
      final BigDecimal compensationAmount,
      final Long driverId,
      final QWeekResponse week,
      final Long feeReplenishTransactionId,
      final Long donorTransactionId) {
    final var compensationTransactionAddRequest =
        getTransactionRequest(
            compensationAmount,
            driverId,
            week,
            TRANSACTION_TYPE_COMPENSATION,
            format(
                "Compensation. Automatically created transaction, during Balance Calculation, for the compensation of Fee replenishment."
                    + "Fee replenishment Transaction ID: %d, Donor Transaction ID: %d",
                feeReplenishTransactionId, donorTransactionId));

    return compensationTransactionAddRequest;
  }

  private TransactionAddRequest getFeeReplenishTransaction(
      final BigDecimal feeReplenishmentAmount, final Long driverId, final QWeekResponse week) {
    final var feeReplenishTransactionAddRequest =
        getTransactionRequest(
            feeReplenishmentAmount,
            driverId,
            week,
            TRANSACTION_TYPE_NAME_FEE_REPLENISH,
            "Replenishment. Automatically created transaction, during Balance Calculation, for the replenishing of Fee debt");

    return feeReplenishTransactionAddRequest;
  }

  private TransactionAddRequest getTransactionRequest(
      final BigDecimal amount,
      final Long driverId,
      final QWeekResponse week,
      final String transactionTypeName,
      final String comment) {
    final var transactionType = transactionTypeQuery.getByName(transactionTypeName);
    if (transactionType == null) {
      throw new RuntimeException(
          "No Transaction Type with name: " + transactionTypeName + " was found in DB");
    }
    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(amount);
    transactionAddRequest.setDate(week.getEnd());
    transactionAddRequest.setWithVat(FALSE);
    transactionAddRequest.setTransactionTypeId(transactionType.getId());
    transactionAddRequest.setDriverId(driverId);
    transactionAddRequest.setWeekNumber(week.getNumber());
    transactionAddRequest.setComment(comment);

    return transactionAddRequest;
  }
}
