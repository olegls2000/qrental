package ee.qrent.billing.insurance.core.service;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.groupingBy;

import ee.qrent.billing.insurance.api.out.InsuranceCalculationAddPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrent.billing.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCaseUpdatePort caseUpdatePort;
  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;
  private final AddRequestValidator<InsuranceCalculationAddRequest> addRequestValidator;

  @Transactional
  @Override
  public Long add(final InsuranceCalculationAddRequest request) {
    System.out.println("----> Insurance Cases Balance Calculation started ...");
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var domain = calculationAddRequestMapper.toDomain(request);
    final var qWeekId = request.getQWeekId();
    final var activeCases = caseLoadPort.loadActiveByQWeekId(qWeekId);
    if (!activeCases.isEmpty()) {
      final var qWeek = qWeekQuery.getById(qWeekId);
      final var insuranceCasesGroupByDriver =
          activeCases.stream().collect(groupingBy(InsuranceCase::getDriverId));
      System.out.println(
          format(
              "----> Insurance Cases Balance Calculation. Calculated week: %d - %d ",
              qWeek.getYear(), qWeek.getNumber()));
      insuranceCasesGroupByDriver.forEach(
          (driverId, insuranceCases) -> {
            final var activeCase = insuranceCases.stream().findFirst().get();
            final var requestedWeekBalance =
                insuranceCaseBalanceCalculator.calculateBalance(activeCase, qWeek);
            domain.getInsuranceCaseBalances().add(requestedWeekBalance);
            checkAndDeactivateIfNecessary(requestedWeekBalance, activeCase);
          });
    }
    final var savedCalculation = calculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Insurance Cases Balance Calculation took %d milli seconds \n",
        calculationDuration);

    return savedCalculation.getId();
  }

  private void checkAndDeactivateIfNecessary(
      final InsuranceCaseBalance balance, final InsuranceCase insuranceCase) {
    if (balance.getDamageRemaining().compareTo(ZERO) == 0
        && balance.getSelfResponsibilityRemaining().compareTo(ZERO) == 0) {
      insuranceCase.setActive(false);
      caseUpdatePort.update(insuranceCase);
    }
  }
}
