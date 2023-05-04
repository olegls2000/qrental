package ee.qrental.balance.adapter.adapter;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrental.balance.adapter.mapper.BalanceAdapterMapper;
import ee.qrental.balance.adapter.repository.BalanceRepository;
import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.balance.domain.Balance;

import java.math.BigDecimal;
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
    if (year == 2023 && weekNumber < 10) {
      return Balance.builder()
              .amount(ZERO)
              .fee(ZERO).build();
    }
    return mapper.mapToDomain(
        repository.getByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber));
  }
}
