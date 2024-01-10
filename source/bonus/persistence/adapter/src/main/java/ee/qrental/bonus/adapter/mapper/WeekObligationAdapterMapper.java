package ee.qrental.bonus.adapter.mapper;

import ee.qrental.bonus.domain.WeekObligation;
import ee.qrental.bonus.entity.jakarta.WeekObligationJakartaEntity;

public class WeekObligationAdapterMapper {

  public WeekObligation mapToDomain(final WeekObligationJakartaEntity entity) {
    return WeekObligation.builder().id(entity.getId()).build();
  }

  public WeekObligationJakartaEntity mapToEntity(final WeekObligation domain) {
    return WeekObligationJakartaEntity.builder().id(domain.getId()).build();
  }
}
