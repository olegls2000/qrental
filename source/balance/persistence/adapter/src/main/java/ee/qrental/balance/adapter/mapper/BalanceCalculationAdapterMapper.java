package ee.qrental.balance.adapter.mapper;

import ee.qrental.balance.domain.Balance;
import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.balance.domain.BalanceCalculationResult;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationJakartaEntity;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationResultJakartaEntity;
import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;

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
        .year(balanceEntity.getYear())
        .weekNumber(balanceEntity.getWeekNumber())
        .created(balanceEntity.getCreated())
        .driverId(balanceEntity.getDriverId())
        .amount(balanceEntity.getAmount())
        .fee(balanceEntity.getFee())
        .build();
  }
}
