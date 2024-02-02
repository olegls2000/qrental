package ee.qrental.bonus.api.out;

import ee.qrental.bonus.domain.BonusCalculation;
import ee.qrental.common.core.out.port.LoadPort;

public interface BonusCalculationLoadPort extends LoadPort<BonusCalculation> {
  Long loadLastCalculatedQWeekId();
}
