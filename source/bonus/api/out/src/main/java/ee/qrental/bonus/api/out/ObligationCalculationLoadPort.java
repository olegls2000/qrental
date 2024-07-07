package ee.qrental.bonus.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.bonus.domain.ObligationCalculation;


public interface ObligationCalculationLoadPort extends LoadPort<ObligationCalculation> {
  Long loadLastCalculatedQWeekId();
}
