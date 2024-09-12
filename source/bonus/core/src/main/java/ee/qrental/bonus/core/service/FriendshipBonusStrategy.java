package ee.qrental.bonus.core.service;

import static java.time.temporal.ChronoUnit.WEEKS;
import static java.util.Collections.emptyList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FriendshipBonusStrategy extends AbstractBonusStrategy {

  private final GetDriverQuery driverQuery;
  private final GetObligationQuery obligationQuery;
  private final GetQWeekQuery qWeekQuery;
  private static final BigDecimal FRIENDSHIP_RATE = BigDecimal.valueOf(0.05d);
  private static final int WEEKS_AMOUNT_FOR_BONUS_CALCULATION = 24;

  public FriendshipBonusStrategy(
      GetTransactionQuery transactionQuery,
      GetTransactionTypeQuery transactionTypeQuery,
      GetDriverQuery driverQuery,
      GetObligationQuery obligationQuery,
      GetQWeekQuery qWeekQuery) {
    super(transactionQuery, transactionTypeQuery);
    this.driverQuery = driverQuery;
    this.obligationQuery = obligationQuery;
    this.qWeekQuery = qWeekQuery;
  }

  @Override
  public boolean canApply(final BonusProgram bonusProgram) {
    return bonusProgram.getActive()
        && STRATEGY_FRIENDSHIP_REWARD_CODE.equals(bonusProgram.getCode());
  }

  @Override
  public List<TransactionAddRequest> calculateBonus(
      final Obligation obligation, final BigDecimal weekPositiveAmount) {
    // TODO Check why transaction was not created
    final var driverId = obligation.getDriverId();
    if (obligation == null){
        return emptyList();
    }
    final var matchCount = obligation.getMatchCount();
    final var qWeekId = obligation.getQWeekId();
    if (matchCount == 0) {
      return emptyList();
    }
    final var friendships = driverQuery.getFriendships(driverId);
    final var bonusTransactions = new ArrayList<TransactionAddRequest>();
    friendships.forEach(
        friendship -> {
          final var friendId = friendship.getFriendId();
          final var friendObligation = obligationQuery.getByQWeekIdAndDriverId(qWeekId, friendId);
          if (friendObligation.getMatchCount() == 0) {
            System.out.println(
                "No bonus transaction for the friendship, because Friend didn't match an Obligation");
            return;
          }
          final var friend = driverQuery.getById(friendId);
          final var friendCreationDate = friend.getCreatedDate();
          final var currentDate = qWeekQuery.getOneAfterById(qWeekId).getStart();
          final var weeksBetween = WEEKS.between(friendCreationDate, currentDate);
          if (weeksBetween > WEEKS_AMOUNT_FOR_BONUS_CALCULATION) {
            System.out.println(
                "No bonus transaction for the friendship, because it was created more then "
                    + WEEKS_AMOUNT_FOR_BONUS_CALCULATION
                    + " weeks ago");
            return;
          }

          final var rentAndNonLabelFineTransactionsAbsAmount =
              getRentAndNonLabelFineTransactionsAbsAmount(friendId, qWeekId);
          final var discount = rentAndNonLabelFineTransactionsAbsAmount.multiply(FRIENDSHIP_RATE);
          final var transactionTypeId = getBonusTransactionTypeId();
          final var bonusTransaction = new TransactionAddRequest();
          bonusTransaction.setDate(currentDate);
          bonusTransaction.setComment(getComment());
          bonusTransaction.setDriverId(driverId);
          bonusTransaction.setAmount(discount);
          bonusTransaction.setTransactionTypeId(transactionTypeId);
          bonusTransactions.add(bonusTransaction);
        });

    return bonusTransactions;
  }

  @Override
  public String getBonusCode() {
    return STRATEGY_FRIENDSHIP_REWARD_CODE;
  }
}
