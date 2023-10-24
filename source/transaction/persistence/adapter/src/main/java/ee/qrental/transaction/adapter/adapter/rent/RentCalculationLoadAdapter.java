package ee.qrental.transaction.adapter.adapter.rent;

import static ee.qrental.common.core.utils.QTimeUtils.RENT_START_CALCULATION_DATE;
import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.adapter.mapper.rent.RentCalculationAdapterMapper;
import ee.qrental.transaction.adapter.repository.rent.RentCalculationRepository;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import ee.qrental.transaction.domain.rent.RentCalculation;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationLoadAdapter implements RentCalculationLoadPort {

  private final RentCalculationRepository repository;
  private final RentCalculationAdapterMapper mapper;

  @Override
  public LocalDate loadLastCalculationDate() {
    return loadedOrDefault(repository.getLastCalculationDate(), RENT_START_CALCULATION_DATE);
  }

  private LocalDate loadedOrDefault(final LocalDate loadedDate, final LocalDate defaultDate) {
    return loadedDate == null ? defaultDate : loadedDate;
  }

  @Override
  public List<RentCalculation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public RentCalculation loadById(final Long id) {
    final var entity = repository.getReferenceById(id);
    return mapper.mapToDomain(entity);
  }
}
