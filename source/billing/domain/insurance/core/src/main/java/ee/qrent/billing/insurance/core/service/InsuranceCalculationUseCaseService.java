package ee.qrent.billing.insurance.core.service;

import static ee.qrent.billing.insurance.core.service.strategy.InsuranceCalculationStrategy.NEW_CONTRACTS_START_DATE;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;


import ee.qrent.billing.bolt.api.in.query.GetBoltRidesCountQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.out.InsuranceCalculationAddPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.core.service.strategy.InsuranceCalculationStrategy;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrent.billing.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final AddRequestValidator<InsuranceCalculationAddRequest> addRequestValidator;
  private final List<InsuranceCalculationStrategy> insuranceCalculationStrategies;
  private final GetContractQuery contractQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetBoltRidesCountQuery boltRidesCountQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final TransactionAddUseCase transactionAddUseCase;
  private final QDateTime qDateTime;

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
      final var driverId = driver.getId();
      final var activeCases = caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId);
      final var driverInfo =
          format(
              "Driver: %s %s, tax number: %d",
              driver.getFirstName(), driver.getLastName(), driver.getTaxNumber());
      final var weekInfo = format("QWeek: %d - %d", qWeek.getYear(), qWeek.getNumber());
      addWeeklyInsurancePaymentTransactionIfNecessary(driverId, qWeekId);
      if (activeCases.isEmpty()) {
        System.out.println(format("No Active insurance cases for %s and %s", driverInfo, weekInfo));

        continue;
      }

      final var activeCaseForProcessing = activeCases.stream().findFirst().get();

      insuranceCalculationStrategies.stream()
          .filter(strategy -> strategy.canApply(driver, qWeek, activeCaseForProcessing))
          .findFirst()
          .orElseThrow(
              () ->
                  new RuntimeException(
                      format(
                          "No Insurance calculation Strategy were found for the %s", driverInfo)))
          .apply(driver, qWeek, domain, activeCaseForProcessing);
    }

    final var savedCalculation = calculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Insurance Cases Calculation took %d milli seconds \n", calculationDuration);

    return savedCalculation.getId();
  }

  private void addWeeklyInsurancePaymentTransactionIfNecessary(
      final Long driverId, final Long qWeekId) {
    final var contract = contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (contract == null) {
      return;
    }
    final var contractStartDate = contract.getDateStart();

    final var isContractNew =
        contractStartDate.isEqual(NEW_CONTRACTS_START_DATE)
            || contractStartDate.isAfter(NEW_CONTRACTS_START_DATE);

    if (isContractNew) {
      final var transactionAddRequest = getWeeklyPaymentTransaction(driverId, qWeekId);
      transactionAddUseCase.add(transactionAddRequest);
    }
  }

  private TransactionAddRequest getWeeklyPaymentTransaction(
      final Long driverId, final Long qWeekId) {
    final var rentAmount =
        transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            .filter(
                transactionResponse ->
                    TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE.equals(transactionResponse.getTypeCode()))
            .map(tr -> tr.getRealAmount())
            .reduce(BigDecimal::add)
            .orElse(ZERO);

    final var insuranceRate = getInsuranceRateBaseOnBoltRides(driverId, qWeekId);
    final var transactionAmount = rentAmount.multiply(insuranceRate);

    final var insurancePaymentTransaction = new TransactionAddRequest();
    insurancePaymentTransaction.setComment("Weekly Insurance payment for the new drivers");
    insurancePaymentTransaction.setDriverId(driverId);
    insurancePaymentTransaction.setAmount(transactionAmount);
    final var transactionTypeId =
        transactionTypeQuery.getByCode(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE).getId();
    insurancePaymentTransaction.setTransactionTypeId(transactionTypeId);
    insurancePaymentTransaction.setDate(qDateTime.getToday());

    return insurancePaymentTransaction;
  }

  private BigDecimal getInsuranceRateBaseOnBoltRides(final Long driverId, final Long qWeekId) {
    final var boltRidesCount =
        boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId);
    if (boltRidesCount < 380) {

      return BigDecimal.valueOf(0.05);
    } else if (boltRidesCount >= 380 && boltRidesCount <= 514) {

      return BigDecimal.valueOf(0.04);
    }
    return BigDecimal.valueOf(0.03);
  }
}
