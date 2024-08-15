package ee.qrental.insurance.adapter.adapter;

import ee.qrental.insurance.adapter.mapper.InsuranceCaseBalanceAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceRepository;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceAddPort;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
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
