package ee.qrental.contract.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.contract.adapter.mapper.ContractAdapterMapper;
import ee.qrental.contract.adapter.repository.ContractRepository;
import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.domain.Contract;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractLoadAdapter implements ContractLoadPort {

  private final ContractRepository repository;
  private final ContractAdapterMapper mapper;

  @Override
  public List<Contract> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Contract loadById(final Long id) {
   final var entity =  repository.getReferenceById(id);

   return mapper.mapToDomain(entity);
  }

  @Override
  public Contract loadByNumber(final String number) {
    final var entity =  repository.getByNumber(number);
   
    return mapper.mapToDomain(entity);
  }
}
