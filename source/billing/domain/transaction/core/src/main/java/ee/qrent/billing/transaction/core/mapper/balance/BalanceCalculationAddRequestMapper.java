package ee.qrent.billing.transaction.core.mapper.balance;

import ee.qrent.billing.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrent.billing.transaction.domain.balance.BalanceCalculation;
import ee.qrent.common.in.mapper.AddRequestMapper;
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
