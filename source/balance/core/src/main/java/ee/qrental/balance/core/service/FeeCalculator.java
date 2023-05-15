package ee.qrental.balance.core.service;

import ee.qrental.balance.domain.Balance;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeCalculator {
  public BigDecimal calculate(
      final BigDecimal feeTransactionsSum, final Balance previousWeekBalance) {
    final var feeBalanceFromPreviousWeek = previousWeekBalance.getFee();
    return feeTransactionsSum.add(feeBalanceFromPreviousWeek);
  }
}
