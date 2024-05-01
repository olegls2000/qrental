package ee.qrental.transaction.api.in.query.balance;

import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import java.math.BigDecimal;
import java.util.List;

public interface GetBalanceQuery {
  List<BalanceResponse> getAll();

  BalanceResponse getById(final Long id);

  BalanceResponse getRawByDriverIdAndQWeekId(Long driverId, Long qWeekId);

  BalanceResponse getRawCurrentByDriver(final Long driverId);

  BigDecimal getAmountTotalByDriver(final Long driverId);

  BigDecimal getAmountRepairmentByDriver(final Long driverId);

  BigDecimal getAmountRepairmentByDriverWithQKasko(final Long driverId);

  BigDecimal getAmountFeeByDriver(final Long driverId);

  BigDecimal getFeeByDriver(final Long driverId);

  BalanceResponse getLatestByDriver(final Long driverId);

  BigDecimal getPeriodAmountByDriverAndQWeek(final Long driverId, final Long qWeekId);

  BigDecimal getPeriodFeeByDriverAndQWeek(final Long driverId, final Long qWeekId);

  /**
   * Return Balance Fee Amount for the requested week. If no Balance was not calculated for the
   * requested week, will return ZERO
   */
  BigDecimal getFeeByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  BalanceResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

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
