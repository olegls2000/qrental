package ee.qrent.billing.deposit.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.deposit.api.out.DepositLoadPort;
import ee.qrent.billing.deposit.domain.Deposit;
import ee.qrent.billing.deposit.persistence.mapper.DepositAdapterMapper;
import ee.qrent.billing.deposit.persistence.repository.DepositRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DepositLoadAdapter implements DepositLoadPort {

  private final DepositRepository repository;
  private final DepositAdapterMapper mapper;

  @Override
  public List<Deposit> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Deposit loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<Deposit> loadAllByDriverId(final Long driverId) {
    return repository.findAllByDriverId(driverId).stream().map(mapper::mapToDomain).collect(toList());
  }
}
