package ee.qrent.billing.transaction.api.in.response.balance;

import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@SuperBuilder
@Getter
public class BalanceRawContextResponse {
  private BalanceResponse previousWeekBalance;
  private BalanceResponse requestedWeekBalance;
  private Map<String, List<TransactionResponse>> transactionsByKind;
}
