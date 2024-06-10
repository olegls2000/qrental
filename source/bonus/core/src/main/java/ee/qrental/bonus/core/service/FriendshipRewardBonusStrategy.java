package ee.qrental.bonus.core.service;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;

import java.math.BigDecimal;
import java.util.Optional;

public class FriendshipRewardBonusStrategy extends AbstractBonusStrategy {
  public FriendshipRewardBonusStrategy(
      GetTransactionQuery transactionQuery, GetTransactionTypeQuery transactionTypeQuery) {
    super(transactionQuery, transactionTypeQuery);
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive()
        && STRATEGY_FRIENDSHIP_REWARD_CODE.equals(bonusProgram.getCode());
  }

  @Override
  public Optional<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {
    final var driverId = obligation.getDriverId();



    return Optional.empty();
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_FRIENDSHIP_REWARD_CODE;
  }
}
