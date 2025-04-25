package ee.qrent.billing.insurance.core.service.balance;

import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;

import ee.qrent.billing.insurance.core.service.InsuranceCaseBalanceCalculator;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.lang.String.format;

@AllArgsConstructor
public class InsuranceCaseBalanceCalculationService implements InsuranceCaseBalanceCalculator {

  private final List<InsuranceCaseBalanceCalculationStrategy> calculationStrategies;

  @Override
  public InsuranceCaseBalance calculateBalance(
      final InsuranceCase insuranceCase, final QWeekResponse requestedQWeek) {
    final var driverId = insuranceCase.getDriverId();
    final var requestedQWeekId = requestedQWeek.getId();

    return getStrategy(requestedQWeekId, driverId).calculate(insuranceCase, requestedQWeek);
  }

  @Override
  public void setFirstRemainingAndSelfResponsibility(
      final InsuranceCase insuranceCase, final InsuranceCaseBalance insuranceCaseBalance) {

    final var driverId = insuranceCase.getDriverId();
    final var requestedQWeekId = insuranceCaseBalance.getQWeekId();

    getStrategy(requestedQWeekId, driverId)
        .setRemainingAndSelfResponsibilityFirstTime(insuranceCase, insuranceCaseBalance);
  }

  private InsuranceCaseBalanceCalculationStrategy getStrategy(
      final Long driverId, final Long requestedQWeekId) {

    return calculationStrategies.stream()
        .filter(strategy -> strategy.canApply(requestedQWeekId, driverId))
        .findFirst()
        .orElseThrow(
            () -> new RuntimeException(
                    format(
                            "No Insurance Case Balance calculation strategy found for driverId: %d and QWeekId: %d",
                            driverId, requestedQWeekId)));
  }
}
