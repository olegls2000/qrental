package ee.qrental.insurance.adapter.adapter;

import ee.qrental.insurance.adapter.mapper.InsuranceCalculationAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCalculationRepository;
import ee.qrental.insurance.api.out.InsuranceCalculationAddPort;
import ee.qrental.insurance.domain.InsuranceCalculation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationPersistenceAdapter implements InsuranceCalculationAddPort {

  private final InsuranceCalculationRepository repository;
  private final InsuranceCalculationAdapterMapper mapper;

  @Override
  public InsuranceCalculation add(final InsuranceCalculation domain) {
    final var savedEntity = repository.save(mapper.mapToEntity(domain));
    return mapper.mapToDomain(savedEntity);
  }
}
