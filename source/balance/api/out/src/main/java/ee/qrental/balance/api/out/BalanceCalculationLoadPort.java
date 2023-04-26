package ee.qrental.balance.api.out;

import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.common.core.out.port.LoadPort;
import java.time.LocalDate;

public interface BalanceCalculationLoadPort extends LoadPort<BalanceCalculation> {
  LocalDate loadLastCalculationDate();
}
