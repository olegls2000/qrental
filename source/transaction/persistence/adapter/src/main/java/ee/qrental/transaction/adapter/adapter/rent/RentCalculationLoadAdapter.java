package ee.qrental.transaction.adapter.adapter.rent;

import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.adapter.mapper.rent.RentCalculationAdapterMapper;
import ee.qrental.transaction.adapter.repository.rent.RentCalculationRepository;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import ee.qrental.transaction.domain.rent.RentCalculation;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationLoadAdapter implements RentCalculationLoadPort {

  private final RentCalculationRepository repository;
  private final RentCalculationAdapterMapper mapper;

  @Override
  public Long loadLastCalculationQWeekId() {
    return repository.getLastCalculationQWeekId();
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
