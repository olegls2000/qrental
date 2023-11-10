package ee.qrental.transaction.api.out.balance;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.transaction.domain.balance.BalanceCalculation;

import java.time.LocalDate;

public interface BalanceCalculationLoadPort extends LoadPort<BalanceCalculation> {
  LocalDate loadLastCalculatedDate();
}
