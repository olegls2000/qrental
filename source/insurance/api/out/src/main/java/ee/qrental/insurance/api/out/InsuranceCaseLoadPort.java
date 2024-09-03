package ee.qrental.insurance.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.insurance.domain.InsuranceCase;

import java.util.List;

public interface InsuranceCaseLoadPort extends LoadPort<InsuranceCase> {
  List<InsuranceCase> loadActiveByQWeekId(final Long qWeekId);

  List<InsuranceCase> loadActiveByDriverId(final Long driverId);
}
