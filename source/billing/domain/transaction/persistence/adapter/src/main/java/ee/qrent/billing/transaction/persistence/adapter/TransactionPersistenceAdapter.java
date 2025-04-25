package ee.qrent.billing.transaction.persistence.adapter;

import ee.qrent.billing.transaction.persistence.mapper.TransactionAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.TransactionRepository;
import ee.qrent.billing.transaction.api.out.TransactionAddPort;
import ee.qrent.billing.transaction.api.out.TransactionDeletePort;
import ee.qrent.billing.transaction.api.out.TransactionUpdatePort;
import ee.qrent.billing.transaction.domain.Transaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionPersistenceAdapter
    implements TransactionAddPort, TransactionUpdatePort, TransactionDeletePort {

  private final TransactionRepository repository;
  private final TransactionAdapterMapper mapper;

  @Override
  public Transaction add(final Transaction transaction) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(transaction)));
  }

  @Override
  public Transaction update(final Transaction transaction) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(transaction)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
