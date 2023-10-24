package ee.qrental.transaction.api.out.rent;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.transaction.domain.rent.RentCalculation;
import java.time.LocalDate;

public interface RentCalculationLoadPort extends LoadPort<RentCalculation> {
  LocalDate loadLastCalculationDate();
}
