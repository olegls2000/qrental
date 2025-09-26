package ee.qrent.billing.bolt.persistence.mapper;

import ee.qrent.billing.bolt.domain.BoltOrdersCount;
import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltOrdersCountJakartaEntity;

public class BoltOrdersCountAdapterMapper {

  public BoltOrdersCount mapToDomain(final BoltOrdersCountJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    return BoltOrdersCount.builder()
        .id(entity.getId())
        .boltId(entity.getBoltId())
        .qWeekId(entity.getQWeekId())
        .driverId(entity.getDriverId())
        .monthOrdersCount(entity.getMonthOrdersCount())
        .month(entity.getMonth())
        .year(entity.getYear())
        .build();
  }

  public BoltOrdersCountJakartaEntity mapToEntity(final BoltOrdersCount domain) {
    return BoltOrdersCountJakartaEntity.builder()
        .id(domain.getId())
        .boltId(domain.getBoltId())
        .qWeekId(domain.getQWeekId())
        .driverId(domain.getDriverId())
        .monthOrdersCount(domain.getMonthOrdersCount())
        .month(domain.getMonth())
        .year(domain.getYear())
        .build();
  }
}
