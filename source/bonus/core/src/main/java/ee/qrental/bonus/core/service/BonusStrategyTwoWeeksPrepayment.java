package ee.qrental.bonus.core.service;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class BonusStrategyTwoWeeksPrepayment extends AbstractBonusStrategy {
  private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.05);

  public BonusStrategyTwoWeeksPrepayment(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery) {
    super(transactionQuery, transactionTypeQuery);
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    if (bonusProgram.getActive()
        && STRATEGY_2_WEEKS_PREPAYMENT_CODE.equals(bonusProgram.getCode())) {
      return true;
    }
    return false;
  }

  @Override
  public Optional<TransactionAddRequest> calculateBonus(final Obligation obligation) {
    if (obligation.getMatchCount() < 4) {
      return Optional.empty();
    }
    final var driverId = obligation.getDriverId();
    final var qWeekId = obligation.getQWeekId();
    final var positiveAmount = getPositiveAmount(driverId, qWeekId);
    final var bonusThreshold = obligation.getObligationAmount().multiply(BigDecimal.valueOf(2l));
    if (positiveAmount.compareTo(bonusThreshold) < 0) {
      return Optional.empty();
    }
    final var weekObligation = obligation.getObligationAmount();
    final var bonusAmount = weekObligation.multiply(DISCOUNT_RATE);
    final var comment =
        format("Bonus Transaction for %s Strategy", STRATEGY_2_WEEKS_PREPAYMENT_CODE);
    final var transactionTypeId = getBonusTransactionTypeId();
    final var bonusTransaction = new TransactionAddRequest();
    bonusTransaction.setDate(LocalDate.now());
    bonusTransaction.setComment(comment);
    bonusTransaction.setDriverId(driverId);
    bonusTransaction.setAmount(bonusAmount);
    bonusTransaction.setTransactionTypeId(transactionTypeId);

    return Optional.of(bonusTransaction);
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_2_WEEKS_PREPAYMENT_CODE;
  }

  private BigDecimal getPositiveAmount(final Long driverId, final Long qWeekId) {
    final var positiveAmount =
        getTransactionQuery().getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            // TODO move "P" to API
            .filter(tr -> "P".equals(tr.getKind()))
            .map(TransactionResponse::getRealAmount)
            .reduce(ZERO, BigDecimal::add);

    return positiveAmount;
  }
}
