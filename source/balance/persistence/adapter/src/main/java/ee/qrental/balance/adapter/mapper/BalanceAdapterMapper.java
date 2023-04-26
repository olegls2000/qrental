package ee.qrental.balance.adapter.mapper;

import ee.qrental.balance.domain.Balance;
import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;

public class BalanceAdapterMapper {

  public Balance mapToDomain(final BalanceJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return Balance.builder().id(entity.getId()).build();
  }

  public BalanceJakartaEntity mapToEntity(final Balance domain) {
    return BalanceJakartaEntity.builder()
        .id(domain.getId())
        .weekNumber(domain.getWeekNumber())
        .driverId(domain.getDriverId())
        .created(domain.getCreated())
        .build();
  }
}
