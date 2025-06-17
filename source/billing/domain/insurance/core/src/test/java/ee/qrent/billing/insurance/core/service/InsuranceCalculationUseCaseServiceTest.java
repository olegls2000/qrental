package ee.qrent.billing.insurance.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_INNER_ROAD_INSURANCE;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
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
  public void testInvalidCalculationBecauseNoRentCalculation() {
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
    assertEquals(qWeekId, request.getQWeekId());
  }

  @Test
  public void testSuccessfulCalculationIfContractIsNewAndNoActiveCassAndRate5() {
    // given
    final var qWeekId = 5L;
    final var driverId = 2L;
    final var today = LocalDate.of(2025, 6, 1);
    final var violationCollector = new ViolationsCollector();
    final var calculationAddRequest = new InsuranceCalculationAddRequest();
    calculationAddRequest.setQWeekId(qWeekId);
    calculationAddRequest.setActionDate(today);

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

    final var qWeek = QWeekResponse.builder().id(qWeekId).year(2025).number(5).build();
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
    when(qDateTime.getToday()).thenReturn(today);
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(emptyList());
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 28))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
                TransactionResponse.builder()
                    .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
                    .realAmount(BigDecimal.valueOf(100))
                    .build()));
    when(boltRidesCountQuery.getRidesCountByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(379);
    when(transactionTypeQuery.getByName(TRANSACTION_TYPE_INNER_ROAD_INSURANCE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.now());
    when(calculationAddPort.add(insuranceCalculation))
        .thenReturn(InsuranceCalculation.builder().id(1L).build());

    // when
    final var id = instanceUnderTest.add(calculationAddRequest);

    // then
    assertNotNull(id);
    assertFalse(violationCollector.hasViolations());
    assertEquals(5L, calculationAddRequest.getQWeekId());

    final var transactionCaptor = forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(1)).add(transactionCaptor.capture());
    final var transactionAddRequest = transactionCaptor.getValue();
    assertEquals(0, BigDecimal.valueOf(5).compareTo(transactionAddRequest.getAmount()));
    assertEquals(today, insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
  }


  //TO check:


  @Test
  public void testSuccessfulCalculationIfContractIsNewAndActiveCasePresentAndRate5() {
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

    final var qWeek = QWeekResponse.builder().id(qWeekId).year(2025).number(5).build();
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
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
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
    assertEquals(5L, calculationAddRequest.getQWeekId());
    assertEquals(BigDecimal.valueOf(0.05), transactionAddRequest.getAmount());
    assertEquals(10L, transactionTypeResponse.getId());
    assertEquals(TRANSACTION_TYPE_INNER_ROAD_INSURANCE, transactionTypeResponse.getName());
    assertEquals(driverId, driver.getId());
    assertEquals("FirstName", driver.getFirstName());
    assertEquals("LastName", driver.getLastName());
    assertEquals(1231231231L, driver.getTaxNumber());
    assertEquals(qWeekId, qWeek.getId());
    assertEquals(2025, qWeek.getYear());
    assertEquals(LocalDate.now().getYear(), qWeek.getYear());
    assertEquals(5, qWeek.getNumber());
    assertEquals(1L, insuranceCase.getId());
    assertEquals(LocalDate.now(), insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
  }

  @Test
  public void testSuccessfulCalculationIfContractIsNewAndActiveCasePresent() {
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
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 28))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
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
    assertEquals(5L, calculationAddRequest.getQWeekId());
    assertEquals(BigDecimal.valueOf(0.05), transactionAddRequest.getAmount());
    assertEquals(10L, transactionTypeResponse.getId());
    assertEquals(TRANSACTION_TYPE_INNER_ROAD_INSURANCE, transactionTypeResponse.getName());
    assertEquals(driverId, driver.getId());
    assertEquals("FirstName", driver.getFirstName());
    assertEquals("LastName", driver.getLastName());
    assertEquals(1231231231L, driver.getTaxNumber());
    assertEquals(qWeekId, qWeek.getId());
    assertEquals(2025, qWeek.getYear());
    assertEquals(LocalDate.now().getYear(), qWeek.getYear());
    assertEquals(5, qWeek.getNumber());
    assertEquals(1L, insuranceCase.getId());
    assertEquals(LocalDate.now(), insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
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
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
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
    assertEquals(5L, calculationAddRequest.getQWeekId());
    assertEquals(BigDecimal.valueOf(0.03), transactionAddRequest.getAmount());
    assertEquals(10L, transactionTypeResponse.getId());
    assertEquals(TRANSACTION_TYPE_INNER_ROAD_INSURANCE, transactionTypeResponse.getName());
    assertEquals(driverId, driver.getId());
    assertEquals("FirstName", driver.getFirstName());
    assertEquals("LastName", driver.getLastName());
    assertEquals(1231231231L, driver.getTaxNumber());
    assertEquals(qWeekId, qWeek.getId());
    assertEquals(2025, qWeek.getYear());
    assertEquals(LocalDate.now().getYear(), qWeek.getYear());
    assertEquals(5, qWeek.getNumber());
    assertEquals(1L, insuranceCase.getId());
    assertEquals(LocalDate.now(), insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
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
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
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
    assertEquals(5L, calculationAddRequest.getQWeekId());
    assertEquals(BigDecimal.valueOf(0.04), transactionAddRequest.getAmount());
    assertEquals(10L, transactionTypeResponse.getId());
    assertEquals(TRANSACTION_TYPE_INNER_ROAD_INSURANCE, transactionTypeResponse.getName());
    assertEquals(driverId, driver.getId());
    assertEquals("FirstName", driver.getFirstName());
    assertEquals("LastName", driver.getLastName());
    assertEquals(1231231231L, driver.getTaxNumber());
    assertEquals(qWeekId, qWeek.getId());
    assertEquals(2025, qWeek.getYear());
    assertEquals(LocalDate.now().getYear(), qWeek.getYear());
    assertEquals(5, qWeek.getNumber());
    assertEquals(1L, insuranceCase.getId());
    assertEquals(LocalDate.now(), insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
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
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(insuranceCase));
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .id(6L)
                .dateStart(LocalDate.of(2025, Month.APRIL, 30))
                .build());
    when(transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            singletonList(
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
    assertEquals(5L, calculationAddRequest.getQWeekId());
    assertEquals(BigDecimal.valueOf(0.04), transactionAddRequest.getAmount());
    assertEquals(10L, transactionTypeResponse.getId());
    assertEquals(TRANSACTION_TYPE_INNER_ROAD_INSURANCE, transactionTypeResponse.getName());
    assertEquals(driverId, driver.getId());
    assertEquals("FirstName", driver.getFirstName());
    assertEquals("LastName", driver.getLastName());
    assertEquals(1231231231L, driver.getTaxNumber());
    assertEquals(qWeekId, qWeek.getId());
    assertEquals(2025, qWeek.getYear());
    assertEquals(LocalDate.now().getYear(), qWeek.getYear());
    assertEquals(5, qWeek.getNumber());
    assertEquals(1L, insuranceCase.getId());
    assertEquals(LocalDate.now(), insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
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
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(singletonList(insuranceCase));
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
    assertEquals(5L, calculationAddRequest.getQWeekId());
    assertEquals(BigDecimal.valueOf(0.05), transactionAddRequest.getAmount());
    assertEquals(10L, transactionTypeResponse.getId());
    assertEquals(TRANSACTION_TYPE_INNER_ROAD_INSURANCE, transactionTypeResponse.getName());
    assertEquals(driverId, driver.getId());
    assertEquals("FirstName", driver.getFirstName());
    assertEquals("LastName", driver.getLastName());
    assertEquals(1231231231L, driver.getTaxNumber());
    assertEquals(qWeekId, qWeek.getId());
    assertEquals(2025, qWeek.getYear());
    assertEquals(LocalDate.now().getYear(), qWeek.getYear());
    assertEquals(5, qWeek.getNumber());
    assertEquals(1L, insuranceCase.getId());
    assertEquals(LocalDate.now(), insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
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
    when(driverQuery.getAll()).thenReturn(singletonList(driver));
    when(caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(emptyList());
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
    assertEquals(5L, calculationAddRequest.getQWeekId());
    assertEquals(BigDecimal.valueOf(0.05), transactionAddRequest.getAmount());
    assertEquals(10L, transactionTypeResponse.getId());
    assertEquals(TRANSACTION_TYPE_INNER_ROAD_INSURANCE, transactionTypeResponse.getName());
    assertEquals(driverId, driver.getId());
    assertEquals("FirstName", driver.getFirstName());
    assertEquals("LastName", driver.getLastName());
    assertEquals(1231231231L, driver.getTaxNumber());
    assertEquals(qWeekId, qWeek.getId());
    assertEquals(2025, qWeek.getYear());
    assertEquals(LocalDate.now().getYear(), qWeek.getYear());
    assertEquals(5, qWeek.getNumber());
    assertEquals(1L, insuranceCase.getId());
    assertEquals(LocalDate.now(), insuranceCalculation.getActionDate());
    assertEquals(qWeekId, insuranceCalculation.getQWeekId());
    assertEquals(new ArrayList<>(), insuranceCalculation.getInsuranceCaseBalances());
    assertNull(insuranceCalculation.getComment());
    assertEquals(1, id);
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
