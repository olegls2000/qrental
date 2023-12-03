package ee.qrental.transaction.api.in.query.balance;

import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import java.math.BigDecimal;
import java.util.List;

public interface GetBalanceQuery {
  List<BalanceResponse> getAll();

  BalanceResponse getById(final Long id);

  BigDecimal getRawBalanceTotalByDriver(final Long driverId);

  BigDecimal getRawFeeTotalByDriver(final Long driverId);

  BigDecimal getFeeByDriver(final Long driverId);

  BalanceResponse getLatestBalanceByDriver(final Long driverId);

  BalanceResponse getDerivedBalanceByDriverAndQWeek(final Long driverId, final Long qWeekId);

  BigDecimal getPeriodAmountByDriverAndQWeek(final Long driverId, final Long qWeekId);

  BigDecimal getPeriodFeeByDriverAndQWeek(final Long driverId, final Long qWeekId);

  @Deprecated
  BalanceResponse getLatestBalanceByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  /**
   * Return Balance Fee Amount for the requested week. If no Balance was not calculated for the
   * requested week, will return ZERO
   */
  BigDecimal getFeeByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  @Deprecated
  BalanceResponse getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  /**
   * Calculate period between versions
   *
   * @deprecated
   *     <p>Use {@link GetBalanceQuery#getRawBalanceTotalByDriverIdAndQWeekId(Long, Long)} instead.
   */
  @Deprecated
  BigDecimal getRawBalanceTotalByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  BigDecimal getRawBalanceTotalByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);
}
