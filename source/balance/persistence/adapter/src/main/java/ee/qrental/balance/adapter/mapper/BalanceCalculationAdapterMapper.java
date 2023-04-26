package ee.qrental.balance.adapter.mapper;

import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.balance.domain.BalanceCalculationResult;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationJakartaEntity;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationResultJakartaEntity;

public class BalanceCalculationAdapterMapper {

  public BalanceCalculation mapToDomain(final BalanceCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return BalanceCalculation.builder()
        .id(entity.getId())
        .actionDate(entity.getActionDate())
        .results(entity.getResults().stream().map(this::mapToDomain).toList())
        .comment(entity.getComment())
        .build();
  }

  private BalanceCalculationResult mapToDomain(
      final BalanceCalculationResultJakartaEntity resultEntity) {

    return BalanceCalculationResult.builder().build();
  }
}
