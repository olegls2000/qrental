package ee.qrent.billing.bonus.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.math.BigDecimal.ZERO;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculator {

    private static final BigDecimal DEBT_RATE = BigDecimal.valueOf(0.25d);

    private final GetQWeekQuery qWeekQuery;
    private final GetBalanceQuery balanceQuery;
    private final GetDriverQuery driverQuery;
    private final GetTransactionQuery transactionQuery;

    public BigDecimal calculate(final Long driverId, final Long qWeekId) {
        var rentObligationResult = ZERO;

        final var previousQWeek = qWeekQuery.getOneBeforeById(qWeekId);
        final var previousQWeekId = previousQWeek.getId();
        final var rentObligation = getRentObligation(driverId, qWeekId);
        final var rentObligationAbs = rentObligation.abs();
        final var previousWeekBalance =
                balanceQuery.getRawContextByDriverIdAndQWeekId(driverId, previousQWeekId).getRequestedWeekBalance().getAmount();
        if (previousWeekBalance.compareTo(ZERO) < 0) {
            final var debt = getDebt(previousWeekBalance, rentObligationAbs);
            rentObligationResult = rentObligationAbs.add(debt);
        } else if (previousWeekBalance.compareTo(ZERO) > 0) {
            rentObligationResult = rentObligationAbs.subtract(previousWeekBalance.abs());
        } else {
            rentObligationResult = rentObligationAbs;
        }
        final var requiredObligation = getRequiredObligation(driverId);
        if (requiredObligation.compareTo(rentObligationResult) > 0) {
            return requiredObligation;
        }
        return rentObligationResult;
    }

    private BigDecimal getDebt(final BigDecimal previousWeekBalance, final BigDecimal rentObligationAbs) {
        final var debtNominal = rentObligationAbs.multiply(DEBT_RATE);
        final var previousWeekBalanceAbs = previousWeekBalance.abs();

        if (debtNominal.compareTo(previousWeekBalanceAbs) > 0) {
            return previousWeekBalanceAbs;
        }
        return debtNominal;
    }

    private BigDecimal getRequiredObligation(final Long driverId) {
        final var driver = driverQuery.getById(driverId);
        if (driver.getHasRequiredObligation()) {
            return driver.getRequiredObligation();
        }
        return ZERO;
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
