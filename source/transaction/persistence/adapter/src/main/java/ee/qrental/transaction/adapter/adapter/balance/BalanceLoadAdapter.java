package ee.qrental.transaction.adapter.adapter.balance;

import static ee.qrental.common.core.utils.QTimeUtils.BALANCE_START_CALCULATION_DATE;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.transaction.adapter.mapper.balance.BalanceAdapterMapper;
import ee.qrental.transaction.adapter.repository.balance.BalanceRepository;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.domain.balance.Balance;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceLoadAdapter implements BalanceLoadPort {

  private final BalanceRepository repository;
  private final BalanceAdapterMapper mapper;

  @Override
  public List<Balance> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Balance loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Balance loadByDriverIdAndYearAndWeekNumberOrDefault(
      final Long driverId, final Integer year, final Integer weekNumber) {
    if (year == 2023 && weekNumber < 1) {
      final var defaultBalance = Balance.builder().amount(ZERO).fee(ZERO).build();

      return defaultBalance;
    }
    return mapper.mapToDomain(
        repository.getByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber));
  }

  @Override
  public Balance loadLatestByDriver(final Long driverId) {
    final var latestBalance = repository.getLatestByDriverId(driverId);

    return mapper.mapToDomain(latestBalance);
  }

  @Override
  public Balance loadLatestByIdAndYearAndWeekNumber(
          final Long driverId, final Integer year, final Integer weekNumber) {
    final var latestBalance = repository.getLatestByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber);

    return mapper.mapToDomain(latestBalance);
  }

  @Override
  public LocalDate loadLatestCalculatedDateOrDefault(){
    final var latestBalance = repository.getLatest();
    if(latestBalance == null) {
      final var defaultLatestCalculatedDate = BALANCE_START_CALCULATION_DATE;

      return defaultLatestCalculatedDate;
    }
    final var latestBalanceYear = latestBalance.getYear();
    final var latestBalanceWeekNumber = latestBalance.getWeekNumber();
    final var latestBalanceSunday = QTimeUtils.getLastDayOfWeekInYear(latestBalanceYear, latestBalanceWeekNumber);

    return  latestBalanceSunday;
  }
}
