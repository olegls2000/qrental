package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.response.ContractResponse;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.common.in.time.QDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_DAMAGE_WRITE_OFF_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class SimpleInsuranceStrategyTest {
  private SimpleInsuranceStrategy instanceUnderTest;

  private GetContractQuery contractQuery;
  private InsuranceCaseUpdatePort caseUpdatePort;
  private GetTransactionTypeQuery transactionTypeQuery;
  private TransactionAddUseCase transactionAddUseCase;
  private QDateTime qDateTime;
  private GetQWeekQuery qWeekQuery;

  @BeforeEach
  void init() {
    contractQuery = mock(GetContractQuery.class);
    caseUpdatePort = mock(InsuranceCaseUpdatePort.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    transactionAddUseCase = mock(TransactionAddUseCase.class);
    qDateTime = mock(QDateTime.class);
    qWeekQuery = mock(GetQWeekQuery.class);

    instanceUnderTest =
        new SimpleInsuranceStrategy(
            contractQuery, caseUpdatePort, transactionTypeQuery, transactionAddUseCase, qDateTime);
  }

  @Test
  void testCanApplyIfNoActiveContractByDriveIdAndQWeekId() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var insuranceCase =
        InsuranceCase.builder().occurrenceDate(LocalDate.of(2025, Month.APRIL, 27)).build();

    // when
    final var canApply = instanceUnderTest.canApply(driver, qWeek, insuranceCase);

    // then
    assertFalse(canApply);
  }

  @Test
  void
      testFailedCanApplyIfInsuranceCaseOccurrenceDateAndContractStartDateIsBeforeNewContractsDate() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var insuranceCase =
        InsuranceCase.builder().occurrenceDate(LocalDate.of(2025, Month.APRIL, 27)).build();
    final var contract =
        ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 27)).build();

    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(contract);

    // when
    final var canApply = instanceUnderTest.canApply(driver, qWeek, insuranceCase);

    // then
    assertFalse(canApply);
  }

  @Test
  void
      testSuccessfulCanApplyIfInsuranceCaseOccurrenceDateAndContractStartDateIsEqualNewContractsDate() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var insuranceCase =
        InsuranceCase.builder().occurrenceDate(LocalDate.of(2025, Month.APRIL, 28)).build();
    final var contract =
        ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 28)).build();

    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(contract);

    // when
    final var canApply = instanceUnderTest.canApply(driver, qWeek, insuranceCase);

    // then
    assertTrue(canApply);
  }

  @Test
  void
      testSuccessfulCanApplyIfInsuranceCaseOccurrenceDateAndContractStartDateIsAfterNewContractsDate() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var insuranceCase =
        InsuranceCase.builder().occurrenceDate(LocalDate.of(2025, Month.APRIL, 29)).build();
    final var contract =
        ContractResponse.builder().dateStart(LocalDate.of(2025, Month.APRIL, 29)).build();

    when(qWeekQuery.getById(qWeekId)).thenReturn(qWeek);
    when(contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(contract);

    // when
    final var canApply = instanceUnderTest.canApply(driver, qWeek, insuranceCase);

    // then
    assertTrue(canApply);
  }

  @Test
  void
      testSuccessfulApplyCreateAndSaveDamageWriteOffTransactionAndDamageWriteOffAmountIsEqual600() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var calculation =
        InsuranceCalculation.builder().insuranceCaseBalances(new ArrayList<>()).build();
    final var insuranceCase =
        InsuranceCase.builder().driverId(driverId).damageAmount(BigDecimal.valueOf(600)).build();

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(15L)
            .code(TRANSACTION_TYPE_DAMAGE_WRITE_OFF_CODE)
            .build();

    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_DAMAGE_WRITE_OFF_CODE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2025, Month.APRIL, 3));

    // when
    instanceUnderTest.apply(driver, qWeek, calculation, insuranceCase);

    // then
    final var transactionCaptor = forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(2)).add(transactionCaptor.capture());
    final var transactionAddRequest = transactionCaptor.getValue();

    assertEquals(
        "Automatically created transaction for the damage compensation.",
        transactionAddRequest.getComment());
    assertEquals(driverId, transactionAddRequest.getDriverId());
    assertEquals(BigDecimal.valueOf(600), transactionAddRequest.getAmount());
    assertEquals(15L, transactionAddRequest.getTransactionTypeId());
    assertEquals(LocalDate.of(2025, Month.APRIL, 3), transactionAddRequest.getDate());
  }

  @Test
  void testSuccessfulApplyAndDamageWriteOffAmountLessThan600() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var calculation =
        InsuranceCalculation.builder().insuranceCaseBalances(new ArrayList<>()).build();
    final var insuranceCase =
        InsuranceCase.builder().driverId(driverId).damageAmount(BigDecimal.valueOf(599)).build();

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(15L)
            .code(TRANSACTION_TYPE_DAMAGE_WRITE_OFF_CODE)
            .build();

    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_DAMAGE_WRITE_OFF_CODE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2025, Month.APRIL, 3));

    // when
    instanceUnderTest.apply(driver, qWeek, calculation, insuranceCase);

    // then
    final var transactionCaptor = forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(2)).add(transactionCaptor.capture());
    final var transactionAddRequest = transactionCaptor.getValue();

    assertEquals(BigDecimal.valueOf(599), transactionAddRequest.getAmount());
  }

  @Test
  void
      testSuccessfulApplyCheckAndDeactivateNotNecessaryInsuranceCaseBalanceBecauseDamageRemainingAndSelfResponsibilityRemainingIsEqual0() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var calculation =
        InsuranceCalculation.builder().insuranceCaseBalances(new ArrayList<>()).build();
    final var insuranceCase =
        InsuranceCase.builder().driverId(driverId).damageAmount(BigDecimal.valueOf(600)).build();

    final var transactionTypeResponse =
        TransactionTypeResponse.builder()
            .id(15L)
            .code(TRANSACTION_TYPE_DAMAGE_WRITE_OFF_CODE)
            .build();

    when(transactionTypeQuery.getByCode(TRANSACTION_TYPE_DAMAGE_WRITE_OFF_CODE))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2025, Month.APRIL, 3));

    // when
    instanceUnderTest.apply(driver, qWeek, calculation, insuranceCase);

    // then
    final var insuranceCaseCaptor = forClass(InsuranceCase.class);
    verify(caseUpdatePort, times(1)).update(insuranceCaseCaptor.capture());
    final var insuranceCaseUpdatePort = insuranceCaseCaptor.getValue();

    assertFalse(insuranceCaseUpdatePort.getActive());
  }
}
