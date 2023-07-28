package ee.qrental.transaction.adapter.adapter.balance;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.adapter.mapper.balance.BalanceAdapterMapper;
import ee.qrental.transaction.adapter.repository.balance.BalanceRepository;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.domain.balance.Balance;
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
  public Balance loadByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    if (year == 2023 && weekNumber < 1) {
      return Balance.builder().amount(ZERO).fee(ZERO).build();
    }
    return mapper.mapToDomain(
        repository.getByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber));
  }

  @Override
  public Balance loadLatestByDriver(final Long driverId) {
    final var latestBalance = repository.getLatestByDriverId(driverId);

    return mapper.mapToDomain(latestBalance);
  }
}
