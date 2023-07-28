package ee.qrental.transaction.adapter.adapter.balance;

import ee.qrental.transaction.adapter.mapper.balance.BalanceAdapterMapper;
import ee.qrental.transaction.adapter.repository.balance.BalanceRepository;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.domain.balance.Balance;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalancePersistenceAdapter implements BalanceAddPort {

  private final BalanceRepository repository;
  private final BalanceAdapterMapper mapper;

  @Override
  public Balance add(final Balance domain) {
    final var savedInvoiceEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedInvoiceEntity);
  }
}
