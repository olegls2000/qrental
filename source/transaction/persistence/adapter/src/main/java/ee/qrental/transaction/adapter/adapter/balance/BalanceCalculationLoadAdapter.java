package ee.qrental.transaction.adapter.adapter.balance;

import static ee.qrental.common.core.utils.QTimeUtils.BALANCE_START_CALCULATION_DATE;
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
  public LocalDate loadLastCalculationDate() {
    return loadedOrDefault(repository.getLastCalculationDate(), BALANCE_START_CALCULATION_DATE);
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
