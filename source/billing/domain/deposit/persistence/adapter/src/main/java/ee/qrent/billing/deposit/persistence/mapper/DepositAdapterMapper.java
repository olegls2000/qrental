package ee.qrent.billing.deposit.persistence.mapper;

import ee.qrent.billing.deposit.domain.Deposit;
import ee.qrent.billing.deposit.persistence.entity.jakarta.DepositJakartaEntity;

public class DepositAdapterMapper {

  public Deposit mapToDomain(final DepositJakartaEntity entity) {

    return Deposit.builder()
        .id(entity.getId())
        .driverId(entity.getDriverId())
        .amount(entity.getAmount())
        .createdOn(entity.getCreatedOn())
        .comment(entity.getComment())
        .build();
  }

  public DepositJakartaEntity mapToEntity(final Deposit domain) {

    return DepositJakartaEntity.builder()
        .id(domain.getId())
        .amount(domain.getAmount())
        .driverId(domain.getDriverId())
        .createdOn(domain.getCreatedOn())
        .comment(domain.getComment())
        .build();
  }
}
