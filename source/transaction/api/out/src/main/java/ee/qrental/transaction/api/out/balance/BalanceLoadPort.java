package ee.qrental.transaction.api.out.balance;


import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.transaction.domain.balance.Balance;

public interface BalanceLoadPort extends LoadPort<Balance> {
  Balance loadByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  Balance loadLatestByDriver(final Long driverId);
}
