package ee.qrental.transaction.adapter.mapper.balance;

import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.balance.BalanceCalculation;
import ee.qrental.transaction.domain.balance.BalanceCalculationResult;
import ee.qrental.transaction.entity.jakarta.balance.BalanceCalculationJakartaEntity;
import ee.qrental.transaction.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import ee.qrental.transaction.entity.jakarta.balance.BalanceJakartaEntity;

public class BalanceCalculationAdapterMapper {

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
        .balance(mapToDomain(resultEntity.getBalance()))
        .build();
  }

  private Balance mapToDomain(final BalanceJakartaEntity balanceEntity) {
    return Balance.builder()
        .id(balanceEntity.getId())
        .qWeekId(balanceEntity.getQWeekId())
        .created(balanceEntity.getCreated())
        .driverId(balanceEntity.getDriverId())
        .amount(balanceEntity.getAmount())
        .fee(balanceEntity.getFee())
        .build();
  }
}
