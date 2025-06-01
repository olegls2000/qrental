package ee.qrent.billing.deposit.core.mapper;

import ee.qrent.billing.deposit.api.in.request.DepositUpdateRequest;
import ee.qrent.billing.deposit.domain.Deposit;
import ee.qrent.common.in.mapper.UpdateRequestMapper;

public class DepositUpdateRequestMapper
    implements UpdateRequestMapper<DepositUpdateRequest, Deposit> {

  @Override
  public Deposit toDomain(final DepositUpdateRequest request) {
    return Deposit.builder()
        .id(request.getId())
        .amount(request.getAmount())
        .driverId(request.getDriverId())
        .createdOn(request.getCreatedOn())
        .comment(request.getComment())
        .build();
  }

  @Override
  public DepositUpdateRequest toRequest(final Deposit domain) {

    return DepositUpdateRequest.builder()
        .id(domain.getId())
        .amount(domain.getAmount())
        .driverId(domain.getDriverId())
        .createdOn(domain.getCreatedOn())
        .comment(domain.getComment())
        .build();
  }
}
