package ee.qrental.bonus.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.bonus.domain.BonusCalculation;


public interface BonusCalculationLoadPort extends LoadPort<BonusCalculation> {
  Long loadLastCalculatedQWeekId();
}
