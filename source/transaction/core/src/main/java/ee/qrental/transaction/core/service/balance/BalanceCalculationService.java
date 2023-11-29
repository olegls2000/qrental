package ee.qrental.transaction.core.service.balance;

import static ee.qrental.transaction.api.in.TransactionConstants.*;
import static ee.qrental.transaction.core.utils.FeeUtils.FEE_WEEKLY_INTEREST;
import static ee.qrental.transaction.core.utils.FeeUtils.getWeekFeeInterest;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.*;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;
// import jakarta.transaction.Transactional;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.balance.BalanceCalculationResult;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {
  private static final BigDecimal FEE_CALCULATION_THRESHOLD = ZERO;

  private final GetConstantQuery constantQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final TransactionLoadPort transactionLoadPort;
  private final TransactionTypeLoadPort transactionTypeLoadPort;
  private final TransactionKindLoadPort transactionKindLoadPort;
  private final BalanceCalculationAddPort balanceCalculationAddPort;
  private final BalanceAddPort balanceAddPort;
  private final BalanceLoadPort balanceLoadPort;
  private final BalanceCalculationAddRequestMapper addRequestMapper;
  private final TransactionAddUseCase transactionAddUseCase;

  // @Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var requestedQWeekId = addRequest.getQWeekId();
    final var domain = addRequestMapper.toDomain(addRequest);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);
    final var previousQWeek = qWeekQuery.getOneBeforeById(requestedQWeekId);
    driverQuery.getAll()
        .forEach(
            driver -> {
              final var driverId = driver.getId();
              final var previousQWeekBalance =
                  balanceLoadPort.loadByDriverIdAndQWeekId(driverId, previousQWeek.getId());
              if (previousQWeekBalance == null) {
                throw new RuntimeException(
                    format("Balance for previous qWeek %d, must exist", previousQWeek.getNumber()));
              }
              if (!previousQWeekBalance.getDerived()) {
                throw new RuntimeException(
                    format(
                        "Balance for previous qWeek %d, must be derived",
                        previousQWeek.getNumber()));
              }

              if (driver.getNeedFee()) {
                BigDecimal feeAmountForRequestedWeek;
                final var faAmountFromPreviousWeekBalance = previousQWeekBalance.getFeeAbleAmount();
                if (faAmountFromPreviousWeekBalance.compareTo(FEE_CALCULATION_THRESHOLD) > 0) {
                  final var weeklyInterestConstant = constantQuery.getByName(FEE_WEEKLY_INTEREST);

                  final var weeklyInterest = getWeekFeeInterest(weeklyInterestConstant);
                  final var nominalWeeklyFee =
                      faAmountFromPreviousWeekBalance.multiply(weeklyInterest);

                  final var feeAmountFromPreviousWeek = previousQWeekBalance.getFeeAmount();
                  final var faAmountFromPreviousWeek = previousQWeekBalance.getFeeAbleAmount();
                  final var totalFeeDebt = nominalWeeklyFee.add(feeAmountFromPreviousWeek);
                  if (totalFeeDebt.compareTo(faAmountFromPreviousWeek) < 0) {
                    feeAmountForRequestedWeek = nominalWeeklyFee;
                  } else {
                    final var feeOverdue = totalFeeDebt.subtract(faAmountFromPreviousWeek);
                    feeAmountForRequestedWeek = nominalWeeklyFee.subtract(feeOverdue);
                  }
                  final var transactionType =
                      transactionTypeLoadPort.loadByName(TRANSACTION_TYPE_NAME_FEE_DEBT);
                  final var feeTransactionAddRequest = new TransactionAddRequest();
                  feeTransactionAddRequest.setAmount(feeAmountForRequestedWeek);
                  feeTransactionAddRequest.setDate(requestedQWeek.getEnd());
                  feeTransactionAddRequest.setWithVat(FALSE);
                  feeTransactionAddRequest.setTransactionTypeId(transactionType.getId());
                  feeTransactionAddRequest.setDriverId(driverId);
                  feeTransactionAddRequest.setWeekNumber(requestedQWeek.getNumber());
                  feeTransactionAddRequest.setComment(
                      "automatically created during Balance calculation");

                  transactionAddUseCase.add(feeTransactionAddRequest);
                }
              }
              final var feeAmount =
                  getBalanceAmount(
                      TransactionKindsCode.F,
                      driverId,
                      requestedQWeek,
                      Balance::getFeeAmount,
                      previousQWeekBalance);
              final var nonFeeAbleAmount =
                  getBalanceAmount(
                      TransactionKindsCode.NFA,
                      driverId,
                      requestedQWeek,
                      Balance::getNonFeeAbleAmount,
                      previousQWeekBalance);
              final var feeAbleAmount =
                  getBalanceAmount(
                      TransactionKindsCode.FA,
                      driverId,
                      requestedQWeek,
                      Balance::getFeeAbleAmount,
                      previousQWeekBalance);
              final var positiveAmount =
                  getBalanceAmount(
                      TransactionKindsCode.P,
                      driverId,
                      requestedQWeek,
                      Balance::getPositiveAmount,
                      previousQWeekBalance);

              final var balance =
                  Balance.builder()
                      .driverId(driverId)
                      .qWeekId(requestedQWeekId)
                      .created(LocalDate.now())
                      .feeAmount(feeAmount)
                      .nonFeeAbleAmount(nonFeeAbleAmount)
                      .feeAbleAmount(feeAbleAmount)
                      .positiveAmount(positiveAmount)
                      .derived(FALSE)
                      .build();
              final var balanceSaved = balanceAddPort.add(balance);
              final var balanceDerived = derive(balanceSaved);
              balanceAddPort.add(balanceDerived);

              final var balanceCalculationResult =
                  getBalanceCalculationResult(balance, requestedQWeek);
              domain.getResults().add(balanceCalculationResult);
            });
    balanceCalculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Balance Calculation took %d milli seconds \n", calculationDuration);
  }

  private Balance derive(final Balance balanceToDerive) {
    var derivedPositiveAmount = balanceToDerive.getPositiveAmount();
    var derivedFeeAmount = balanceToDerive.getFeeAmount();
    var derivedNonFeeAbleAmount = balanceToDerive.getNonFeeAbleAmount();
    var derivedFeeAbleAmount = balanceToDerive.getFeeAbleAmount();

    if (derivedPositiveAmount.compareTo(ZERO) == 0) {
      System.out.println(
          "No positive amount. Derive is impossible for the Balance: " + balanceToDerive);

      return Balance.builder()
          .derived(TRUE)
          .driverId(balanceToDerive.getDriverId())
          .created(LocalDate.now())
          .qWeekId(balanceToDerive.getQWeekId())
          .feeAmount(balanceToDerive.getFeeAmount())
          .nonFeeAbleAmount(balanceToDerive.getNonFeeAbleAmount())
          .feeAbleAmount(balanceToDerive.getFeeAbleAmount())
          .positiveAmount(balanceToDerive.getPositiveAmount())
          .build();
    }

    if (derivedFeeAmount.compareTo(ZERO) == 0
        && derivedFeeAbleAmount.compareTo(ZERO) == 0
        && derivedNonFeeAbleAmount.compareTo(ZERO) == 0) {

      System.out.println(
          "No negative amounts. Derive is not required for Balance: " + balanceToDerive);

      return Balance.builder()
          .derived(TRUE)
          .driverId(balanceToDerive.getDriverId())
          .created(LocalDate.now())
          .qWeekId(balanceToDerive.getQWeekId())
          .feeAmount(balanceToDerive.getFeeAmount())
          .nonFeeAbleAmount(balanceToDerive.getNonFeeAbleAmount())
          .feeAbleAmount(balanceToDerive.getFeeAbleAmount())
          .positiveAmount(balanceToDerive.getPositiveAmount())
          .build();
    }
    // positive: 5, fee: 2
    if (derivedPositiveAmount.compareTo(derivedFeeAmount) > 0) {
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedFeeAmount); // 3
      derivedFeeAmount = derivedFeeAmount.subtract(derivedFeeAmount); // 0
    } else {
      // positive: 2, fee: 3
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedPositiveAmount); // 0
      derivedFeeAmount = derivedFeeAmount.subtract(derivedPositiveAmount); // 1
    }

    if (derivedPositiveAmount.compareTo(ZERO) == 0) {
      System.out.println("No positive amount. Derive is done for the Balance: " + balanceToDerive);

      return Balance.builder()
          .derived(TRUE)
          .driverId(balanceToDerive.getDriverId())
          .created(LocalDate.now())
          .qWeekId(balanceToDerive.getQWeekId())
          .feeAmount(derivedFeeAmount)
          .nonFeeAbleAmount(derivedNonFeeAbleAmount)
          .feeAbleAmount(derivedFeeAbleAmount)
          .positiveAmount(derivedPositiveAmount)
          .build();
    }

    // positive: 5, NonFeeAble: 2
    if (derivedPositiveAmount.compareTo(derivedNonFeeAbleAmount) > 0) {
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedNonFeeAbleAmount); // 3
      derivedNonFeeAbleAmount = derivedFeeAmount.subtract(derivedNonFeeAbleAmount); // 0
    } else {
      // positive: 2, NonFeeAble: 3
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedPositiveAmount); // 0
      derivedNonFeeAbleAmount = derivedNonFeeAbleAmount.subtract(derivedPositiveAmount); // 1
    }

    if (derivedPositiveAmount.compareTo(ZERO) == 0) {
      System.out.println("No positive amount. Derive is done for the Balance: " + balanceToDerive);

      return Balance.builder()
          .derived(TRUE)
          .driverId(balanceToDerive.getDriverId())
          .created(LocalDate.now())
          .qWeekId(balanceToDerive.getQWeekId())
          .feeAmount(derivedFeeAmount)
          .nonFeeAbleAmount(derivedNonFeeAbleAmount)
          .feeAbleAmount(derivedFeeAbleAmount)
          .positiveAmount(derivedPositiveAmount)
          .build();
    }

    // positive: 5, feeAble: 2
    if (derivedPositiveAmount.compareTo(derivedFeeAbleAmount) > 0) {
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedFeeAbleAmount); // 3
      derivedFeeAbleAmount = derivedFeeAmount.subtract(derivedFeeAbleAmount); // 0
    } else {
      // positive: 2, feeAble: 3
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedPositiveAmount); // 0
      derivedFeeAbleAmount = derivedFeeAbleAmount.subtract(derivedPositiveAmount); // 1
    }

    return Balance.builder()
        .derived(TRUE)
        .driverId(balanceToDerive.getDriverId())
        .created(LocalDate.now())
        .qWeekId(balanceToDerive.getQWeekId())
        .feeAmount(derivedFeeAmount)
        .nonFeeAbleAmount(derivedNonFeeAbleAmount)
        .feeAbleAmount(derivedFeeAbleAmount)
        .positiveAmount(derivedPositiveAmount)
        .build();
  }

  private BigDecimal getBalanceAmount(
      final TransactionKindsCode kindEnum,
      final Long driverId,
      final QWeekResponse requestedQWeek,
      final Function<Balance, BigDecimal> getAmount,
      final Balance previousWeekBalance) {
    final var kind = transactionKindLoadPort.loadByCode(kindEnum.name());
    final var kindId = kind.getId();
    final var pTransactions =
        transactionLoadPort.loadAllByDriverIdAndKindIdAndBetweenDays(
            driverId, kindId, requestedQWeek.getStart(), requestedQWeek.getEnd());
    final var transactionAmountSum =
        pTransactions.stream()
            .map(Transaction::getRealAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return transactionAmountSum.add(getAmount.apply(previousWeekBalance));
  }

  private BalanceCalculationResult getBalanceCalculationResult(
      final Balance savedBalance, final QWeekResponse requestedWeek) {
    final var transactionIds =
        transactionLoadPort
            .loadAllByDriverIdAndBetweenDays(
                savedBalance.getDriverId(), requestedWeek.getStart(), requestedWeek.getEnd())
            .stream()
            .map(Transaction::getId)
            .collect(toSet());
    return BalanceCalculationResult.builder()
        .balance(savedBalance)
        .transactionIds(transactionIds)
        .build();
  }
}
