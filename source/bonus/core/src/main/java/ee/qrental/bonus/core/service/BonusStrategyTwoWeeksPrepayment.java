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
  private static final BigDecimal BONUS_THRESHOLD_RATE = BigDecimal.valueOf(2d);

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
  public Optional<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal rawBalanceAmount) {
    if (obligation.getMatchCount() < 4) {
      return Optional.empty();
    }
    final var driverId = obligation.getDriverId();
    final var obligationAbs = obligation.getObligationAmount().abs();
    final var bonusThreshold = obligationAbs.multiply(BONUS_THRESHOLD_RATE);
    if (rawBalanceAmount.compareTo(bonusThreshold) < 0) {
      return Optional.empty();
    }
    final var bonusAmount = obligationAbs.multiply(DISCOUNT_RATE);
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
}
