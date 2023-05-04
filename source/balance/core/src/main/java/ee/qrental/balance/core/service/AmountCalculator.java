package ee.qrental.balance.core.service;

import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.common.core.utils.Week;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AmountCalculator {

  private final BalanceLoadPort loadPort;

  public BigDecimal calculate(
      final Week week, final Long driverId, final List<TransactionResponse> driversTransactions) {
    final var transactionsSum =
        driversTransactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal::add)
            .get();
    final var previousWeekNumber = week.weekNumber() - 1;
    final var balanceFromPreviousWeek =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);
    final var amountFromPreviousWeek = balanceFromPreviousWeek.getAmount();

    return transactionsSum.add(amountFromPreviousWeek);
  }
}
