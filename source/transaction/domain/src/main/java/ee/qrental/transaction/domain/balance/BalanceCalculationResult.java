package ee.qrental.transaction.domain.balance;

import java.util.Set;

import ee.qrental.transaction.domain.balance.Balance;
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
