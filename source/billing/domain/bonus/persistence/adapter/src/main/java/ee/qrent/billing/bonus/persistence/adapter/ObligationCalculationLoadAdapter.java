package ee.qrent.billing.bonus.persistence.adapter;

import ee.qrent.billing.bonus.persistence.mapper.ObligationCalculationAdapterMapper;
import ee.qrent.billing.bonus.persistence.repository.ObligationCalculationRepository;
import ee.qrent.billing.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrent.billing.bonus.domain.ObligationCalculation;
import java.util.List;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class ObligationCalculationLoadAdapter implements ObligationCalculationLoadPort {

  private final ObligationCalculationRepository repository;
  private final ObligationCalculationAdapterMapper mapper;

  @Override
  public Long loadLastCalculatedQWeekId() {
    return repository.getLastCalculationQWeekId();
  }

  @Override
  public List<ObligationCalculation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public ObligationCalculation loadById(Long id) {
    final var entity = repository.getReferenceById(id);
    return mapper.mapToDomain(entity);
  }
}
