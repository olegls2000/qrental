package ee.qrent.billing.insurance.persistence.adapter;

import ee.qrent.billing.insurance.persistence.mapper.InsuranceCaseAdapterMapper;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseRepository;
import ee.qrent.billing.insurance.api.out.InsuranceCaseAddPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCasePersistenceAdapter
    implements InsuranceCaseAddPort, InsuranceCaseUpdatePort {

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
}
