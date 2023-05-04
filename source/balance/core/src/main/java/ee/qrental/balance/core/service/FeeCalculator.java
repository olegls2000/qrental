package ee.qrental.balance.core.service;

import static java.math.BigDecimal.ZERO;

import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.common.core.utils.Week;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeCalculator {
  private static final BigDecimal WEEKLY_INTEREST = BigDecimal.valueOf(0.07d);

  private final BalanceLoadPort loadPort;

  public BigDecimal calculate(final Week week, final Long driverId) {
    final var currentWeekNumber = week.weekNumber();
    if (currentWeekNumber == 10 || currentWeekNumber == 11) {

      return ZERO;
    }
    final var previousWeekNumber = currentWeekNumber - 1;
    final var balanceFromPreviousWeek =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);
    final var amountFromPreviousWeek = balanceFromPreviousWeek.getAmount();
    if (amountFromPreviousWeek.compareTo(ZERO) < 0) {

      return amountFromPreviousWeek.multiply(WEEKLY_INTEREST).negate();
    }

    return ZERO;
  }
}
