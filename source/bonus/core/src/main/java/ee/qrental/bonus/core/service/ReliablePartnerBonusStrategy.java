package ee.qrental.bonus.core.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReliablePartnerBonusStrategy extends AbstractBonusStrategy {
  private static final Integer BONUS_THRESHOLD_MATCH_COUNT = 4;
  private GetQWeekQuery qWeekQuery;

  public ReliablePartnerBonusStrategy(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetQWeekQuery qWeekQuery) {
    super(transactionQuery, transactionTypeQuery);
    this.qWeekQuery = qWeekQuery;
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive()
        && STRATEGY_RELIABLE_PARTNER_CODE.equals(bonusProgram.getCode());
  }

  @Override
  public List<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {
    final var matchCount = obligation.getMatchCount();

    if (matchCount < BONUS_THRESHOLD_MATCH_COUNT) {

      return emptyList();
    }
    final var driverId = obligation.getDriverId();
    final var qWeekId = obligation.getQWeekId();

    final var discountRate = getDiscountRate(matchCount);
    final var rentAndNonLabelFineTransactionsAbsAmount =
        getRentAndNonLabelFineTransactionsAbsAmount(driverId, qWeekId);

    final var discount = rentAndNonLabelFineTransactionsAbsAmount.multiply(discountRate);
    final var bonusTransaction = new TransactionAddRequest();
    final var transactionDate = qWeekQuery.getOneAfterById(qWeekId).getStart();
    bonusTransaction.setDate(transactionDate);
    bonusTransaction.setComment(getComment());
    bonusTransaction.setDriverId(driverId);
    bonusTransaction.setAmount(discount);
    bonusTransaction.setTransactionTypeId(getBonusTransactionTypeId());

    return asList(bonusTransaction);
  }

  private BigDecimal getDiscountRate(final int matchCount) {
    final var occurrences = (double) (matchCount / BONUS_THRESHOLD_MATCH_COUNT);
    if (occurrences < 4) {
      return BigDecimal.valueOf(occurrences).divide(BigDecimal.valueOf(100d));
    }
    return BigDecimal.valueOf(0.05d);
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_RELIABLE_PARTNER_CODE;
  }
}
