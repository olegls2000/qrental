package ee.qrental.transaction.api.in.query.balance;


import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import ee.qrental.transaction.api.in.response.balance.BalanceAmountWithDriverResponse;

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

  List<BalanceAmountWithDriverResponse> getAllBalanceTotalsWithDriver();
}
