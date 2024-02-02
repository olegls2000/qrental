package ee.qrental.bonus.core.service;

import ee.qrental.bonus.api.out.BonusProgramLoadPort;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusTransactionCreator {

  public static final Integer MINIMUM_MATCHED_WEEKS = 4;

  private final BonusProgramLoadPort bonusProgramLoadPort;

  public List<TransactionAddRequest> getBonusTransactions(final Obligation obligation) {
    final var bonusPrograms = bonusProgramLoadPort.loadAll();
    final var matchCount = obligation.getMatchCount();
    if (MINIMUM_MATCHED_WEEKS < 4) {
      System.out.printf(
          "No bonuses Transaction allowed for driver with id: %d, in week id: %d%n",
          obligation.getDriverId(), obligation.getQWeekId());
      return Collections.emptyList();
    }
    return null;
  }
}
