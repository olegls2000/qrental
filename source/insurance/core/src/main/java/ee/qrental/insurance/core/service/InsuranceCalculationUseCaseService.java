package ee.qrental.insurance.core.service;

import static ee.qrental.constant.api.in.query.GetQWeekQuery.DEFAULT_COMPARATOR;

import ee.qrental.constant.api.in.query.GetQWeekQuery;

import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceCalculator;
import ee.qrental.insurance.core.validator.InsuranceCalculationAddBusinessRuleValidator;

import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCalculationLoadPort calculationLoadPort;
  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;
  private final GetBalanceQuery balanceQuery;
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
    final var startWeekId = getStartWeekId();
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

  private Long getStartWeekId() {
    final var latestCalculatedBalanceQWeek = getLatestBalanceCalculatedQWeek();
    final var lastInsuranceCalculatedQWeek = getLatestInsuranceCalculatedQWeek();

    final var startWeek =
        getLatestQWeek(latestCalculatedBalanceQWeek, lastInsuranceCalculatedQWeek);

    return startWeek.getId();
  }

  private QWeekResponse getLatestQWeek(final QWeekResponse qWeek1, final QWeekResponse qWeek2) {
    if (qWeek1 == null) {
      return qWeek2;
    }
    if (qWeek2 == null) {
      return qWeek1;
    }

    if (qWeek1.compareTo(qWeek2) >= 0) {
      return qWeek1;
    }
    return qWeek2;
  }

  private QWeekResponse getLatestInsuranceCalculatedQWeek() {
    final var lastCalculatedQWeekId = calculationLoadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {
      return qWeekQuery.getFirstWeek();
    }
    return qWeekQuery.getOneAfterById(lastCalculatedQWeekId);
  }

  private QWeekResponse getLatestBalanceCalculatedQWeek() {
    final var latestCalculatedBalance = balanceQuery.getLatest();
    if (latestCalculatedBalance == null) {
      return null;
    }
    return qWeekQuery.getOneAfterById(latestCalculatedBalance.getQWeekId());
  }

  private Long getEndWeekId(final InsuranceCalculationAddRequest request) {
    return request.getQWeekId();
  }
}
