package ee.qrental.transaction.api.out.balance;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.transaction.domain.balance.Balance;

public interface BalanceLoadPort extends LoadPort<Balance> {
  Balance loadByDriverIdAndYearAndWeekNumberOrDefault(
      final Long driverId, final Integer year, final Integer weekNumber);

  Balance loadByDriverIdAndQWeekIdAndDerived(
      final Long driverId, final Long qWeekId, final boolean derived);

  Balance loadLatestByDriver(final Long driverId);

  Long loadCountByDriver(final Long driverId);

  Balance loadLatest();

  Balance loadLatestByDriverId(final Long driverId);

  Balance loadLatestByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);
}
