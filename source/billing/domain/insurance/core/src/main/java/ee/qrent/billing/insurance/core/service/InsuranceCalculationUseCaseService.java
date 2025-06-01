package ee.qrent.billing.insurance.core.service;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.groupingBy;

import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.out.InsuranceCalculationAddPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.core.service.strategy.InsuranceCalculationStrategy;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrent.billing.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;

  private final AddRequestValidator<InsuranceCalculationAddRequest> addRequestValidator;
  private final List<InsuranceCalculationStrategy> insuranceCalculationStrategies;

  @Transactional
  @Override
  public Long add(final InsuranceCalculationAddRequest request) {
    System.out.println("----> Insurance Cases Calculation started ...");
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var domain = calculationAddRequestMapper.toDomain(request);
    final var qWeekId = request.getQWeekId();
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var drivers = driverQuery.getAll();
    for (var driver : drivers) {
      insuranceCalculationStrategies.stream()
          .filter(strategy -> strategy.canApply(driver, qWeek))
          .findFirst()
          .orElseThrow(
              () ->
                  new RuntimeException(
                      format(
                          "No Insurance calculation Strategy were found for the driver.taxNumber: %d",
                          driver.getTaxNumber())))
          .apply(driver, qWeek, domain);
    }

    final var savedCalculation = calculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Insurance Cases Calculation took %d milli seconds \n", calculationDuration);

    return savedCalculation.getId();
  }
}
