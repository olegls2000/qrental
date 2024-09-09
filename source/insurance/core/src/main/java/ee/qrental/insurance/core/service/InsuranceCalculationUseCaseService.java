package ee.qrental.insurance.core.service;

import static ee.qrental.constant.api.in.query.GetQWeekQuery.DEFAULT_COMPARATOR;

import ee.qrental.constant.api.in.query.GetQWeekQuery;

import ee.qrental.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceCalculator;
import ee.qrental.insurance.core.validator.InsuranceCalculationAddBusinessRuleValidator;

import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;
  private final GetInsuranceCalculationQuery insuranceCalculationQuery;
  private final InsuranceCalculationAddBusinessRuleValidator addBusinessRuleValidator;

  @Transactional
  @Override
  public Long add(final InsuranceCalculationAddRequest request) {
    System.out.println("----> Insurance Cases Balance Calculation started ...");
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addBusinessRuleValidator.validateAdd(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var startWeekId = insuranceCalculationQuery.getStartQWeekId();
    final var endWeekId = getEndWeekId(request);
    final var domain = calculationAddRequestMapper.toDomain(request);
    domain.setStartQWeekId(startWeekId);
    final var weeksForCalculation =
        qWeekQuery.getQWeeksFromPeriodOrdered(startWeekId, endWeekId, DEFAULT_COMPARATOR);
    weeksForCalculation.forEach(
        qWeek -> {
          final var qWeekId = qWeek.getId();
          final var activeCases = caseLoadPort.loadActiveByQWeekId(qWeekId);
          if (activeCases.isEmpty()) {
            return;
          }
          final var activeCase = activeCases.stream().findFirst().get();
          System.out.println(
              "----> Insurance Cases Balance Calculation. Calculated week: "
                  + qWeek.getYear()
                  + "-"
                  + qWeek.getNumber());
          final var requestedWeekBalance =
              insuranceCaseBalanceCalculator.calculateBalance(activeCase, qWeek);
          domain.getInsuranceCaseBalances().add(requestedWeekBalance);
        });
    final var savedCalculation = calculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Insurance Cases Balance Calculation took %d milli seconds \n",
        calculationDuration);

    return savedCalculation.getId();
  }

  private Long getEndWeekId(final InsuranceCalculationAddRequest request) {
    return request.getQWeekId();
  }
}
