package ee.qrental.balance.core.service;

import static java.math.BigDecimal.ZERO;

import ee.qrental.balance.domain.Balance;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AmountCalculator {

  public BigDecimal calculate(
      final List<TransactionResponse> driversTransactions, final Balance previousWeekBalance) {
    final var transactionsSum =
        driversTransactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal::add)
            .orElse(ZERO);
    final var amountFromPreviousWeek = previousWeekBalance.getAmount();

    return transactionsSum.add(amountFromPreviousWeek);
  }
}
