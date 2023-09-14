package ee.qrental.contract.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.contract.adapter.mapper.ContractAdapterMapper;
import ee.qrental.contract.adapter.repository.ContractRepository;
import ee.qrental.contract.api.out.ContractAddPort;
import ee.qrental.contract.api.out.ContractDeletePort;
import ee.qrental.contract.api.out.ContractUpdatePort;
import ee.qrental.invoice.api.out.InvoiceAddPort;
import ee.qrental.invoice.api.out.InvoiceDeletePort;
import ee.qrental.invoice.api.out.InvoiceUpdatePort;
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
    final var savedContractItemEntities =
        domain.getItems().stream()
            .map(item -> mapper.mapToItemEntity(item))
            .peek(entityItem -> entityItem.setContract(savedContractEntity))
            .map(itemRepository::save)
            .collect(toList());
    savedContractEntity.setItems(savedContractItemEntities);

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
