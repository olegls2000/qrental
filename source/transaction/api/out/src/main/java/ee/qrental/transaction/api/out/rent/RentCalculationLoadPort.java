package ee.qrental.transaction.api.out.rent;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.transaction.domain.rent.RentCalculation;

public interface RentCalculationLoadPort extends LoadPort<RentCalculation> {
  Long loadLastCalculationQWeekId();
}
