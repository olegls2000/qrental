package ee.qrental.insurance.api.out;

import ee.qrental.insurance.domain.InsuranceCaseBalance;

import java.util.List;

public interface InsuranceCaseBalanceLoadPort {

  InsuranceCaseBalance loadLatestByInsuranceCaseId(final Long insuranceCseId);

  InsuranceCaseBalance loadByInsuranceCaseIdAndQWeekId(
      final Long insuranceCseId, final Long qWeekId);

  List<InsuranceCaseBalance> loadAllByInsuranceCseId(final Long insuranceCseId);

  List<InsuranceCaseBalance> loadAllByQWeekIdAndDriverId(final Long qWeekId, final Long driverId);
}
