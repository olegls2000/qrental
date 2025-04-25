package ee.qrent.billing.transaction.persistence.mapper.balance;

import ee.qrent.billing.transaction.domain.balance.BalanceCalculation;
import ee.qrent.billing.transaction.domain.balance.BalanceCalculationResult;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationJakartaEntity;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationAdapterMapper {

  private final BalanceAdapterMapper balanceAdapterMapper;

  public BalanceCalculation mapToDomain(final BalanceCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return BalanceCalculation.builder()
        .id(entity.getId())
        .startDate(entity.getStartDate())
        .endDate(entity.getEndDate())
        .actionDate(entity.getActionDate())
        .results(entity.getResults().stream().map(this::mapToDomain).toList())
        .comment(entity.getComment())
        .build();
  }

  private BalanceCalculationResult mapToDomain(
      final BalanceCalculationResultJakartaEntity resultEntity) {

    return BalanceCalculationResult.builder()
        .balance(balanceAdapterMapper.mapToDomain(resultEntity.getBalance()))
        .build();
  }
}
