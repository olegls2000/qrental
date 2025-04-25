package ee.qrent.billing.insurance.persistence.adapter;

import ee.qrent.billing.insurance.persistence.mapper.InsuranceCaseBalanceAdapterMapper;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseBalanceRepository;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceAddPort;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalancePersistenceAdapter implements InsuranceCaseBalanceAddPort {

  private final InsuranceCaseBalanceRepository repository;
  private final InsuranceCaseBalanceAdapterMapper mapper;

  @Override
  public InsuranceCaseBalance add(final InsuranceCaseBalance domain) {
    final var savedEntity = repository.save(mapper.mapToEntity(domain));
    return mapper.mapToDomain(savedEntity);
  }
}
