package ee.qrent.billing.bonus.persistence.mapper;

import ee.qrent.billing.bonus.domain.ObligationCalculation;
import ee.qrent.billing.bonus.domain.ObligationCalculationResult;
import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationCalculationJakartaEntity;
import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationCalculationResultJakartaEntity;

import java.util.List;

public class ObligationCalculationAdapterMapper {
  public ObligationCalculation mapToDomain(final ObligationCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    return ObligationCalculation.builder()
        .id(entity.getId())
        .qWeekId(entity.getQWeekId())
        .actionDate(entity.getActionDate())
        .results(mapToDomain(entity.getResults()))
        .comment(entity.getComment())
        .build();
  }

  private List<ObligationCalculationResult> mapToDomain(
      final List<ObligationCalculationResultJakartaEntity> entities) {
    if (entities == null) {
      return null;
    }
    return entities.stream().map(this::mapToDomain).toList();
  }

  private ObligationCalculationResult mapToDomain(
      final ObligationCalculationResultJakartaEntity resultEntity) {

    return ObligationCalculationResult.builder()
        .obligationId(resultEntity.getObligationId())
        .calculationId(resultEntity.getObligationCalculation().getId())
        .build();
  }
}
