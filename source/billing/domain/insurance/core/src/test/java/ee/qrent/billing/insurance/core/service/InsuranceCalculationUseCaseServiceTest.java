package ee.qrent.billing.insurance.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_INNER_ROAD_INSURANCE;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import ee.qrent.billing.transaction.api.in.query.rent.GetRentCalculationQuery;
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
import java.util.Collections;
import java.util.List;

class InsuranceCalculationUseCaseServiceTest {
  private InsuranceCalculationUseCaseService instanceUnderTest;

  private InsuranceCalculationStrategy insuranceCalculationStrategy;
  private GetRentCalculationQuery rentCalculationQuery;
  private InsuranceCaseLoadPort caseLoadPort;
  private InsuranceCalculationAddPort calculationAddPort;
  private InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private GetQWeekQuery qWeekQuery;
  private GetDriverQuery driverQuery;
  private InsuranceCalculationAddRequestValidator addRequestValidator;
  private List<InsuranceCalculationStrategy> insuranceCalculationStrategies;
  private GetContractQuery contractQuery;
  private GetTransactionQuery transactionQuery;
  private GetBoltRidesCountQuery boltRidesCountQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private TransactionAddUseCase transactionAddUseCase;
  private QDateTime qDateTime;

  @BeforeEach
  void init() {
    insuranceCalculationStrategy = mock(InsuranceCalculationStrategy.class);
    rentCalculationQuery = mock(GetRentCalculationQuery.class);
    caseLoadPort = mock(InsuranceCaseLoadPort.class);
    calculationAddPort = mock(InsuranceCalculationAddPort.class);
    calculationAddRequestMapper = mock(InsuranceCalculationAddRequestMapper.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    addRequestValidator = mock(InsuranceCalculationAddRequestValidator.class);
    insuranceCalculationStrategies = List.of(insuranceCalculationStrategy);
    contractQuery = mock(GetContractQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    boltRidesCountQuery = mock(GetBoltRidesCountQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    transactionAddUseCase = mock(TransactionAddUseCase.class);
    qDateTime = mock(QDateTime.class);

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
  public void testIfSuccessfulInsuranceCalculationAndContractNewAndWithActiveCases() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.05));

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .name(TRANSACTION_TYPE_INNER_ROAD_INSURANCE)
            .build();

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

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 28))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            Collections.singletonList(
                TransactionResponse.builder()
                    .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
                    .realAmount(BigDecimal.valueOf(5))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(379);
    when(transactionTypeQuery.getByName(TRANSACTION_TYPE_INNER_ROAD_INSURANCE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.now());
    when(transactionAddUseCase.add(transactionAddRequest)).thenReturn(1L);
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(true);
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(calculationAddRequest.getQWeekId(), 5L);
    assertEquals(transactionAddRequest.getAmount(), BigDecimal.valueOf(0.05));
    assertEquals(transactionTypeResponse.getId(), 10L);
    assertEquals(transactionTypeResponse.getName(), TRANSACTION_TYPE_INNER_ROAD_INSURANCE);
    assertEquals(driver.getId(), driverId);
    assertEquals(driver.getFirstName(), "FirstName");
    assertEquals(driver.getLastName(), "LastName");
    assertEquals(driver.getTaxNumber(), 1231231231L);
    assertEquals(qWeek.getId(), qWeekId);
    assertEquals(qWeek.getYear(), 2025);
    assertEquals(qWeek.getYear(), LocalDate.now().getYear());
    assertEquals(qWeek.getNumber(), 5);
    assertEquals(insuranceCase.getId(), 1L);
    assertEquals(insuranceCalculation.getActionDate(), LocalDate.now());
    assertEquals(insuranceCalculation.getQWeekId(), qWeekId);
    assertEquals(insuranceCalculation.getInsuranceCaseBalances(), new ArrayList<>());
    assertNull(insuranceCalculation.getComment());
    assertEquals(id, 1);
  }

  @Test
  public void testIfRentCalculationBeforeInsuranceBalanceCalculation() {
    // given
    final var qWeekId = 5L;
    final var violationCollector = new ViolationsCollector();
    final var request = new InsuranceCalculationAddRequest();
    request.setQWeekId(qWeekId);
    violationCollector.collect(
        "Rent calculation must be done, before Insurance Balance calculations");

    when(addRequestValidator.validate(request)).thenReturn(violationCollector);
    when(rentCalculationQuery.getLastCalculatedQWeekId()).thenReturn(4L);
    when(qWeekQuery.getById(2L)).thenReturn(QWeekResponse.builder().id(qWeekId).build());
    when(qWeekQuery.getById(request.getQWeekId()))
        .thenReturn(QWeekResponse.builder().id(qWeekId).build());

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
    assertEquals(request.getQWeekId(), qWeekId);
  }

  @Test
  public void
      testIfSuccessfulInsuranceCalculationAndContractNewAfterNewContractsStartDateAndWithActiveCasesWithInsuranceRateBase005() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.05));

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .name(TRANSACTION_TYPE_INNER_ROAD_INSURANCE)
            .build();

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

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            Collections.singletonList(
                TransactionResponse.builder()
                    .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
                    .realAmount(BigDecimal.valueOf(5))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(379);
    when(transactionTypeQuery.getByName(TRANSACTION_TYPE_INNER_ROAD_INSURANCE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.now());
    when(transactionAddUseCase.add(transactionAddRequest)).thenReturn(1L);
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(true);
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(calculationAddRequest.getQWeekId(), 5L);
    assertEquals(transactionAddRequest.getAmount(), BigDecimal.valueOf(0.05));
    assertEquals(transactionTypeResponse.getId(), 10L);
    assertEquals(transactionTypeResponse.getName(), TRANSACTION_TYPE_INNER_ROAD_INSURANCE);
    assertEquals(driver.getId(), driverId);
    assertEquals(driver.getFirstName(), "FirstName");
    assertEquals(driver.getLastName(), "LastName");
    assertEquals(driver.getTaxNumber(), 1231231231L);
    assertEquals(qWeek.getId(), qWeekId);
    assertEquals(qWeek.getYear(), 2025);
    assertEquals(qWeek.getYear(), LocalDate.now().getYear());
    assertEquals(qWeek.getNumber(), 5);
    assertEquals(insuranceCase.getId(), 1L);
    assertEquals(insuranceCalculation.getActionDate(), LocalDate.now());
    assertEquals(insuranceCalculation.getQWeekId(), qWeekId);
    assertEquals(insuranceCalculation.getInsuranceCaseBalances(), new ArrayList<>());
    assertNull(insuranceCalculation.getComment());
    assertEquals(id, 1);
  }

  @Test
  public void
      testIfSuccessfulInsuranceCalculationAndContractNewAfterNewContractsStartDateAndWithActiveCasesWithInsuranceRateBase003() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.03));

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .name(TRANSACTION_TYPE_INNER_ROAD_INSURANCE)
            .build();

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

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            Collections.singletonList(
                TransactionResponse.builder()
                    .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
                    .realAmount(BigDecimal.valueOf(5))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(700);
    when(transactionTypeQuery.getByName(TRANSACTION_TYPE_INNER_ROAD_INSURANCE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.now());
    when(transactionAddUseCase.add(transactionAddRequest)).thenReturn(1L);
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(true);
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(calculationAddRequest.getQWeekId(), 5L);
    assertEquals(transactionAddRequest.getAmount(), BigDecimal.valueOf(0.03));
    assertEquals(transactionTypeResponse.getId(), 10L);
    assertEquals(transactionTypeResponse.getName(), TRANSACTION_TYPE_INNER_ROAD_INSURANCE);
    assertEquals(driver.getId(), driverId);
    assertEquals(driver.getFirstName(), "FirstName");
    assertEquals(driver.getLastName(), "LastName");
    assertEquals(driver.getTaxNumber(), 1231231231L);
    assertEquals(qWeek.getId(), qWeekId);
    assertEquals(qWeek.getYear(), 2025);
    assertEquals(qWeek.getYear(), LocalDate.now().getYear());
    assertEquals(qWeek.getNumber(), 5);
    assertEquals(insuranceCase.getId(), 1L);
    assertEquals(insuranceCalculation.getActionDate(), LocalDate.now());
    assertEquals(insuranceCalculation.getQWeekId(), qWeekId);
    assertEquals(insuranceCalculation.getInsuranceCaseBalances(), new ArrayList<>());
    assertNull(insuranceCalculation.getComment());
    assertEquals(id, 1);
  }

  @Test
  public void
      testIfSuccessfulInsuranceCalculationAndContractNewAfterNewContractsStartDateAndWithActiveCasesWithInsuranceRateBase004() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.04));

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .name(TRANSACTION_TYPE_INNER_ROAD_INSURANCE)
            .build();

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

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            Collections.singletonList(
                TransactionResponse.builder()
                    .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
                    .realAmount(BigDecimal.valueOf(5))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(380);
    when(transactionTypeQuery.getByName(TRANSACTION_TYPE_INNER_ROAD_INSURANCE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.now());
    when(transactionAddUseCase.add(transactionAddRequest)).thenReturn(1L);
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(true);
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(calculationAddRequest.getQWeekId(), 5L);
    assertEquals(transactionAddRequest.getAmount(), BigDecimal.valueOf(0.04));
    assertEquals(transactionTypeResponse.getId(), 10L);
    assertEquals(transactionTypeResponse.getName(), TRANSACTION_TYPE_INNER_ROAD_INSURANCE);
    assertEquals(driver.getId(), driverId);
    assertEquals(driver.getFirstName(), "FirstName");
    assertEquals(driver.getLastName(), "LastName");
    assertEquals(driver.getTaxNumber(), 1231231231L);
    assertEquals(qWeek.getId(), qWeekId);
    assertEquals(qWeek.getYear(), 2025);
    assertEquals(qWeek.getYear(), LocalDate.now().getYear());
    assertEquals(qWeek.getNumber(), 5);
    assertEquals(insuranceCase.getId(), 1L);
    assertEquals(insuranceCalculation.getActionDate(), LocalDate.now());
    assertEquals(insuranceCalculation.getQWeekId(), qWeekId);
    assertEquals(insuranceCalculation.getInsuranceCaseBalances(), new ArrayList<>());
    assertNull(insuranceCalculation.getComment());
    assertEquals(id, 1);
  }

  @Test
  public void
      testIfSuccessfulInsuranceCalculationAndContractNewAfterNewContractsStartDateAndWithActiveCasesWithInsuranceRateBase004_2() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.04));

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .name(TRANSACTION_TYPE_INNER_ROAD_INSURANCE)
            .build();

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

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            Collections.singletonList(
                TransactionResponse.builder()
                    .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
                    .realAmount(BigDecimal.valueOf(5))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(514);
    when(transactionTypeQuery.getByName(TRANSACTION_TYPE_INNER_ROAD_INSURANCE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.now());
    when(transactionAddUseCase.add(transactionAddRequest)).thenReturn(1L);
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(true);
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(calculationAddRequest.getQWeekId(), 5L);
    assertEquals(transactionAddRequest.getAmount(), BigDecimal.valueOf(0.04));
    assertEquals(transactionTypeResponse.getId(), 10L);
    assertEquals(transactionTypeResponse.getName(), TRANSACTION_TYPE_INNER_ROAD_INSURANCE);
    assertEquals(driver.getId(), driverId);
    assertEquals(driver.getFirstName(), "FirstName");
    assertEquals(driver.getLastName(), "LastName");
    assertEquals(driver.getTaxNumber(), 1231231231L);
    assertEquals(qWeek.getId(), qWeekId);
    assertEquals(qWeek.getYear(), 2025);
    assertEquals(qWeek.getYear(), LocalDate.now().getYear());
    assertEquals(qWeek.getNumber(), 5);
    assertEquals(insuranceCase.getId(), 1L);
    assertEquals(insuranceCalculation.getActionDate(), LocalDate.now());
    assertEquals(insuranceCalculation.getQWeekId(), qWeekId);
    assertEquals(insuranceCalculation.getInsuranceCaseBalances(), new ArrayList<>());
    assertNull(insuranceCalculation.getComment());
    assertEquals(id, 1);
  }

  @Test
  public void testIfSuccessfulInsuranceCalculationAndContractNotNewAndWithActiveCases() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.05));

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .name(TRANSACTION_TYPE_INNER_ROAD_INSURANCE)
            .build();

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

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 27))
                .build());
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(true);
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(calculationAddRequest.getQWeekId(), 5L);
    assertEquals(transactionAddRequest.getAmount(), BigDecimal.valueOf(0.05));
    assertEquals(transactionTypeResponse.getId(), 10L);
    assertEquals(transactionTypeResponse.getName(), TRANSACTION_TYPE_INNER_ROAD_INSURANCE);
    assertEquals(driver.getId(), driverId);
    assertEquals(driver.getFirstName(), "FirstName");
    assertEquals(driver.getLastName(), "LastName");
    assertEquals(driver.getTaxNumber(), 1231231231L);
    assertEquals(qWeek.getId(), qWeekId);
    assertEquals(qWeek.getYear(), 2025);
    assertEquals(qWeek.getYear(), LocalDate.now().getYear());
    assertEquals(qWeek.getNumber(), 5);
    assertEquals(insuranceCase.getId(), 1L);
    assertEquals(insuranceCalculation.getActionDate(), LocalDate.now());
    assertEquals(insuranceCalculation.getQWeekId(), qWeekId);
    assertEquals(insuranceCalculation.getInsuranceCaseBalances(), new ArrayList<>());
    assertNull(insuranceCalculation.getComment());
    assertEquals(id, 1);
  }

  @Test
  public void testIfSuccessfulInsuranceCalculationAndContractNotNewAndWithNonActiveCases() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setAmount(BigDecimal.valueOf(0.05));

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(10L)
            .name(TRANSACTION_TYPE_INNER_ROAD_INSURANCE)
            .build();

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

    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
        .thenReturn(insuranceCalculation);
    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.emptyList());
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 27))
                .build());
    when(insuranceCalculationStrategy.canApply(driver, qWeek, insuranceCase)).thenReturn(true);
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(calculationAddRequest.getQWeekId(), 5L);
    assertEquals(transactionAddRequest.getAmount(), BigDecimal.valueOf(0.05));
    assertEquals(transactionTypeResponse.getId(), 10L);
    assertEquals(transactionTypeResponse.getName(), TRANSACTION_TYPE_INNER_ROAD_INSURANCE);
    assertEquals(driver.getId(), driverId);
    assertEquals(driver.getFirstName(), "FirstName");
    assertEquals(driver.getLastName(), "LastName");
    assertEquals(driver.getTaxNumber(), 1231231231L);
    assertEquals(qWeek.getId(), qWeekId);
    assertEquals(qWeek.getYear(), 2025);
    assertEquals(qWeek.getYear(), LocalDate.now().getYear());
    assertEquals(qWeek.getNumber(), 5);
    assertEquals(insuranceCase.getId(), 1L);
    assertEquals(insuranceCalculation.getActionDate(), LocalDate.now());
    assertEquals(insuranceCalculation.getQWeekId(), qWeekId);
    assertEquals(insuranceCalculation.getInsuranceCaseBalances(), new ArrayList<>());
    assertNull(insuranceCalculation.getComment());
    assertEquals(id, 1);
  }

  //  @Test
  //  public void testIfThrowExceptionWhenNoActiveInsuranceCasesFound() {
  //    // given
  //    final var qWeekId = 5L;
  //    final var driverId = 2L;
  //    final var violationCollector = new ViolationsCollector();
  //    final var calculationAddRequest = new InsuranceCalculationAddRequest();
  //    calculationAddRequest.setQWeekId(qWeekId);
  //
  //    final var transactionAddRequest = new TransactionAddRequest();
  //    transactionAddRequest.setAmount(BigDecimal.valueOf(0.05));
  //
  //    final var driver =
  //        DriverResponse.builder()
  //            .id(driverId)
  //            .firstName("FirstName")
  //            .lastName("LastName")
  //            .taxNumber(1231231231L)
  //            .build();
  //
  //    final var qWeek =
  //        QWeekResponse.builder().id(qWeekId).year(LocalDate.now().getYear()).number(5).build();
  //    final var insuranceCase = InsuranceCase.builder().id(1L).build();
  //    final var insuranceCalculation =
  //        InsuranceCalculation.builder()
  //            .actionDate(calculationAddRequest.getActionDate())
  //            .qWeekId(calculationAddRequest.getQWeekId())
  //            .insuranceCaseBalances(new ArrayList<>())
  //            .comment(calculationAddRequest.getComment())
  //            .build();
  //
  //    final var driverInfo =
  //        format(
  //            "Driver: %s %s, tax number: %d",
  //            driver.getFirstName(), driver.getLastName(), driver.getTaxNumber());
  //    final var weekInfo = format("QWeek: %d - %d", qWeek.getYear(), qWeek.getNumber());
  //
  //    when(addRequestValidator.validate(calculationAddRequest)).thenReturn(violationCollector);
  //    when(calculationAddRequestMapper.toDomain(calculationAddRequest))
  //        .thenReturn(insuranceCalculation);
  //    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
  //    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
  //    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
  //        .thenReturn(Collections.emptyList());
  //    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
  //        .thenReturn(
  //            ContractResponse.builder()
  //                .id(6L)
  //                .dateStart(LocalDate.of(2025, Month.APRIL, 27))
  //                .build());
  //
  //    // when
  //    Exception exception = null;
  //    try {
  //      instanceUnderTest.add(calculationAddRequest);
  //    } catch (Exception e) {
  //      exception = e;
  //    }
  //
  //    // then
  //    assertNotNull(exception);
  //    assertEquals(RuntimeException.class, exception.getClass());
  //    assertEquals(
  //        exception.getMessage(),
  //        format("No active insurance cases found for %s and %s", driverInfo, weekInfo));
  //  }

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
    when(driverQuery.getAll()).thenReturn(Collections.singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(Collections.singletonList(insuranceCase));
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
