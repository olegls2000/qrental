package ee.qrent.billing.bolt.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.bolt.api.out.BoltStatisticsLoadPort;
import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.billing.bolt.persistence.mapper.BoltStatisticsAdapterMapper;
import ee.qrent.billing.bolt.persistence.repository.BoltStatisticsRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltStatisticsLoadAdapter implements BoltStatisticsLoadPort {

  private final BoltStatisticsRepository repository;
  private final BoltStatisticsAdapterMapper mapper;

  @Override
  public List<BoltStatistics> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public BoltStatistics loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }
}
