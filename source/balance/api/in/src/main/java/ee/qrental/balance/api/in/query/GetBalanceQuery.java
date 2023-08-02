package ee.qrental.balance.api.in.query;

import ee.qrental.balance.api.in.response.BalanceResponse;
import java.math.BigDecimal;
import java.util.List;

public interface GetBalanceQuery {
  List<BalanceResponse> getAll();

  BalanceResponse getById(final Long id);

  BalanceResponse getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  BigDecimal getRawBalanceTotalByDriver(final Long driverId);

  BigDecimal getRawBalanceTotalByDriverIdAndYearAndWeekNumber(final Long driverId, final Integer year, final Integer weekNumber);

  BigDecimal getFeeByDriver(final Long driverId);

  BalanceResponse getLatestBalanceByDriver(final Long driverId);
  BalanceResponse getLatestBalanceByDriverIdAndYearAndWeekNumber(
          final Long driverId,
          final Integer year,
          final Integer weekNumber);
}
