package ee.qrent.billing.bonus.core.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import ee.qrent.billing.bonus.domain.BonusProgram;
import ee.qrent.billing.bonus.domain.Obligation;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TwoWeeksPrepaymentBonusStrategy extends AbstractBonusStrategy {
  private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.05);
  private static final BigDecimal BONUS_THRESHOLD_RATE = BigDecimal.valueOf(2d);
  private static final Integer BONUS_THRESHOLD_MATCH_COUNT = 4;

  public TwoWeeksPrepaymentBonusStrategy(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery) {
    super(transactionQuery, transactionTypeQuery);
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive()
        && STRATEGY_2_WEEKS_PREPAYMENT_CODE.equals(bonusProgram.getCode());
  }

  @Override
  public List<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {
    if (obligation.getMatchCount() < BONUS_THRESHOLD_MATCH_COUNT) {

      return emptyList();
    }
    final var driverId = obligation.getDriverId();
    final var qWeekId = obligation.getQWeekId();
    final var rentAndNonLabelFineAmountAbs =
        getRentAndNonLabelFineTransactionsAbsAmount(driverId, qWeekId);
    final var bonusThreshold = rentAndNonLabelFineAmountAbs.multiply(BONUS_THRESHOLD_RATE);
    if (weekPositiveAmount.compareTo(bonusThreshold) < 0) {
      return emptyList();
    }
    final var oneWeekBonusAmount = rentAndNonLabelFineAmountAbs.multiply(DISCOUNT_RATE);
    final var totalDiscount = oneWeekBonusAmount.add(oneWeekBonusAmount);
    final var transactionTypeId = getBonusTransactionTypeId();
    final var bonusTransaction = new TransactionAddRequest();
    bonusTransaction.setDate(LocalDate.now());
    bonusTransaction.setComment(getComment());
    bonusTransaction.setDriverId(driverId);
    bonusTransaction.setAmount(totalDiscount);
    bonusTransaction.setTransactionTypeId(transactionTypeId);

    return singletonList(bonusTransaction);
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_2_WEEKS_PREPAYMENT_CODE;
  }
}
