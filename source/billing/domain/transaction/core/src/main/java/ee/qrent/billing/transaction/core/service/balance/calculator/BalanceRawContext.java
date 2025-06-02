package ee.qrent.billing.transaction.core.service.balance.calculator;

import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.domain.balance.Balance;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@SuperBuilder
@Getter
public class BalanceRawContext {
  private Balance requestedWeekBalance;
  private Balance previousWeekBalance;
  private Map<String, List<TransactionResponse>> transactionsByKind;
}
