package ee.qrental.insurance.adapter.mapper;

import ee.qrental.insurance.domain.InsuranceCase;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseJakartaEntity;

public class InsuranceCaseAdapterMapper {

  public InsuranceCase mapToDomain(final InsuranceCaseJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return InsuranceCase.builder()
        .id(entity.getId())
        .driverId(entity.getDriverId())
        .carId(entity.getCarId())
        .damageAmount(entity.getDamageAmount())
        .description(entity.getDescription())
        .occurrenceDate(entity.getOccurrenceDate())
        .startQWeekId(entity.getStartQWeekId())
        .active(entity.getActive())
        .build();
  }

  public InsuranceCaseJakartaEntity mapToEntity(final InsuranceCase domain) {
    if (domain == null) {
      return null;
    }
    return InsuranceCaseJakartaEntity.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .carId(domain.getCarId())
        .damageAmount(domain.getDamageAmount())
        .description(domain.getDescription())
        .occurrenceDate(domain.getOccurrenceDate())
        .startQWeekId(domain.getStartQWeekId())
        .active(domain.getActive())
        .build();
  }
}
