package ee.qrent.billing.bolt.persistence.adapter;

import ee.qrent.billing.bolt.api.out.BoltOrdersCountAddPort;
import ee.qrent.billing.bolt.domain.BoltOrdersCount;
import ee.qrent.billing.bolt.persistence.mapper.BoltOrdersCountAdapterMapper;
import ee.qrent.billing.bolt.persistence.repository.BoltOrdersCountRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltOrdersCountPersistenceAdapter
    implements BoltOrdersCountAddPort {

  private final BoltOrdersCountRepository repository;
  private final BoltOrdersCountAdapterMapper mapper;

  @Override
  public BoltOrdersCount add(final BoltOrdersCount domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }
}
