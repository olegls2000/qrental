package ee.qrental.transaction.core.mapper.balance;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.transaction.api.in.response.balance.BalanceCalculationResponse;
import ee.qrental.transaction.domain.balance.BalanceCalculation;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationResponseMapper
    implements ResponseMapper<BalanceCalculationResponse, BalanceCalculation> {
  private final BalanceResponseMapper balanceResponseMapper;

  @Override
  public BalanceCalculationResponse toResponse(final BalanceCalculation domain) {
    if (domain == null) {
      return null;
    }

    final var balances =
        domain.getResults().stream()
            .map(r -> r.getBalance())
            .map(balanceResponseMapper::toResponse)
            .collect(Collectors.toList());

    return BalanceCalculationResponse.builder()
        .id(domain.getId())
        .startDate(domain.getStartDate())
        .endDate(domain.getEndDate())
        .actionDate(domain.getActionDate())
        .comment(domain.getComment())
        .balances(balances)
        .build();
  }

  @Override
  public String toObjectInfo(final BalanceCalculation domain) {
    return String.format("Balance Calculation was done at: %s", domain.getActionDate());
  }
}
