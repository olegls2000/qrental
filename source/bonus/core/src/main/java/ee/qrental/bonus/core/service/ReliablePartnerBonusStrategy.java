package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
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
import java.util.List;
import java.util.Optional;

public class ReliablePartnerBonusStrategy extends AbstractBonusStrategy {
   private static final Integer BONUS_THRESHOLD_MATCH_COUNT = 4;

  public ReliablePartnerBonusStrategy(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery) {
    super(transactionQuery, transactionTypeQuery);
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive()
        && STRATEGY_RELIABLE_PARTNER_CODE.equals(bonusProgram.getCode());
  }

  @Override
  public Optional<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {
    final var matchCount = obligation.getMatchCount();

    if (matchCount < BONUS_THRESHOLD_MATCH_COUNT) {

      return Optional.empty();
    }
    final var driverId = obligation.getDriverId();
    final var qWeekId = obligation.getQWeekId();

    final var discountRate = getDiscountRate(matchCount);
    final var rentAndNonLabelFineTransactionsAbsAmount =
        getRentAndNonLabelFineTransactionsAbsAmount(driverId, qWeekId);

    final var discount = rentAndNonLabelFineTransactionsAbsAmount.multiply(discountRate);
    final var comment = format("Bonus Transaction for %s Strategy", STRATEGY_RELIABLE_PARTNER_CODE);
    final var transactionTypeId = getBonusTransactionTypeId();
    final var bonusTransaction = new TransactionAddRequest();
    bonusTransaction.setDate(LocalDate.now());
    bonusTransaction.setComment(comment);
    bonusTransaction.setDriverId(driverId);
    bonusTransaction.setAmount(discount);
    bonusTransaction.setTransactionTypeId(transactionTypeId);

    return Optional.of(bonusTransaction);
  }

  private BigDecimal getDiscountRate(final int matchCount) {
    final var occurrences = (double) (matchCount / BONUS_THRESHOLD_MATCH_COUNT);
    if (occurrences < 4) {
      return BigDecimal.valueOf(occurrences).divide(BigDecimal.valueOf(100d));
    }
    return BigDecimal.valueOf(0.05d);
  }

  private BigDecimal getRentAndNonLabelFineTransactionsAbsAmount(
      final Long driverId, final Long qWeekId) {
    return getTransactionQuery().getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(
            transaction ->
                List.of(TRANSACTION_TYPE_NAME_WEEKLY_RENT, TRANSACTION_TYPE_NO_LABEL_FINE)
                    .contains(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_RELIABLE_PARTNER_CODE;
  }
}
