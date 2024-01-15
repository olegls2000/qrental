package ee.qrental.bonus.adapter.mapper;

import ee.qrental.bonus.domain.Obligation;
import ee.qrental.bonus.entity.jakarta.ObligationJakartaEntity;

public class ObligationAdapterMapper {

  public Obligation mapToDomain(final ObligationJakartaEntity entity) {
    return Obligation.builder()
        .id(entity.getId())
        .driverId(entity.getDriverId())
        .qWeekId(entity.getQWeekId())
        .obligationAmount(entity.getObligationAmount())
        .positiveAmount(entity.getPositiveAmount())
        .matchCount(entity.getMatchCount())
        .build();
  }

  public ObligationJakartaEntity mapToEntity(final Obligation domain) {
    return ObligationJakartaEntity.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .qWeekId(domain.getQWeekId())
        .obligationAmount(domain.getObligationAmount())
        .positiveAmount(domain.getPositiveAmount())
        .matchCount(domain.getMatchCount())
        .build();
  }
}
