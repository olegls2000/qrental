package ee.qrental.transaction.core.service.balance;

import ee.qrental.transaction.domain.balance.Balance;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceFeeCalculator {
  public BigDecimal calculate(
      final BigDecimal feeTransactionsSum, final Balance previousWeekBalance) {
    final var feeBalanceFromPreviousWeek = previousWeekBalance.getFee();
    return feeTransactionsSum.add(feeBalanceFromPreviousWeek);
  }
}
