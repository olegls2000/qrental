package ee.qrental.insurance.api.out;

import ee.qrental.insurance.domain.InsuranceCaseBalance;

import java.util.List;

public interface InsuranceCaseBalanceLoadPort {

  InsuranceCaseBalance loadLatestByInsuranceCseId(final Long insuranceCseId);

  List<InsuranceCaseBalance> loadAllByInsuranceCseId(final Long insuranceCseId);
}
