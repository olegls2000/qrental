package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.math.BigDecimal.ZERO;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculator {

  private static final BigDecimal DEBT_RATE = BigDecimal.valueOf(0.25d);

  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;

  public BigDecimal calculate(final Long driverId, final Long qWeekId) {
    final var previousQWeek = qWeekQuery.getOneBeforeById(qWeekId);
    final var previousQWeekId = previousQWeek.getId();
    final var rentObligation = getRentObligation(driverId, qWeekId);
    final var rentObligationAbs = rentObligation.abs();
    final var previousWeekBalance =
        balanceQuery.getRawBalanceTotalByDriverIdAndQWeekId(driverId, previousQWeekId);
    if (previousWeekBalance.compareTo(ZERO) < 0) {
      final var debt = rentObligationAbs.multiply(DEBT_RATE);

      return rentObligationAbs.add(debt);
    }

    if (previousWeekBalance.compareTo(ZERO) > 0) {
      return rentObligationAbs.subtract(previousWeekBalance.abs());
    }

    return rentObligationAbs;
  }

  private BigDecimal getRentObligation(final Long driverId, final Long qWeekId) {

    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(
            transaction ->
                List.of(TRANSACTION_TYPE_NAME_WEEKLY_RENT, TRANSACTION_TYPE_NO_LABEL_FINE)
                    .contains(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add);
  }
}
