package ee.qrental.insurance.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.insurance.adapter.mapper.InsuranceCalculationAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCalculationRepository;
import ee.qrental.insurance.api.out.InsuranceCalculationLoadPort;
import ee.qrental.insurance.domain.InsuranceCalculation;
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
