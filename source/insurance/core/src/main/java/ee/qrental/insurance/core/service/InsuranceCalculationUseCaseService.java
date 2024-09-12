package ee.qrental.insurance.core.service;

import static java.util.stream.Collectors.groupingBy;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceCalculator;
import ee.qrental.insurance.core.validator.InsuranceCalculationAddBusinessRuleValidator;
import ee.qrental.insurance.domain.InsuranceCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;
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
    final var domain = calculationAddRequestMapper.toDomain(request);
    final var qWeekId = request.getQWeekId();
    final var activeCases = caseLoadPort.loadActiveByQWeekId(qWeekId);
    if (activeCases.isEmpty()) {
      return null;
    }
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var insuranceCasesGroupByDriver =
        activeCases.stream().collect(groupingBy(InsuranceCase::getDriverId));
    System.out.println(
        "----> Insurance Cases Balance Calculation. Calculated week: "
            + qWeek.getYear()
            + "-"
            + qWeek.getNumber());

    insuranceCasesGroupByDriver.forEach(
        (driverId, insuranceCases) -> {
          final var activeCase = insuranceCases.stream().findFirst().get();
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
}
