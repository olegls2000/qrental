package ee.qrental.transaction.adapter.mapper.rent;

import ee.qrental.transaction.domain.rent.RentCalculation;
import ee.qrental.transaction.domain.rent.RentCalculationResult;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationJakartaEntity;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationAdapterMapper {

  public RentCalculation mapToDomain(final RentCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return RentCalculation.builder()
        .id(entity.getId())
        .actionDate(entity.getActionDate())
        .qWeekId(entity.getQWeekId())
        .results(entity.getResults().stream().map(this::mapToDomain).toList())
        .comment(entity.getComment())
        .build();
  }

  private RentCalculationResult mapToDomain(final RentCalculationResultJakartaEntity resultEntity) {

    return RentCalculationResult.builder()
        .id(resultEntity.getId())
        .carLinkId(resultEntity.getCarLinkId())
        .transactionId(resultEntity.getTransactionId())
        .build();
  }
}
