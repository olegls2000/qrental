package ee.qrent.billing.insurance.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCase;

import java.util.List;

public interface InsuranceCaseLoadPort extends LoadPort<InsuranceCase> {
  List<InsuranceCase> loadActiveByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  List<InsuranceCase> loadActiveByQWeekId(final Long qWeekId);

  List<InsuranceCase> loadAllActive();

  List<InsuranceCase> loadAlClosed();

  Long loadCountActive();

  Long loadCountClosed();

  List<InsuranceCase> loadAllActiveByDriverId(final Long driverId);

  List<Long> loadPaymentTransactionIdsByInsuranceCaseId(final Long insuranceCaseId);
}
