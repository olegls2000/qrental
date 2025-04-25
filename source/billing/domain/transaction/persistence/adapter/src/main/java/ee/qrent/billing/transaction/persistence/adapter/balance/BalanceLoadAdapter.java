package ee.qrent.billing.transaction.persistence.adapter.balance;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.transaction.persistence.mapper.balance.BalanceAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceRepository;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.domain.balance.Balance;
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
      final var defaultBalance =
          Balance.builder().feeAbleAmount(ZERO).nonFeeAbleAmount(ZERO).feeAmount(ZERO).build();

      return defaultBalance;
    }
    return mapper.mapToDomain(
        repository.getByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber));
  }

  @Override
  public Balance loadByDriverIdAndQWeekIdAndDerived(
      final Long driverId, final Long qWeekId, final boolean derived) {
    return mapper.mapToDomain(
        repository.getByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, derived));
  }

  @Override
  public Balance loadLatestByDriver(final Long driverId) {
    final var latestBalance = repository.getLatestByDriverId(driverId);

    return mapper.mapToDomain(latestBalance);
  }

  @Override
  public Long loadCountByDriver(Long driverId) {
    return repository.getCountByDriverId(driverId);
  }

  @Override
  public Balance loadLatestByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    final var latestBalance =
        repository.getLatestByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber);

    return mapper.mapToDomain(latestBalance);
  }

  @Override
  public Balance loadLatest() {
    final var latestBalance = repository.getLatest();

    return mapper.mapToDomain(latestBalance);
  }

  @Override
  public Balance loadLatestByDriverId(Long driverId) {
    final var latestBalance = repository.getLatestByDriverId(driverId);

    return mapper.mapToDomain(latestBalance);
  }
}
