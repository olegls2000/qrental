package ee.qrental.balance.core.mapper;

import static java.lang.String.format;

import ee.qrental.balance.api.in.response.BalanceCalculationResponse;
import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.common.core.in.mapper.ResponseMapper;

public class BalanceCalculationResponseMapper
    implements ResponseMapper<BalanceCalculationResponse, BalanceCalculation> {
  @Override
  public BalanceCalculationResponse toResponse(final BalanceCalculation domain) {
    if (domain == null) {
      return null;
    }
    return BalanceCalculationResponse.builder()
        .id(domain.getId())
        .actionDate(domain.getActionDate())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final BalanceCalculation domain) {
    return format("Balance Calculation was done at: %s", domain.getActionDate());
  }
}
