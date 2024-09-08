package ee.qrental.insurance.core.service.balance;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseBalanceQuery;
import ee.qrental.insurance.api.in.response.InsuranceBalanceTotalResponse;
import ee.qrental.insurance.api.out.InsuranceCalculationLoadPort;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.domain.InsuranceCase;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ee.qrental.common.utils.QTimeUtils.getWeekNumber;
import static ee.qrental.constant.api.in.query.GetQWeekQuery.DEFAULT_COMPARATOR;
import static ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceCalculatorStrategy.DRY_RUN;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

@AllArgsConstructor
public class InsuranceCaseBalanceQueryService implements GetInsuranceCaseBalanceQuery {
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCaseBalanceLoadPort balanceLoadPort;
  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCalculationLoadPort calculationLoadPort;
  private final List<InsuranceCaseBalanceCalculatorStrategy> calculatorStrategies;

  @Override
  public InsuranceBalanceTotalResponse getInsuranceBalanceTotalByDriverForCurrentWeek(
      final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();

    return getInsuranceBalanceTotalByDriverIdAndQWeekId(driverId, currentQWeek.getId());
  }

  @Override
  public InsuranceBalanceTotalResponse getInsuranceBalanceTotalByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {

    final var lastCalculatedQWeekId = calculationLoadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {

      return calculateBalance(qWeekId, driverId);
    }
    final var lastCalculatedQWeek = qWeekQuery.getById(lastCalculatedQWeekId);
    final var requestedQWeek = qWeekQuery.getById(qWeekId);

    final var isRequestedWeekBeforeLastCalculatedWeek =
        requestedQWeek.compareTo(lastCalculatedQWeek) > 0;

    if (isRequestedWeekBeforeLastCalculatedWeek) {
      return getCalculatedBalance(qWeekId, driverId);
    }

    return calculateBalance(qWeekId, driverId);
  }

  private InsuranceBalanceTotalResponse getCalculatedBalance(
      final Long requestedQWeekId, final Long driverId) {
    final var balances = balanceLoadPort.loadAllByQWeekIdAndDriverId(requestedQWeekId, driverId);

    final var damageRemainingTotal =
        balances.stream()
            .map(balance -> balance.getDamageRemaining())
            .reduce(ZERO, BigDecimal::add);

    final var selfResponsibilityRemainingTotal =
        balances.stream()
            .map(balance -> balance.getSelfResponsibilityRemaining())
            .reduce(ZERO, BigDecimal::add);

    return InsuranceBalanceTotalResponse.builder()
        .damageRemainingTotal(damageRemainingTotal)
        .selfResponsibilityRemainingTotal(selfResponsibilityRemainingTotal)
        .build();
  }

  private InsuranceCaseBalance calculateBalanceForInsuranceCase(
      final InsuranceCase insuranceCase,
      final Long requestedQWeekId,
      final InsuranceCaseBalanceCalculatorStrategy calculator) {
    InsuranceCaseBalance requestedWeekBalance = null;
    final var latestCalculatedBalance =
        balanceLoadPort.loadLatestByInsuranceCaseId(insuranceCase.getId());

    final QWeekResponse startWeek = getStartWeek(insuranceCase, latestCalculatedBalance);
    InsuranceCaseBalance previousWeekBalance = latestCalculatedBalance;

    final var weeksForCalculation =
        qWeekQuery.getQWeeksFromPeriodOrdered(
            startWeek.getId(), requestedQWeekId, DEFAULT_COMPARATOR);

    if (weeksForCalculation.isEmpty()) {
      return balanceLoadPort.loadByInsuranceCaseIdAndQWeekId(
          insuranceCase.getId(), requestedQWeekId);
    }

    for (int i = 0; i < weeksForCalculation.size(); i++) {
      final var week = weeksForCalculation.get(i);
      final var balance = calculator.calculateBalance(insuranceCase, week, previousWeekBalance);
      if (i < weeksForCalculation.size() - 1) {
        previousWeekBalance = balance;
      } else {
        requestedWeekBalance = balance;
      }
    }
    return requestedWeekBalance;
  }

  private InsuranceBalanceTotalResponse calculateBalance(
      final Long requestedQWeekId, final Long driverId) {
    final var activeCases = caseLoadPort.loadActiveByDriverId(driverId);
    final var calculator = getDryRunStrategy();
    final var balancesCollector = new ArrayList<InsuranceCaseBalance>();
    for (final var insuranceCase : activeCases) {
      final var insuranceCaseBalance =
          calculateBalanceForInsuranceCase(insuranceCase, requestedQWeekId, calculator);
      balancesCollector.add(insuranceCaseBalance);
    }

    final var damageRemainingTotal =
        balancesCollector.stream()
            .map(InsuranceCaseBalance::getDamageRemaining)
            .reduce(ZERO, BigDecimal::add);

    final var selfResponsibilityRemaining =
        balancesCollector.stream()
            .map(InsuranceCaseBalance::getSelfResponsibilityRemaining)
            .reduce(ZERO, BigDecimal::add);

    return InsuranceBalanceTotalResponse.builder()
        .damageRemainingTotal(damageRemainingTotal)
        .selfResponsibilityRemainingTotal(selfResponsibilityRemaining)
        .build();
  }

  private QWeekResponse getStartWeek(
      final InsuranceCase insuranceCase, final InsuranceCaseBalance latestCalculatedBalance) {
    if (latestCalculatedBalance != null) {
      final var latestCalculatedQWeekId = latestCalculatedBalance.getQWeekId();

      return qWeekQuery.getOneAfterById(latestCalculatedQWeekId);
    }
    final var occurrenceDate = insuranceCase.getOccurrenceDate();
    final var occurrenceYear = occurrenceDate.getYear();
    final var occurrenceWeekNumber = getWeekNumber(occurrenceDate);
    final var startQWeek = qWeekQuery.getByYearAndNumber(occurrenceYear, occurrenceWeekNumber);
    if (startQWeek == null) {
      throw new RuntimeException(
          format(
              "No Q-Week found for occurrence date: %s, with year: %d and number: %d",
              occurrenceDate, occurrenceYear, occurrenceWeekNumber));
    }
    return startQWeek;
  }

  private InsuranceCaseBalanceCalculatorStrategy getDryRunStrategy() {
    return calculatorStrategies.stream()
        .filter(strategy -> strategy.canApply(DRY_RUN))
        .findFirst()
        .orElseThrow(
            () ->
                new RuntimeException(
                    "No Insurance Case Balance Calculator strategy found for 'dry run'"));
  }
}
