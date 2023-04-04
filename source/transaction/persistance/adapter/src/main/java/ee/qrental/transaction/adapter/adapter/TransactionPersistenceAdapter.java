package ee.qrental.transaction.adapter.adapter;

import ee.qrental.transaction.adapter.mapper.TransactionAdapterMapper;
import ee.qrental.transaction.adapter.repository.TransactionRepository;
import ee.qrental.transaction.api.out.TransactionAddPort;
import ee.qrental.transaction.api.out.TransactionDeletePort;
import ee.qrental.transaction.api.out.TransactionUpdatePort;
import ee.qrental.transaction.domain.Transaction;
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
