package ee.qrental.transaction.api.out.balance;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.transaction.domain.balance.BalanceCalculation;

import java.time.LocalDate;

public interface BalanceCalculationLoadPort extends LoadPort<BalanceCalculation> {
  LocalDate loadLastCalculatedDate();
}
