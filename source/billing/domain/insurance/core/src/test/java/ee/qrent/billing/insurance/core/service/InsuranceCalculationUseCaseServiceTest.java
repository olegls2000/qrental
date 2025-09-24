package ee.qrent.billing.insurance.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import ee.qrent.billing.bolt.api.in.query.GetBoltRidesCountQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.response.ContractResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.api.out.InsuranceCalculationAddPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrent.billing.insurance.core.service.strategy.InsuranceCalculationStrategy;
import ee.qrent.billing.insurance.core.validator.InsuranceCalculationAddRequestValidator;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.ViolationsCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

class InsuranceCalculationUseCaseServiceTest {
  private InsuranceCalculationUseCaseService instanceUnderTest;

  private InsuranceCalculationStrategy insuranceCalculationStrategy;
  private InsuranceCaseLoadPort caseLoadPort;
  private InsuranceCalculationAddPort calculationAddPort;
  private InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private GetQWeekQuery qWeekQuery;
  private GetDriverQuery driverQuery;
  private InsuranceCalculationAddRequestValidator addRequestValidator;
  private GetContractQuery contractQuery;
  private GetTransactionQuery transactionQuery;
  private GetBoltRidesCountQuery boltRidesCountQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private TransactionAddUseCase transactionAddUseCase;

  @BeforeEach
  void init() {
    insuranceCalculationStrategy = mock(InsuranceCalculationStrategy.class);
    caseLoadPort = mock(InsuranceCaseLoadPort.class);
    calculationAddPort = mock(InsuranceCalculationAddPort.class);
    calculationAddRequestMapper = mock(InsuranceCalculationAddRequestMapper.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    addRequestValidator = mock(InsuranceCalculationAddRequestValidator.class);
    List<InsuranceCalculationStrategy> insuranceCalculationStrategies =
        List.of(insuranceCalculationStrategy);
    contractQuery = mock(GetContractQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    boltRidesCountQuery = mock(GetBoltRidesCountQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    transactionAddUseCase = mock(TransactionAddUseCase.class);
    QDateTime qDateTime = mock(QDateTime.class);

    when(insuranceCalculationStrategy.canApply(
            any(DriverResponse.class), any(QWeekResponse.class), any(InsuranceCase.class)))
        .thenReturn(true);

    doNothing()
        .when(insuranceCalculationStrategy)
        .apply(
            any(DriverResponse.class),
            any(QWeekResponse.class),
            any(InsuranceCalculation.class),
            any(InsuranceCase.class));

    instanceUnderTest =
        new InsuranceCalculationUseCaseService(
            caseLoadPort,
            calculationAddPort,
            calculationAddRequestMapper,
            qWeekQuery,
            driverQuery,
            addRequestValidator,
            insuranceCalculationStrategies,
            contractQuery,
            transactionQuery,
            boltRidesCountQuery,
            transactionTypeQuery,
            transactionAddUseCase,
            qDateTime);
  }

  @Test
  public void testInvalidCalculationBecauseNoRentCalculation() {
    // given
    final var qWeekId = 5L;
    final var violationCollector = new ViolationsCollector();
    final var request = new InsuranceCalculationAddRequest();
    request.setQWeekId(qWeekId);
    violationCollector.collect(
        "Rent calculation must be done, before Insurance Balance calculations");
    when(addRequestValidator.validate(request)).thenReturn(violationCollector);

    // when
    final var id = instanceUnderTest.add(request);

    // then
    assertTrue(request.hasViolations());
    assertTrue(
        request.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Rent calculation must be done, before Insurance Balance calculations")));
    assertNull(id);
  }

  @Test
  public void testSuccessfulCalculationIfContractIsNewAndNoActiveCasesAndRate5() {
    // given
    final var qWeekId = 155L;
    final var request = new InsuranceCalculationAddRequest();
    request.setQWeekId(qWeekId);
    final var driverId = 1L;

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .code(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE)
            .build();

    final var driver =
        DriverResponse.builder()
            .id(driverId)
            .firstName("FirstName")
            .lastName("LastName")
            .taxNumber(1231231231L)
            .build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).year(2025).number(5).build();
    final var insuranceCalculation =
        InsuranceCalculation.builder()
            .qWeekId(qWeekId)
            .insuranceCaseBalances(new ArrayList<>())
            .build();

    when(addRequestValidator.validate(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(new ViolationsCollector());
    when(calculationAddRequestMapper.toDomain(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(emptyList());
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 28)).build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
                TransactionResponse.builder()
                    .typeCode(TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE)
                    .realAmount(BigDecimal.valueOf(100))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(379);
    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE))
        .thenReturn(transactionTypeResponse);
    when(calculationAddPort.add(any())).thenReturn(InsuranceCalculation.builder().id(99L).build());

    // when
    instanceUnderTest.add(request);

    // then
    final var transactionCaptor = forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(1)).add(transactionCaptor.capture());
    final var transactionAddRequest = transactionCaptor.getValue();
    assertEquals(0, BigDecimal.valueOf(5).compareTo(transactionAddRequest.getAmount()));
  }

  @Test
  public void testSuccessfulCalculationIfContractIsNewAndNoActiveCasesAndRate4() {
    // given
    final var qWeekId = 155L;
    final var request = new InsuranceCalculationAddRequest();
    request.setQWeekId(qWeekId);
    final var driverId = 1L;

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .code(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE)
            .build();

    final var driver =
        DriverResponse.builder()
            .id(driverId)
            .firstName("FirstName")
            .lastName("LastName")
            .taxNumber(1231231231L)
            .build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).year(2025).number(5).build();
    final var insuranceCalculation =
        InsuranceCalculation.builder()
            .qWeekId(qWeekId)
            .insuranceCaseBalances(new ArrayList<>())
            .build();

    when(addRequestValidator.validate(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(new ViolationsCollector());
    when(calculationAddRequestMapper.toDomain(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(emptyList());
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 28)).build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
                TransactionResponse.builder()
                    .typeCode(TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE)
                    .realAmount(BigDecimal.valueOf(100))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(380);
    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE))
        .thenReturn(transactionTypeResponse);
    when(calculationAddPort.add(any())).thenReturn(InsuranceCalculation.builder().id(99L).build());

    // when
    instanceUnderTest.add(request);

    // then
    final var transactionCaptor = forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(1)).add(transactionCaptor.capture());
    final var transactionAddRequest = transactionCaptor.getValue();
    assertEquals(0, BigDecimal.valueOf(4).compareTo(transactionAddRequest.getAmount()));
  }

  @Test
  public void testSuccessfulCalculationIfContractIsNewAndNoActiveCasesAndRate3() {
    // given
    final var qWeekId = 155L;
    final var request = new InsuranceCalculationAddRequest();
    request.setQWeekId(qWeekId);
    final var driverId = 1L;

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .code(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE)
            .build();

    final var driver =
        DriverResponse.builder()
            .id(driverId)
            .firstName("FirstName")
            .lastName("LastName")
            .taxNumber(1231231231L)
            .build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).year(2025).number(5).build();
    final var insuranceCalculation =
        InsuranceCalculation.builder()
            .qWeekId(qWeekId)
            .insuranceCaseBalances(new ArrayList<>())
            .build();

    when(addRequestValidator.validate(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(new ViolationsCollector());
    when(calculationAddRequestMapper.toDomain(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(emptyList());
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 28)).build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
                TransactionResponse.builder()
                    .typeCode(TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE)
                    .realAmount(BigDecimal.valueOf(100))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(515);
    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE))
        .thenReturn(transactionTypeResponse);
    when(calculationAddPort.add(any())).thenReturn(InsuranceCalculation.builder().id(99L).build());

    // when
    instanceUnderTest.add(request);

    // then
    final var transactionCaptor = forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(1)).add(transactionCaptor.capture());
    final var transactionAddRequest = transactionCaptor.getValue();
    assertEquals(0, BigDecimal.valueOf(3).compareTo(transactionAddRequest.getAmount()));
  }

  @Test
  public void testSuccessfulCalculationIfContractIsOldAndNoActiveCases() {
    // given
    final var qWeekId = 155L;
    final var request = new InsuranceCalculationAddRequest();
    request.setQWeekId(qWeekId);
    final var driverId = 1L;

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .code(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE)
            .build();

    final var driver =
        DriverResponse.builder()
            .id(driverId)
            .firstName("FirstName")
            .lastName("LastName")
            .taxNumber(1231231231L)
            .build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).year(2025).number(5).build();
    final var insuranceCalculation =
        InsuranceCalculation.builder()
            .qWeekId(qWeekId)
            .insuranceCaseBalances(new ArrayList<>())
            .build();

    when(addRequestValidator.validate(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(new ViolationsCollector());
    when(calculationAddRequestMapper.toDomain(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(emptyList());
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 27)).build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
                TransactionResponse.builder()
                    .typeCode(TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE)
                    .realAmount(BigDecimal.valueOf(100))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(515);
    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE))
        .thenReturn(transactionTypeResponse);
    when(calculationAddPort.add(any())).thenReturn(InsuranceCalculation.builder().id(99L).build());

    // when
    instanceUnderTest.add(request);

    // then
    verify(transactionAddUseCase, times(0)).add(any(TransactionAddRequest.class));
  }

  @Test
  public void testSuccessfulCalculationIfContractIsOldAndActiveCases() {
    // given
    final var qWeekId = 155L;
    final var request = new InsuranceCalculationAddRequest();
    request.setQWeekId(qWeekId);
    final var driverId = 1L;

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .code(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE)
            .build();

    final var driver =
        DriverResponse.builder()
            .id(driverId)
            .firstName("FirstName")
            .lastName("LastName")
            .taxNumber(1231231231L)
            .build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).year(2025).number(5).build();
    final var insuranceCalculation =
        InsuranceCalculation.builder()
            .qWeekId(qWeekId)
            .insuranceCaseBalances(new ArrayList<>())
            .build();

    when(addRequestValidator.validate(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(new ViolationsCollector());
    when(calculationAddRequestMapper.toDomain(any(InsuranceCalculationAddRequest.class)))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(emptyList());
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 27)).build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
                TransactionResponse.builder()
                    .typeCode(TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE)
                    .realAmount(BigDecimal.valueOf(100))
                    .build()));
    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE))
        .thenReturn(transactionTypeResponse);
    when(calculationAddPort.add(any())).thenReturn(InsuranceCalculation.builder().id(99L).build());
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(InsuranceCase.builder().id(1L).build()));

    // when
    instanceUnderTest.add(request);

    // then
    verify(transactionAddUseCase, times(0)).add(any(TransactionAddRequest.class));
    verify(insuranceCalculationStrategy, times(1))
        .canApply(any(DriverResponse.class), any(QWeekResponse.class), any(InsuranceCase.class));
    verify(insuranceCalculationStrategy, times(1))
        .apply(
            any(DriverResponse.class),
            any(QWeekResponse.class),
            any(InsuranceCalculation.class),
            any(InsuranceCase.class));
  }

  @Test
  public void testIfThrowExceptionWhenNoInsuranceCalculationStrategyFound() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.05));

    final var driver =
        DriverResponse.builder()
            .id(driverId)
            .firstName("FirstName")
            .lastName("LastName")
            .taxNumber(1231231231L)
            .build();

    final var qWeek =
        QWeekResponse.builder().id(qWeekId).year(LocalDate.now().getYear()).number(5).build();
    final var insuranceCase = InsuranceCase.builder().id(1L).build();
    final var insuranceCalculation =
        InsuranceCalculation.builder()
            .actionDate(calculationAddRequest.getActionDate())
            .qWeekId(calculationAddRequest.getQWeekId())
            .insuranceCaseBalances(new ArrayList<>())
            .comment(calculationAddRequest.getComment())
            .build();

    final var driverInfo =
        format(
            "Driver: %s %s, tax number: %d",
            driver.getFirstName(), driver.getLastName(), driver.getTaxNumber());

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 27))
                .build());
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(false);

    // when
    Exception exception = null;
    try {
      instanceUnderTest.add(calculationAddRequest);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertNotNull(exception);
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals(
        exception.getMessage(),
        format("No Insurance calculation Strategy were found for the %s", driverInfo));
  }
}
