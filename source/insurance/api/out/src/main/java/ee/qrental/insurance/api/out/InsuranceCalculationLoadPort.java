package ee.qrental.insurance.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.insurance.domain.InsuranceCalculation;

public interface InsuranceCalculationLoadPort extends LoadPort<InsuranceCalculation> {
  Long loadLastCalculatedQWeekId();
}
