package ee.qrent.billing.deposit.core.mapper;

import ee.qrent.billing.deposit.api.in.request.DepositAddRequest;
import ee.qrent.billing.deposit.domain.Deposit;
import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.in.time.QDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DepositAddRequestMapper implements AddRequestMapper<DepositAddRequest, Deposit> {

  private final QDateTime qDateTime;

  @Override
  public Deposit toDomain(final DepositAddRequest request) {

    return Deposit.builder()
        .id(null)
        .amount(request.getAmount())
        .driverId(request.getDriverId())
        .createdOn(qDateTime.getToday())
        .comment(request.getComment())
        .build();
  }
}
