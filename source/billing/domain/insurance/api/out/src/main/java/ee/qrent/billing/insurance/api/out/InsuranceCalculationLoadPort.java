package ee.qrent.billing.insurance.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;

public interface InsuranceCalculationLoadPort extends LoadPort<InsuranceCalculation> {
  Long loadLastCalculatedQWeekId();
}
