package ee.qrent.billing.bolt.persistence.mapper;

import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltStatisticsJakartaEntity;

public class BoltStatisticsAdapterMapper {

  public BoltStatistics mapToDomain(final BoltStatisticsJakartaEntity entity) {
    return BoltStatistics.builder()
        .id(entity.getId())
        .data(entity.getData())
        .region(entity.getRegion())
        .fileName(entity.getFileName())
        .month(entity.getMonth())
        .year(entity.getYear())
        .createdOn(entity.getCreatedOn())
        .build();
  }

  public BoltStatisticsJakartaEntity mapToEntity(final BoltStatistics domain) {
    return BoltStatisticsJakartaEntity.builder()
        .id(domain.getId())
        .data(domain.getData())
        .region(domain.getRegion())
        .fileName(domain.getFileName())
        .month(domain.getMonth())
        .year(domain.getYear())
        .createdOn(domain.getCreatedOn())
        .build();
  }
}
