package ee.qrental.transaction.adapter.mapper.balance;

import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.entity.jakarta.balance.BalanceJakartaEntity;

public class BalanceAdapterMapper {

  public Balance mapToDomain(final BalanceJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return Balance.builder()
        .id(entity.getId())
        .qWeekId(entity.getQWeekId())
        .driverId(entity.getDriverId())
        .created(entity.getCreated())
        .feeAbleAmount(entity.getFeeAbleAmount())
        .nonFeeAbleAmount(entity.getNonFeeAbleAmount())
        .repairmentAmount(entity.getRepairmentAmount())
        .feeAmount(entity.getFeeAmount())
        .positiveAmount(entity.getPositiveAmount())
        .derived(entity.getDerived())
        .build();
  }

  public BalanceJakartaEntity mapToEntity(final Balance domain) {
    return BalanceJakartaEntity.builder()
        .id(domain.getId())
        .qWeekId(domain.getQWeekId())
        .driverId(domain.getDriverId())
        .created(domain.getCreated())
        .feeAbleAmount(domain.getFeeAbleAmount())
        .nonFeeAbleAmount(domain.getNonFeeAbleAmount())
        .repairmentAmount(domain.getRepairmentAmount())
        .feeAmount(domain.getFeeAmount())
        .positiveAmount(domain.getPositiveAmount())
        .derived(domain.getDerived())
        .build();
  }
}
