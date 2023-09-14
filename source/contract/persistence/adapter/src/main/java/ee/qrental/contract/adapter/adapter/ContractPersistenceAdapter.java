package ee.qrental.contract.adapter.adapter;


import ee.qrental.contract.adapter.mapper.ContractAdapterMapper;
import ee.qrental.contract.adapter.repository.ContractRepository;
import ee.qrental.contract.api.out.ContractAddPort;
import ee.qrental.contract.api.out.ContractDeletePort;
import ee.qrental.contract.api.out.ContractUpdatePort;
import ee.qrental.contract.domain.Contract;
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
