package ee.qrent.billing.transaction.core.service.balance.calculator;

import ee.qrent.billing.constant.api.in.query.GetConstantQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.domain.balance.Balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ee.qrent.billing.transaction.domain.kind.TransactionKindsCode.F;

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
      final Map<String, List<TransactionResponse>> transactionsByKind) {
    System.out.println(
        "Saving Fee transactions during dry run not supported. Amount will be included into Balance");
    final var feeTransaction =
        TransactionResponse.builder().realAmount(feeAmountForPreviousWeek.negate()).build();
    if (transactionsByKind.get(F.name()) == null) {
      transactionsByKind.put(F.name(), new ArrayList<>());
    }

    transactionsByKind.get(F.name()).add(feeTransaction);
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
