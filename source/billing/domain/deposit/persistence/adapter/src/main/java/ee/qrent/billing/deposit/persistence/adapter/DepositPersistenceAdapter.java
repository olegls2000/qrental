package ee.qrent.billing.deposit.persistence.adapter;

import ee.qrent.billing.deposit.api.out.DepositAddPort;
import ee.qrent.billing.deposit.api.out.DepositDeletePort;
import ee.qrent.billing.deposit.api.out.DepositUpdatePort;
import ee.qrent.billing.deposit.domain.Deposit;
import ee.qrent.billing.deposit.persistence.mapper.DepositAdapterMapper;
import ee.qrent.billing.deposit.persistence.repository.DepositRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DepositPersistenceAdapter
    implements DepositAddPort, DepositUpdatePort, DepositDeletePort {

  private final DepositRepository repository;
  private final DepositAdapterMapper mapper;

  @Override
  public Deposit add(final Deposit domain) {

    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public Deposit update(final Deposit domain) {

    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
