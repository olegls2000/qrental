package ee.qrent.billing.transaction.domain.balance;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BalanceCalculationResult {
  private Balance balance;
  private Set<Long> transactionIds;
}
