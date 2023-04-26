package ee.qrental.balance.adapter.adapter;

import ee.qrental.balance.adapter.mapper.BalanceAdapterMapper;
import ee.qrental.balance.adapter.repository.BalanceRepository;
import ee.qrental.balance.api.out.BalanceAddPort;
import ee.qrental.balance.domain.Balance;
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
