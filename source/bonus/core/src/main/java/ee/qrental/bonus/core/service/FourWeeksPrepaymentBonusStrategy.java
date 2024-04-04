package ee.qrental.bonus.core.service;

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

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

public class FourWeeksPrepaymentBonusStrategy extends AbstractBonusStrategy {
  private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);
  private static final BigDecimal BONUS_THRESHOLD_RATE = BigDecimal.valueOf(4d);
  private static final BigDecimal BONUS_TRANSACTION_COUNT = BigDecimal.valueOf(4d);
  private static final Integer BONUS_THRESHOLD_MATCH_COUNT = 4;

  public FourWeeksPrepaymentBonusStrategy(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery) {
    super(transactionQuery, transactionTypeQuery);
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive()
        && STRATEGY_4_WEEKS_PREPAYMENT_CODE.equals(bonusProgram.getCode());
  }

  @Override
  public Optional<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {
    if (obligation.getMatchCount() < BONUS_THRESHOLD_MATCH_COUNT) {

      return Optional.empty();
    }
    final var driverId = obligation.getDriverId();
    final var qWeekId = obligation.getQWeekId();
    final var rentAndNonLabelFineAmountAbs =
        getRentAndNonLabelFineTransactionsAbsAmount(driverId, qWeekId);
    final var bonusThreshold = rentAndNonLabelFineAmountAbs.multiply(BONUS_THRESHOLD_RATE);
    if (weekPositiveAmount.compareTo(bonusThreshold) < 0) {
      return Optional.empty();
    }
    final var oneWeekBonusAmount = rentAndNonLabelFineAmountAbs.multiply(DISCOUNT_RATE);
    final var totalDiscount = oneWeekBonusAmount.multiply(BONUS_TRANSACTION_COUNT);
    final var comment =
        format("Bonus Transaction for %s Strategy", STRATEGY_4_WEEKS_PREPAYMENT_CODE);
    final var transactionTypeId = getBonusTransactionTypeId();
    final var bonusTransaction = new TransactionAddRequest();
    bonusTransaction.setDate(LocalDate.now());
    bonusTransaction.setComment(comment);
    bonusTransaction.setDriverId(driverId);
    bonusTransaction.setAmount(totalDiscount);
    bonusTransaction.setTransactionTypeId(transactionTypeId);

    return Optional.of(bonusTransaction);
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
    return STRATEGY_4_WEEKS_PREPAYMENT_CODE;
  }
}
