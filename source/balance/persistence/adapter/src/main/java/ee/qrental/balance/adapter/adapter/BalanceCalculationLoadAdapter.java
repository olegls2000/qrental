package ee.qrental.balance.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.balance.adapter.mapper.BalanceCalculationAdapterMapper;
import ee.qrental.balance.adapter.repository.BalanceCalculationRepository;
import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.balance.domain.BalanceCalculation;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationLoadAdapter implements BalanceCalculationLoadPort {

  private final BalanceCalculationRepository repository;
  private final BalanceCalculationAdapterMapper mapper;

  @Override
  public LocalDate loadLastCalculationDate() {
    return loadedOrDefault(repository.getLastCalculationDate(), LocalDate.of(2023, 02, 01));
  }

  private LocalDate loadedOrDefault(final LocalDate loadedDate, final LocalDate defaultDate) {
    return loadedDate == null ? defaultDate : loadedDate;
  }

  @Override
  public List<BalanceCalculation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public BalanceCalculation loadById(final Long id) {
    final var entity = repository.getReferenceById(id);
    return mapper.mapToDomain(entity);
  }
}
