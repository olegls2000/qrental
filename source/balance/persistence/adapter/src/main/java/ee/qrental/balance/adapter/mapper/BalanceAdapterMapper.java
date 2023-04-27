package ee.qrental.balance.adapter.mapper;

import ee.qrental.balance.domain.Balance;
import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;

public class BalanceAdapterMapper {

  public Balance mapToDomain(final BalanceJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return Balance.builder()
        .id(entity.getId())
        .weekNumber(entity.getWeekNumber())
        .year(entity.getYear())
        .driverId(entity.getDriverId())
        .created(entity.getCreated())
        .amount(entity.getAmount())
        .build();
  }

  public BalanceJakartaEntity mapToEntity(final Balance domain) {
    return BalanceJakartaEntity.builder()
        .id(domain.getId())
        .weekNumber(domain.getWeekNumber())
        .year(domain.getYear())
        .driverId(domain.getDriverId())
        .created(domain.getCreated())
        .amount(domain.getAmount())
        .build();
  }
}
