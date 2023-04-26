package ee.qrental.balance.core.mapper;

import ee.qrental.balance.api.in.request.BalanceCalculationAddRequest;
import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.common.core.in.mapper.AddRequestMapper;
import java.util.ArrayList;

public class BalanceCalculationAddRequestMapper
    implements AddRequestMapper<BalanceCalculationAddRequest, BalanceCalculation> {

  public BalanceCalculation toDomain(final BalanceCalculationAddRequest request) {
    return BalanceCalculation.builder()
        .actionDate(request.getActionDate())
        .results(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
