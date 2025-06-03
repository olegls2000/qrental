package ee.qrent.billing.bolt.persistence.mapper;

import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltStatisticsJakartaEntity;

public class BoltStatisticsAdapterMapper {

  public BoltStatistics mapToDomain(final BoltStatisticsJakartaEntity entity) {
    return BoltStatistics.builder()
        .id(entity.getId())
        .comment(entity.getComment())
        .build();
  }

  public BoltStatisticsJakartaEntity mapToEntity(final BoltStatistics domain) {
    return BoltStatisticsJakartaEntity.builder()
        .id(domain.getId())
        .comment(domain.getComment())
        .build();
  }
}
