package ee.qrent.billing.transaction.persistence.adapter.type;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.transaction.persistence.mapper.type.TransactionTypeAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.TransactionTypeRepository;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrent.billing.transaction.domain.type.TransactionType;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeLoadAdapter implements TransactionTypeLoadPort {

  private final TransactionTypeRepository repository;
  private final TransactionTypeAdapterMapper mapper;

  @Override
  public List<TransactionType> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public TransactionType loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public TransactionType loadByName(String name) {
    return mapper.mapToDomain(repository.findByName(name));
  }

  @Override
  public List<TransactionType> loadByKindCodesIn(final List<String> kindCodes) {
    return repository.findAllByKindCodesIn(kindCodes).stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public List<TransactionType> loadByNameIn(List<String> names) {
    return repository.findAllByNameIn(names).stream().map(mapper::mapToDomain).toList();
  }
}
