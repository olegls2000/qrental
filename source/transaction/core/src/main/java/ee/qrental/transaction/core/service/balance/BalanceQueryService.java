package ee.qrental.transaction.core.service.balance;

import static ee.qrental.transaction.core.utils.FeeUtils.FEE_WEEKLY_INTEREST;
import static ee.qrental.transaction.core.utils.FeeUtils.getWeekFeeInterest;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.util.stream.Collectors.toList;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.common.core.utils.QWeekIterator;
import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.response.balance.BalanceAmountWithDriverResponse;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceQueryService implements GetBalanceQuery {

  private final GetDriverQuery driverQuery;
  private final GetConstantQuery constantQuery;
  private final BalanceLoadPort balanceLoadPort;
  private final TransactionLoadPort transactionLoadPort;
  private final BalanceResponseMapper balanceResponseMapper;

  @Override
  public List<BalanceAmountWithDriverResponse> getAllBalanceTotalsWithDriver() {
    return driverQuery.getAll().stream()
        .map(this::getBalanceByDriverId)
        .sorted(this::getBalanceComparator)
        .collect(toList());
  }

  @Override
  public BalanceResponse getLatestBalanceByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    final var latestBalance =
        balanceLoadPort.loadLatestByIdAndYearAndWeekNumber(driverId, year, weekNumber);

    return balanceResponseMapper.toResponse(latestBalance);
  }

  private int getBalanceComparator(
      BalanceAmountWithDriverResponse bal1, BalanceAmountWithDriverResponse bal2) {
    final var callSign1 = bal1.getCallSign();
    final var callSign2 = bal2.getCallSign();
    if (callSign1 != null && callSign2 != null) {
      return callSign1 - callSign2;
    }
    return 0;
  }

  private BalanceAmountWithDriverResponse getBalanceByDriverId(final DriverResponse driver) {
    final var driverId = driver.getId();
    final var total = calculateTotal(transactionLoadPort.loadAllByDriverId(driverId));

    return BalanceAmountWithDriverResponse.builder()
        .driverId(driverId)
        .total(total)
        .callSign(driver.getCallSign())
        .firstName(driver.getFirstName())
        .lastName(driver.getLastName())
        .phone(driver.getPhone())
        .build();
  }

  private BigDecimal calculateTotal(final List<Transaction> transactions) {
    return transactions.stream()
        .map(Transaction::getRealAmount)
        .reduce(ZERO, BigDecimal::add);
  }

  @Override
  public List<BalanceResponse> getAll() {
    return balanceLoadPort.loadAll().stream()
        .map(balanceResponseMapper::toResponse)
        .collect(toList());
  }

  @Override
  public BalanceResponse getById(final Long id) {
    return balanceResponseMapper.toResponse(balanceLoadPort.loadById(id));
  }

  @Override
  public BalanceResponse getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    return balanceResponseMapper.toResponse(
        balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, year, weekNumber));
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var balanceSum = latestBalance != null ? latestBalance.getAmount() : ZERO;
    final var rawSum = getRawTransactionsSum(latestBalance, driverId);

    return balanceSum.add(rawSum);
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    final var balance =
        balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, year, weekNumber);
    if (balance != null) {

      return balance.getAmount();
    }

    final var endDate = QTimeUtils.getLastDayOfWeekInYear(year, weekNumber);
    final var filterBuilder = PeriodAndDriverFilter.builder().dateEnd(endDate).driverId(driverId);
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    if (latestBalance == null) {
      final var startDate = QTimeUtils.getFirstDayOfYear(year);
      final var filter = filterBuilder.dateStart(startDate).build();

      return getSumOfTransactionByFilter(filter);
    }
    final var latestCalculatedWeek = latestBalance.getWeekNumber();
    final var earliestNotCalculatedWeek = latestCalculatedWeek + 1;
    final var startDate = QTimeUtils.getFirstDayOfWeekInYear(year, earliestNotCalculatedWeek);
    final var filter = filterBuilder.dateStart(startDate).build();
    final var fromBalance = latestBalance.getAmount();
    final var rawTransactionSum = getSumOfTransactionByFilter(filter);

      return fromBalance.add(rawTransactionSum);
  }

  @Override
  public BigDecimal getRawFeeTotalByDriver(final Long driverId) {
    var rawFeeTotal = ZERO;
    var rawTotal = ZERO;
    var startDate = LocalDate.of(2023, 1, 1);

    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    if (latestBalance != null) {
      final var feeFromBalance = latestBalance.getFee();
      rawFeeTotal = rawFeeTotal.add(feeFromBalance);

      final var balanceAmount = latestBalance.getAmount();
      rawTotal = rawTotal.add(balanceAmount);

      final var year = latestBalance.getYear();
      final var latestCalculatedWeek = latestBalance.getWeekNumber();
      final var earliestNotCalculatedWeek = latestCalculatedWeek + 1;
      startDate = QTimeUtils.getFirstDayOfWeekInYear(year, earliestNotCalculatedWeek);
    }

    final var endDate = LocalDate.now();
    final var weekIterator = new QWeekIterator(startDate, endDate);
    final var weeklyInterestConstant = constantQuery.getByName(FEE_WEEKLY_INTEREST);
    final var weeklyInterest = getWeekFeeInterest(weeklyInterestConstant);
    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      final var weekTransactions =
          transactionLoadPort.loadAllByDriverIdAndBetweenDays(driverId, week.start(), week.end());
      final var weekTotal =
          weekTransactions.stream()
              .map(Transaction::getRealAmount)
              .reduce(ZERO, BigDecimal::add);
      rawTotal = rawTotal.add(weekTotal);
      if (rawTotal.compareTo(ZERO) < 0) {
        final var weekFee = rawTotal.multiply(weeklyInterest);
        rawFeeTotal = rawFeeTotal.add(weekFee);
        if (rawFeeTotal.compareTo(rawTotal) <= 0) {
          rawFeeTotal = rawTotal;
        }
      }
    }

    return rawFeeTotal;
  }

  private BigDecimal getRawTransactionsSum(final Balance latestBalance, final Long driverId) {
    final var earliestNotCalculatedDate = getEarliestNotCalculatedDate(latestBalance);
    final var rawTransactionFilter =
        PeriodAndDriverFilter.builder()
            .driverId(driverId)
            .dateStart(earliestNotCalculatedDate)
            .dateEnd(LocalDate.now())
            .build();

    return getSumOfTransactionByFilter(rawTransactionFilter);
  }

  private LocalDate getEarliestNotCalculatedDate(final Balance latestBalance) {
    if (latestBalance == null) {
      return LocalDate.now().with(firstDayOfYear());
    }

    final var latestBalanceYear = latestBalance.getYear();
    final var latestBalanceWeek = latestBalance.getWeekNumber();
    final var latestCalculatedDate =
        QTimeUtils.getLastDayOfWeekInYear(latestBalanceYear, latestBalanceWeek);

    return latestCalculatedDate.plusDays(1L);
  }

  @Override
  public BigDecimal getFeeByDriver(Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    return latestBalance != null ? latestBalance.getFee() : ZERO;
  }

  @Override
  public BalanceResponse getLatestBalanceByDriver(Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    return balanceResponseMapper.toResponse(latestBalance);
  }

  @Override
  public LocalDate getLatestCalculatedDate() {
    return balanceLoadPort.loadLatestCalculatedDateOrDefault();
  }

  private BigDecimal getSumOfTransactionByFilter(final PeriodAndDriverFilter filter) {
    final var rawTransactions =
        transactionLoadPort.loadAllByDriverIdAndBetweenDays(
            filter.getDriverId(), filter.getDateStart(), filter.getDateEnd());
    final var result =
        rawTransactions.stream()
            .map(Transaction::getRealAmount)
            .reduce(ZERO, BigDecimal::add);

    return result;
  }
}
