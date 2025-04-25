package ee.qrent.billing.transaction.api.out.balance;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.transaction.domain.balance.BalanceCalculation;

import java.time.LocalDate;

public interface BalanceCalculationLoadPort extends LoadPort<BalanceCalculation> {
  LocalDate loadLastCalculatedDate();
}
