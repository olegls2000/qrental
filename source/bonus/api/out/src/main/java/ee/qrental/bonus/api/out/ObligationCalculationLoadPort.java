package ee.qrental.bonus.api.out;

import ee.qrental.bonus.domain.ObligationCalculation;
import ee.qrental.common.core.out.port.LoadPort;

public interface ObligationCalculationLoadPort extends LoadPort<ObligationCalculation> {
  Long loadLastCalculatedQWeekId();
}
