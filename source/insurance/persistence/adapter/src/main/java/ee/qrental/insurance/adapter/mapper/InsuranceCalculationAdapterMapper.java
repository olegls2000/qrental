package ee.qrental.insurance.adapter.mapper;

import ee.qrental.insurance.domain.InsuranceCalculation;
import ee.qrental.insurance.entity.jakarta.InsuranceCalculationJakartaEntity;

public class InsuranceCalculationAdapterMapper {

  public InsuranceCalculation mapToDomain(final InsuranceCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return InsuranceCalculation.builder()
        .id(entity.getId())
        .actionDate(entity.getActionDate())
        .startQWeekId(entity.getStartQWeekId())
        .endQWeekId(entity.getEndQWeekId())
        .comment(entity.getComment())
        .build();
  }

  public InsuranceCalculationJakartaEntity mapToEntity(final InsuranceCalculation domain) {
    if (domain == null) {
      return null;
    }
    return InsuranceCalculationJakartaEntity.builder()
        .id(domain.getId())
        .actionDate(domain.getActionDate())
        .startQWeekId(domain.getStartQWeekId())
        .endQWeekId(domain.getEndQWeekId())
        .comment(domain.getComment())
        .build();
  }
}
