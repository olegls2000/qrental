package ee.qrent.billing.transaction.core.service.balance.calculator;

import ee.qrent.billing.constant.api.in.query.GetConstantQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.billing.transaction.api.out.balance.BalanceAddPort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrent.billing.transaction.domain.balance.Balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ee.qrent.billing.transaction.domain.kind.TransactionKindsCode.F;

public class BalanceCalculatorStrategySaving extends AbstractBalanceCalculator {
  private final TransactionAddUseCase transactionAddUseCase;
  private final GetTransactionQuery transactionQuery;
  private final TransactionTypeLoadPort transactionTypeLoadPort;
  private final BalanceAddPort balanceAddPort;
  private static final String TRANSACTION_TYPE_NAME_FEE_DEBT = "fee debt";

  public BalanceCalculatorStrategySaving(
      final TransactionAddUseCase transactionAddUseCase,
      final BalanceDeriveService balanceDeriveService,
      final GetConstantQuery constantQuery,
      GetTransactionQuery transactionQuery,
      final TransactionTypeLoadPort transactionTypeLoadPort,
      final BalanceAddPort balanceAddPort) {
    super(balanceDeriveService, constantQuery);
    this.transactionAddUseCase = transactionAddUseCase;
    this.transactionQuery = transactionQuery;
    this.transactionTypeLoadPort = transactionTypeLoadPort;
    this.balanceAddPort = balanceAddPort;
  }

  @Override
  protected void handleFeeTransaction(
      final BigDecimal feeAmountForPreviousWeek,
      final QWeekResponse qWeek,
      final Long driverId,
      final Map<String, List<TransactionResponse>> transactionsByKind) {
    if (feeAmountForPreviousWeek.compareTo(BigDecimal.ZERO) > 0) {
      final var transactionType =
          transactionTypeLoadPort.loadByName(TRANSACTION_TYPE_NAME_FEE_DEBT);
      final var feeTransactionAddRequest = new TransactionAddRequest();
      feeTransactionAddRequest.setAmount(feeAmountForPreviousWeek);
      feeTransactionAddRequest.setDate(qWeek.getEnd());
      feeTransactionAddRequest.setTransactionTypeId(transactionType.getId());
      feeTransactionAddRequest.setDriverId(driverId);
      feeTransactionAddRequest.setWeekNumber(qWeek.getNumber());
      feeTransactionAddRequest.setComment("automatically created during Balance calculation");
      final var feeTransactionId = transactionAddUseCase.add(feeTransactionAddRequest);

      final var vialations = feeTransactionAddRequest.getViolations();
      if (vialations.size() > 0) {
        throw new RuntimeException(vialations.get(0));
      }

      final var feeTransaction = transactionQuery.getById(feeTransactionId);
      if (transactionsByKind.get(F.name()) == null) {
        transactionsByKind.put(F.name(), new ArrayList<>());
      }
      transactionsByKind.get(F.name()).add(feeTransaction);
    }
  }

  @Override
  protected void saveBalanceIfNecessary(final Balance balance) {
    balanceAddPort.add(balance);
  }

  @Override
  protected Balance saveAndGetDerivedBalanceIfNecessary(final Balance derivedBalance) {
    return balanceAddPort.add(derivedBalance);
  }

  @Override
  public boolean canApply(final String strategyName) {
    return SAVING.equals(strategyName);
  }
}
