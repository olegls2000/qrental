package ee.qrental.balance.api.out;

import ee.qrental.balance.domain.Balance;
import ee.qrental.common.core.out.port.LoadPort;

public interface BalanceLoadPort extends LoadPort<Balance> {
  Balance loadByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  Balance loadLatestByDriver(final Long driverId);
}
