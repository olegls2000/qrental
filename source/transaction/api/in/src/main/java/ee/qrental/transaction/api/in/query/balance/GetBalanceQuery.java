package ee.qrental.transaction.api.in.query.balance;

import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import java.math.BigDecimal;
import java.util.List;

public interface GetBalanceQuery {
  List<BalanceResponse> getAll();

  BalanceResponse getById(final Long id);

  BalanceResponse getRawBalanceByDriver(final Long driverId);

  BigDecimal getRawBalanceTotalByDriver(final Long driverId);

  BigDecimal getRawRepairmentTotalByDriver(final Long driverId);

  BigDecimal getRawRepairmentTotalByDriverWithQKasko(final Long driverId);

  BigDecimal getRawFeeTotalByDriver(final Long driverId);

  BigDecimal getFeeByDriver(final Long driverId);

  BalanceResponse getLatestCalculatedBalanceByDriver(final Long driverId);

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

  BalanceResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  BalanceResponse getLatestByDriverId(final Long driverId);

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

  Long getCountByDriver(final Long driverId);
}
