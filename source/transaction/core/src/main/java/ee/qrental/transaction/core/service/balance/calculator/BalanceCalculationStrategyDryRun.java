package ee.qrental.transaction.core.service.balance.calculator;


import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.domain.balance.Balance;
import java.math.BigDecimal;

public class BalanceCalculationStrategyDryRun extends AbstractBalanceCalculator {

  public BalanceCalculationStrategyDryRun(
      final BalanceDeriveService balanceDeriveService,
      final GetConstantQuery constantQuery) {
    super(balanceDeriveService, constantQuery);
  }

  @Override
  protected void saveFeeTransactionIfNecessary(
      BigDecimal feeAmountForPreviousWeek, QWeekResponse requestedQWeek, Long driverId) {
    System.out.println(
        "Saving Fee transactions during dry run not supported. Amount will be included into Balance");
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
