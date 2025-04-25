package ee.qrent.billing.insurance.core.service.balance;

import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;

public interface InsuranceCaseBalanceCalculationStrategy {

  boolean canApply(final Long qWeekId, final Long driverId);

  InsuranceCaseBalance calculate(
      final InsuranceCase insuranceCase, final QWeekResponse requestedQWeek);

  void setRemainingAndSelfResponsibilityFirstTime(
      final InsuranceCase insuranceCase, final InsuranceCaseBalance insuranceCaseBalance);
}
