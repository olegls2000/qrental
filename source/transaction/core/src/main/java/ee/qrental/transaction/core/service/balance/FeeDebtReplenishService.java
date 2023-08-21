package ee.qrental.transaction.core.service.balance;

import static ee.qrental.transaction.api.in.TransactionConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Comparator.comparing;

import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeDebtReplenishService {

    private final BalanceLoadPort loadPort;
    private final TransactionAddUseCase transactionAddUseCase;
    private final GetTransactionTypeQuery transactionTypeQuery;
    private final GetTransactionQuery transactionQuery;
    public List<TransactionResponse> replenish(final Week week, final DriverResponse driver){
        final List<TransactionResponse> feeReplenishTransactions = new ArrayList<>();
        final var driverId = driver.getId();
        final var currentWeekNumber = week.weekNumber();
        final var previousWeekNumber = currentWeekNumber - 1;
        final var balanceFromPreviousWeek =
                loadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, week.getYear(), previousWeekNumber);
        var feeAmountToReplenish = balanceFromPreviousWeek.getFee();
        if(feeAmountToReplenish.compareTo(ZERO) <= 0) {
            System.out.println("Fee replenish is not required. Balance for the previous week ("
                    + previousWeekNumber + ") does not have Fee Debt.");
            return feeReplenishTransactions;
        }

        final var positiveSortedTransactions = getListOfPositiveNonFeeTransactions(week, driverId);
        for(final TransactionResponse transaction: positiveSortedTransactions){
            feeAmountToReplenish  = replenishAndGetNewFeeDebt(week, transaction, feeAmountToReplenish, feeReplenishTransactions);
            if(feeAmountToReplenish.compareTo(ZERO) <= 0) {
                return feeReplenishTransactions;
            }
        }
        return feeReplenishTransactions;
    }
    
    private List<TransactionResponse> getListOfPositiveNonFeeTransactions(final Week week, final Long driverId){
        final var filter = PeriodAndDriverFilter.builder()
                .dateStart(week.start())
                .dateEnd(week.end())
                .driverId(driverId)
                .build();

        return transactionQuery.getAllByFilter(filter)
                .stream()
                .filter(tx -> isNotFeeType(tx.getType()))
                .filter(transaction-> transaction.getRealAmount().compareTo(ZERO) > 0)
                .sorted(comparing(TransactionResponse::getRealAmount))
                .toList();
    }

    private BigDecimal replenishAndGetNewFeeDebt(
            final Week week,
            final TransactionResponse donorTransaction,
            final BigDecimal feeAmountToReplenish,
            final List<TransactionResponse> feeReplenishTransactions) {
        final var transactionAmount = donorTransaction.getRealAmount();
        if(transactionAmount.compareTo(ZERO) <= 0) {
            throw new RuntimeException("You are trying to replenish Fee Debt from Negative Transaction. Check Transaction selection Logic!");
        }

        if(transactionAmount.compareTo(feeAmountToReplenish) >= 0){
            final var feeReplenishTransaction = getFeeReplenishTransaction(
                    feeAmountToReplenish,
                    donorTransaction.getDriverId(),
                    week);
           final var feeReplenishTransactionId = transactionAddUseCase.add(feeReplenishTransaction);
            feeReplenishTransactions.add(TransactionResponse.builder().id(feeReplenishTransactionId)
                    .realAmount(feeAmountToReplenish)
                    .build());
            final var compensationTransaction = getCompensationTransaction(
                    feeAmountToReplenish,
                    donorTransaction.getDriverId(),
                    week,
                    feeReplenishTransactionId,
                    donorTransaction.getId());
            transactionAddUseCase.add(compensationTransaction);

            return ZERO;
        }
        final var possibleReplenishmentAmount = transactionAmount;
        final var feeReplenishTransaction = getFeeReplenishTransaction(
                possibleReplenishmentAmount,
                donorTransaction.getDriverId(),
                week);
        final var feeReplenishTransactionId = transactionAddUseCase.add(feeReplenishTransaction);
        feeReplenishTransactions.add(TransactionResponse.builder().id(feeReplenishTransactionId)
                .realAmount(possibleReplenishmentAmount)
                .build());
        final var compensationTransaction = getCompensationTransaction(
                possibleReplenishmentAmount,
                donorTransaction.getDriverId(),
                week,
                feeReplenishTransactionId,
                donorTransaction.getId());
        transactionAddUseCase.add(compensationTransaction);

        return feeAmountToReplenish.subtract(possibleReplenishmentAmount);
    }

    private  TransactionAddRequest  getCompensationTransaction(
            final BigDecimal compensationAmount,
            final Long driverId,
            final Week week,
            final Long feeReplenishTransactionId,
            final Long donorTransactionId){
        final var compensationTransactionAddRequest =  getTransactionRequest(
                compensationAmount,
                driverId,
                week,
                TRANSACTION_TYPE_COMPENSATION,
                format("Compensation. Automatically created transaction, during Balance Calculation, for the compensation of Fee replenishment." +
                                "Fee replenishment Transaction ID: %d, Donor Transaction ID: %d",
                        feeReplenishTransactionId,
                        donorTransactionId));

        return compensationTransactionAddRequest;
    }
    
  private  TransactionAddRequest  getFeeReplenishTransaction(
          final BigDecimal feeReplenishmentAmount,
          final Long driverId,
          final Week week){
     final var feeReplenishTransactionAddRequest =  getTransactionRequest(
             feeReplenishmentAmount,
              driverId,
              week,
              TRANSACTION_TYPE_NAME_FEE_REPLENISH,
              "Replenishment. Automatically created transaction, during Balance Calculation, for the replenishing Fee debt");

     return feeReplenishTransactionAddRequest;
    }

    private TransactionAddRequest getTransactionRequest(
            final BigDecimal amount,
            final Long driverId,
            final Week week,
            final String transactionTypeName,
            final String comment) {
        final var transactionType =
                transactionTypeQuery.getByName(transactionTypeName);
        if(transactionType == null) {
            throw new RuntimeException("No Transaction Type with name: " + transactionTypeName + " was found in DB");
        }
        final var transactionAddRequest = new TransactionAddRequest();
        transactionAddRequest.setAmount(amount);
        transactionAddRequest.setDate(week.end());
        transactionAddRequest.setWithVat(FALSE);
        transactionAddRequest.setTransactionTypeId(transactionType.getId());
        transactionAddRequest.setDriverId(driverId);
        transactionAddRequest.setWeekNumber(week.weekNumber());
        transactionAddRequest.setComment(comment);

        return transactionAddRequest;
    }
}
