package ee.qrent.billing.constant.persistence.mapper;

import ee.qrent.billing.constant.domain.QWeek;
import ee.qrent.billing.constant.persistence.entity.jakarta.QWeekJakartaEntity;

public class QWeekAdapterMapper {

  public QWeek mapToDomain(final QWeekJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    return QWeek.builder()
        .id(entity.getId())
        .year(entity.getYear())
        .number(entity.getNumber())
        .description(entity.getDescription())
        .build();
  }

  public QWeekJakartaEntity mapToEntity(final QWeek domain) {
    return QWeekJakartaEntity.builder()
        .id(domain.getId())
        .year(domain.getYear())
        .number(domain.getNumber())
        .description(domain.getDescription())
        .build();
  }
}
