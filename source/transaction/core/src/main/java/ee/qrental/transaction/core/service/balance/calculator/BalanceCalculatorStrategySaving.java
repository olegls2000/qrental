package ee.qrental.transaction.core.service.balance.calculator;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.domain.balance.Balance;
import java.math.BigDecimal;

public class BalanceCalculatorStrategySaving extends AbstractBalanceCalculator {
  private final TransactionAddUseCase transactionAddUseCase;
  private final TransactionTypeLoadPort transactionTypeLoadPort;
  private final BalanceAddPort balanceAddPort;
  private static final String TRANSACTION_TYPE_NAME_FEE_DEBT = "fee debt";

  public BalanceCalculatorStrategySaving(
      final TransactionAddUseCase transactionAddUseCase,
      final BalanceDeriveService balanceDeriveService,
      final GetConstantQuery constantQuery,
      final TransactionTypeLoadPort transactionTypeLoadPort,
      final BalanceAddPort balanceAddPort) {
    super(balanceDeriveService, constantQuery);
    this.transactionAddUseCase = transactionAddUseCase;
    this.transactionTypeLoadPort = transactionTypeLoadPort;
    this.balanceAddPort = balanceAddPort;
  }

  @Override
  protected void saveFeeTransactionIfNecessary(
      final BigDecimal feeAmountForPreviousWeek, final QWeekResponse qWeek, final Long driverId) {
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
      transactionAddUseCase.add(feeTransactionAddRequest);
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
