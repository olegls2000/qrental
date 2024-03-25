package ee.qrental.bonus.core.service;

import static java.lang.String.format;

import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbstractBonusStrategy implements BonusStrategy {

    private final String BONUS_TRANSACTION_TYPE_NAME = "bonus";
    private final GetTransactionQuery transactionQuery;
    private final GetTransactionTypeQuery transactionTypeQuery;

    final Long getBonusTransactionTypeId() {
        final var bonusTransactionType = getTransactionTypeQuery().getByName(BONUS_TRANSACTION_TYPE_NAME);
        if (bonusTransactionType == null) {
            throw new RuntimeException(
                    format(
                            "Transaction Type with name: %s, does not exist. "
                                    + "Please create it, before Bonus calculation",
                            BONUS_TRANSACTION_TYPE_NAME));
        }
        return bonusTransactionType.getId();
    }
}
