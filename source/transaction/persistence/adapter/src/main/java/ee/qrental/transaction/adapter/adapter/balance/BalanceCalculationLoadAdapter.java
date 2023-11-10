package ee.qrental.transaction.adapter.adapter.balance;

import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.adapter.mapper.balance.BalanceCalculationAdapterMapper;
import ee.qrental.transaction.adapter.repository.balance.BalanceCalculationRepository;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.domain.balance.BalanceCalculation;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationLoadAdapter implements BalanceCalculationLoadPort {

  private final BalanceCalculationRepository repository;
  private final BalanceCalculationAdapterMapper mapper;

  @Override
  public LocalDate loadLastCalculatedDate() {
    return repository.getLastCalculationDate();
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
