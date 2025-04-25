package ee.qrent.billing.contract.persistence.adapter;


import ee.qrent.billing.contract.persistence.mapper.ContractAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.ContractRepository;
import ee.qrent.billing.contract.api.out.ContractAddPort;
import ee.qrent.billing.contract.api.out.ContractDeletePort;
import ee.qrent.billing.contract.api.out.ContractUpdatePort;
import ee.qrent.billing.contract.domain.Contract;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractPersistenceAdapter
    implements ContractAddPort, ContractUpdatePort, ContractDeletePort {

  private final ContractRepository repository;
  private final ContractAdapterMapper mapper;

  @Override
  public Contract add(final Contract domain) {
    final var savedContractEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedContractEntity);
  }

  @Override
  public Contract update(final Contract domain) {
    repository.save(mapper.mapToEntity(domain));

    return domain;
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
