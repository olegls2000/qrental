package ee.qrent.billing.bolt.persistence.adapter;

import ee.qrent.billing.bolt.api.out.BoltStatisticsAddPort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsDeletePort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsUpdatePort;
import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.billing.bolt.persistence.mapper.BoltStatisticsAdapterMapper;
import ee.qrent.billing.bolt.persistence.repository.BoltStatisticsRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltStatisticsPersistenceAdapter
    implements BoltStatisticsAddPort, BoltStatisticsUpdatePort, BoltStatisticsDeletePort {

  private final BoltStatisticsRepository repository;
  private final BoltStatisticsAdapterMapper mapper;

  @Override
  public BoltStatistics add(final BoltStatistics domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public BoltStatistics update(final BoltStatistics domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
