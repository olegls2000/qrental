package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbstractBonusStrategy implements BonusStrategy {

  private final String BONUS_TRANSACTION_TYPE_NAME = "bonus";
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;

  final Long getBonusTransactionTypeId() {
    final var bonusTransactionType =
        getTransactionTypeQuery().getByName(BONUS_TRANSACTION_TYPE_NAME);
    if (bonusTransactionType == null) {
      throw new RuntimeException(
          format(
              "Transaction Type with name: %s, does not exist. "
                  + "Please create it, before Bonus calculation",
              BONUS_TRANSACTION_TYPE_NAME));
    }
    return bonusTransactionType.getId();
  }

  protected final BigDecimal getRentAndNonLabelFineTransactionsAbsAmount(
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

  protected final String getComment() {
    return format("Bonus Transaction for %s Strategy", getBonusCode());
  }
}
