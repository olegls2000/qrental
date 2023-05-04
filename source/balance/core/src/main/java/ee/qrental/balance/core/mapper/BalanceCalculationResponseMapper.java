package ee.qrental.balance.core.mapper;

import static java.lang.String.format;

import ee.qrental.balance.api.in.response.BalanceCalculationResponse;
import ee.qrental.balance.api.in.response.BalanceResponse;
import ee.qrental.balance.domain.Balance;
import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.common.core.in.mapper.ResponseMapper;
import java.util.stream.Collectors;

public class BalanceCalculationResponseMapper
    implements ResponseMapper<BalanceCalculationResponse, BalanceCalculation> {
  @Override
  public BalanceCalculationResponse toResponse(final BalanceCalculation domain) {
    if (domain == null) {
      return null;
    }

    final var balances =
        domain.getResults().stream()
            .map(r -> r.getBalance())
            .map(this::toResponse)
            .collect(Collectors.toList());

    return BalanceCalculationResponse.builder()
        .id(domain.getId())
        .actionDate(domain.getActionDate())
        .comment(domain.getComment())
        .balances(balances)
        .build();
  }

  private BalanceResponse toResponse(final Balance domain) {
    return BalanceResponse.builder()
        .fee(domain.getFee())
        .weekNumber(domain.getWeekNumber())
        .amount(domain.getAmount())
        .driverId(domain.getDriverId())
        .build();
  }

  @Override
  public String toObjectInfo(final BalanceCalculation domain) {
    return format("Balance Calculation was done at: %s", domain.getActionDate());
  }
}
