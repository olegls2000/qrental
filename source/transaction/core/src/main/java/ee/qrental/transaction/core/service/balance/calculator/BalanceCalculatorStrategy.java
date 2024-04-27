package ee.qrental.transaction.core.service.balance.calculator;

import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;

import java.util.List;
import java.util.Map;

public interface BalanceCalculatorStrategy {

  String DRY_RUN = "dry-run";
  String SAVING = "saving";

  Balance calculateBalance(
      final DriverResponse driver,
      final QWeekResponse requestedQWeek,
      final Balance previousWeekBalance,
      final Map<TransactionKindsCode, List<TransactionResponse>> transactionsByKind);

  boolean canApply(String strategyName);
}
