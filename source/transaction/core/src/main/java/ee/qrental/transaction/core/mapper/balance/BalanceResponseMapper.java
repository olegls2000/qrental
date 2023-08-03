package ee.qrental.transaction.core.mapper.balance;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import ee.qrental.transaction.domain.balance.Balance;

public class BalanceResponseMapper implements ResponseMapper<BalanceResponse, Balance> {
  @Override
  public BalanceResponse toResponse(final Balance domain) {
    if(domain == null) {
      return null;
    }
    
    return BalanceResponse.builder()
        .id(domain.getId())
        .weekNumber(domain.getWeekNumber())
        .created(domain.getCreated())
        .amount(domain.getAmount())
        .fee(domain.getFee())
        .build();
  }

  @Override
  public String toObjectInfo(final Balance domain) {
    return format("Receiver: %s, Sender: %s, Week: %d", "??", "??", "??");
  }
}
