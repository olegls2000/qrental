package ee.qrental.transaction.core.service.balance.calculator;

import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.domain.balance.Balance;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@SuperBuilder
@Getter
public class BalanceWrapper {
  private Balance requestedWeekBalance;
  private Map<String, List<TransactionResponse>> transactionsByKind;
}
