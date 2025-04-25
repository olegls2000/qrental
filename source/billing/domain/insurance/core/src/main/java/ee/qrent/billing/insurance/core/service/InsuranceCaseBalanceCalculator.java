package ee.qrent.billing.insurance.core.service;

import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;

public interface InsuranceCaseBalanceCalculator {

  InsuranceCaseBalance calculateBalance(
      final InsuranceCase insuranceCase, final QWeekResponse requestedQWeek);

  void setFirstRemainingAndSelfResponsibility(
      final InsuranceCase insuranceCase, final InsuranceCaseBalance insuranceCaseBalance);
}
