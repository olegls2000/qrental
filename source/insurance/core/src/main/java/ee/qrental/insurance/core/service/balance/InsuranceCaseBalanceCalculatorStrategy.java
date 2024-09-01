package ee.qrental.insurance.core.service.balance;

import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.domain.InsuranceCase;
import ee.qrental.insurance.domain.InsuranceCaseBalance;

public interface InsuranceCaseBalanceCalculatorStrategy {

  String DRY_RUN = "dry-run";
  String SAVING = "saving";

  InsuranceCaseBalance calculateBalance(
      final InsuranceCase insuranceCase,
      final QWeekResponse requestedQWeek,
      final InsuranceCaseBalance previousWeekBalance);

  boolean canApply(final String strategyName);
}
