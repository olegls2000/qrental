package ee.qrent.billing.bonus.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.bonus.domain.BonusCalculation;


public interface BonusCalculationLoadPort extends LoadPort<BonusCalculation> {
  Long loadLastCalculatedQWeekId();
}
