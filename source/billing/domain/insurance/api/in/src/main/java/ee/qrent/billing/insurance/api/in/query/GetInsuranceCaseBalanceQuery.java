package ee.qrent.billing.insurance.api.in.query;

import ee.qrent.billing.insurance.api.in.response.InsuranceBalanceTotalResponse;

public interface GetInsuranceCaseBalanceQuery {

  InsuranceBalanceTotalResponse getInsuranceBalanceTotalByDriverForCurrentWeek(final Long driverId);

  InsuranceBalanceTotalResponse getInsuranceBalanceTotalByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId);
}
