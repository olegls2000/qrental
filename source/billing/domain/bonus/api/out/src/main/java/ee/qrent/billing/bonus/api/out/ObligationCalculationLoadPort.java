package ee.qrent.billing.bonus.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.bonus.domain.ObligationCalculation;


public interface ObligationCalculationLoadPort extends LoadPort<ObligationCalculation> {
  Long loadLastCalculatedQWeekId();
}
