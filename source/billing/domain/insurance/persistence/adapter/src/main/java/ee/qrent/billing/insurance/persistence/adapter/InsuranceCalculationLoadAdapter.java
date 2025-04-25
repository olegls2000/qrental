package ee.qrent.billing.insurance.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.insurance.persistence.mapper.InsuranceCalculationAdapterMapper;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCalculationRepository;
import ee.qrent.billing.insurance.api.out.InsuranceCalculationLoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationLoadAdapter implements InsuranceCalculationLoadPort {
  private final InsuranceCalculationRepository repository;
  private final InsuranceCalculationAdapterMapper mapper;

  @Override
  public Long loadLastCalculatedQWeekId() {
    return repository.getLastCalculatedQWeekId();
  }

  @Override
  public List<InsuranceCalculation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public InsuranceCalculation loadById(Long id) {
    final var entity = repository.getReferenceById(id);
    return mapper.mapToDomain(entity);
  }
}
