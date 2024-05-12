package ee.qrental.transaction.core.service.balance.calculator;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BalanceCalculationStrategyDryRun extends AbstractBalanceCalculator {

  public BalanceCalculationStrategyDryRun(
      final BalanceDeriveService balanceDeriveService, final GetConstantQuery constantQuery) {
    super(balanceDeriveService, constantQuery);
  }

  @Override
  protected void handleFeeTransaction(
      final BigDecimal feeAmountForPreviousWeek,
      final QWeekResponse requestedQWeek,
      final Long driverId,
      final Map<TransactionKindsCode, List<TransactionResponse>> transactionsByKind) {
    System.out.println(
        "Saving Fee transactions during dry run not supported. Amount will be included into Balance");
    final var feeTransaction =
        TransactionResponse.builder().realAmount(feeAmountForPreviousWeek).build();
    if (transactionsByKind.get(TransactionKindsCode.F) == null) {
      transactionsByKind.put(TransactionKindsCode.F, new ArrayList<>());
    }

    transactionsByKind.get(TransactionKindsCode.F).add(feeTransaction);
  }

  @Override
  protected void saveBalanceIfNecessary(final Balance balance) {
    System.out.println("Saving balance during dry run not supported: " + balance);
  }

  @Override
  protected Balance saveAndGetDerivedBalanceIfNecessary(final Balance derivedBalance) {
    System.out.println("Saving balance during dry run not supported: " + derivedBalance);

    return derivedBalance;
  }

  @Override
  public boolean canApply(final String strategyName) {
    return DRY_RUN.equals(strategyName);
  }
}
