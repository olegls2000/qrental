package ee.qrental.bonus.api.out;

import ee.qrental.bonus.domain.ObligationCalculation;
import ee.qrental.common.core.out.port.LoadPort;
import java.time.LocalDate;

public interface ObligationCalculationLoadPort extends LoadPort<ObligationCalculation> {
  LocalDate loadLastCalculatedDate();
}
