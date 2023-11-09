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
        .amount(entity.getAmount())
        .fee(entity.getFee())
        .build();
  }

  public BalanceJakartaEntity mapToEntity(final Balance domain) {
    return BalanceJakartaEntity.builder()
        .id(domain.getId())
        .qWeekId(domain.getQWeekId())
        .driverId(domain.getDriverId())
        .created(domain.getCreated())
        .amount(domain.getAmount())
        .fee(domain.getFee())
        .build();
  }
}
