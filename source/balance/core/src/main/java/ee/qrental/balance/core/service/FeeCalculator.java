package ee.qrental.balance.core.service;

import static ee.qrental.balance.core.service.FeeTransactionCreator.TRANSACTION_TYPE_NAME_FEE_DEBT;
import static java.math.BigDecimal.ZERO;

import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.common.core.utils.Week;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeCalculator {

  private final BalanceLoadPort loadPort;
  private final GetTransactionQuery transactionQuery;

  public BigDecimal calculate(final Week week, final Long driverId) {
    final var currentWeekNumber = week.weekNumber();
    if (currentWeekNumber == 10 || currentWeekNumber == 11) {
      return ZERO;
    }
    final var previousWeekNumber = currentWeekNumber - 1;
    final var balanceFromPreviousWeek =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);
    final var feeBalanceFromPreviousWeek = balanceFromPreviousWeek.getFee();

    final var filter =
        PeriodAndDriverFilter.builder()
            .driverId(driverId)
            .dateStart(week.start())
            .datEnd(week.end())
            .build();
    final var feeTransactionsSum =
        transactionQuery.getAllByFilter(filter).stream()
            .filter(tr -> tr.getType().equals(TRANSACTION_TYPE_NAME_FEE_DEBT))
            .map(tr -> tr.getRealAmount())
            .reduce(BigDecimal::add)
            .orElse(ZERO);
    return feeTransactionsSum.add(feeBalanceFromPreviousWeek);
  }
}
