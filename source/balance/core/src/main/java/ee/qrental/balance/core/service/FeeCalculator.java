package ee.qrental.balance.core.service;

import static java.math.BigDecimal.ZERO;

import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.common.core.utils.Week;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeCalculator {

  private final BalanceLoadPort loadPort;

  public BigDecimal calculate(
      final Week week, final Long driverId, final BigDecimal feeTransactionsSum) {
    final var currentWeekNumber = week.weekNumber();
    if (currentWeekNumber == 10) {
      return ZERO;
    }
    final var previousWeekNumber = currentWeekNumber - 1;
    final var balanceFromPreviousWeek =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);
    final var feeBalanceFromPreviousWeek = balanceFromPreviousWeek.getFee();
    return feeTransactionsSum.add(feeBalanceFromPreviousWeek);
  }
}
