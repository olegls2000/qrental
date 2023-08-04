package ee.qrental.transaction.api.out.balance;


import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.transaction.domain.balance.Balance;

import java.time.LocalDate;

public interface BalanceLoadPort extends LoadPort<Balance> {
  Balance loadByDriverIdAndYearAndWeekNumberOrDefault(
      final Long driverId, final Integer year, final Integer weekNumber);

  Balance loadLatestByDriver(final Long driverId);

  LocalDate loadLatestCalculatedDateOrDefault();

  LocalDate loadLatestCalculatedDateOrDefaultByDriverId(final Long driverId);

  Balance loadLatestByIdAndYearAndWeekNumber(final Long driverId, final Integer year, final Integer weekNumber);
}
