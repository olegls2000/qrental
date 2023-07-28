package ee.qrental.transaction.adapter.adapter.type;

import ee.qrental.transaction.adapter.mapper.type.TransactionTypeAdapterMapper;
import ee.qrental.transaction.adapter.repository.TransactionTypeRepository;
import ee.qrental.transaction.api.out.type.TransactionTypeAddPort;
import ee.qrental.transaction.api.out.type.TransactionTypeDeletePort;
import ee.qrental.transaction.api.out.type.TransactionTypeUpdatePort;
import ee.qrental.transaction.domain.type.TransactionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypePersistenceAdapter
    implements TransactionTypeAddPort, TransactionTypeUpdatePort, TransactionTypeDeletePort {

  private final TransactionTypeRepository repository;
  private final TransactionTypeAdapterMapper mapper;

  @Override
  public TransactionType add(final TransactionType transactionType) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(transactionType)));
  }

  @Override
  public TransactionType update(final TransactionType transactionType) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(transactionType)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
