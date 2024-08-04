package ee.qrental.insurance.adapter.adapter;

import ee.qrental.insurance.adapter.mapper.InsuranceCaseAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCaseRepository;
import ee.qrental.insurance.api.out.InsuranceCaseAddPort;
import ee.qrental.insurance.api.out.InsuranceCaseDeletePort;
import ee.qrental.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrental.insurance.domain.InsuranceCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCasePersistenceAdapter
    implements InsuranceCaseAddPort, InsuranceCaseUpdatePort, InsuranceCaseDeletePort {

  private final InsuranceCaseRepository repository;
  private final InsuranceCaseAdapterMapper mapper;

  @Override
  public InsuranceCase add(final InsuranceCase domain) {
    final var savedEntity = repository.save(mapper.mapToEntity(domain));
    return mapper.mapToDomain(savedEntity);
  }

  @Override
  public InsuranceCase update(final InsuranceCase domain) {
    repository.save(mapper.mapToEntity(domain));

    return domain;
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
