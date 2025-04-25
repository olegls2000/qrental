package ee.qrent.billing.bonus.persistence.mapper;

import ee.qrent.billing.bonus.domain.BonusCalculation;
import ee.qrent.billing.bonus.domain.BonusCalculationResult;
import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationJakartaEntity;
import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationResultJakartaEntity;

public class BonusCalculationAdapterMapper {
  public BonusCalculation mapToDomain(final BonusCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return BonusCalculation.builder()
        .id(entity.getId())
        .qWeekId(entity.getQWeekId())
        .actionDate(entity.getActionDate())
        .results(entity.getResults().stream().map(this::mapToDomain).toList())
        .comment(entity.getComment())
        .build();
  }

  private BonusCalculationResult mapToDomain(
      final BonusCalculationResultJakartaEntity resultEntity) {

    return BonusCalculationResult.builder()
        .bonusProgramId(resultEntity.getBonusProgram().getId())
        .transactionId(resultEntity.getTransactionId())
        .calculationId(resultEntity.getBonusCalculation().getId())
        .build();
  }
}
