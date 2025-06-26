package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.response.ContractResponse;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
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

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_DAMAGE_WRITE_OFF;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_INNER_ROAD_INSURANCE;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
  void testCannotApplyIfInsuranceCaseOccurrenceDateAndContractStartDateIsBeforeNewContractsDate() {
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
  void testCanApplyIfInsuranceCaseOccurrenceDateAndContractStartDateIsEqualNewContractsDate() {
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
  void testCanApplyIfInsuranceCaseOccurrenceDateAndContractStartDateIsAfterNewContractsDate() {
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
  void testApply() {
    // given
    final var driverId = 3L;
    final var qWeekId = 10L;

    final var driver = DriverResponse.builder().id(driverId).build();
    final var qWeek = QWeekResponse.builder().id(qWeekId).build();
    final var calculation =
        InsuranceCalculation.builder()
            .insuranceCaseBalances(
                new ArrayList<>(singletonList(InsuranceCaseBalance.builder().build())))
            .build();
    final var insuranceCase = InsuranceCase.builder().damageAmount(BigDecimal.valueOf(600)).build();

    final var transactionTypeResponse =
        TransactionTypeResponse.builder().id(10L).name(TRANSACTION_TYPE_DAMAGE_WRITE_OFF).build();

    final var transactionAddRequest = new TransactionAddRequest();
    transactionAddRequest.setComment("Automatically created transaction for the damage compensation.");
    transactionAddRequest.setAmount(BigDecimal.valueOf(600));
    // ....

    when(transactionTypeQuery.getByName(TRANSACTION_TYPE_DAMAGE_WRITE_OFF))
        .thenReturn(transactionTypeResponse);
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2025, Month.APRIL, 3));
    //when(transactionAddUseCase.add()).thenReturn();

    // when
    instanceUnderTest.apply(driver, qWeek, calculation, insuranceCase);

    // then
  }
}
