package ee.qrent.billing.insurance.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.core.validator.InsuranceCaseCloseRequestValidator;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InsuranceCaseCloseUseCaseServiceTest {

  private InsuranceCaseCloseUseCaseService instanceUnderTest;

  private InsuranceCaseUpdatePort updatePort;
  private InsuranceCaseLoadPort loadPort;
  private InsuranceCaseCloseRequestValidator closeRuleValidator;
  private GetQKaskoQuery getQKaskoQuery;
  private GetQWeekQuery qWeekQuery;
  private GetDriverQuery driverQuery;
  private GetTransactionQuery transactionQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private QDateTime qDateTime;
  private TransactionAddUseCase transactionAddUseCase;

  @BeforeEach
  void setUp() {
    updatePort = mock(InsuranceCaseUpdatePort.class);
    loadPort = mock(InsuranceCaseLoadPort.class);
    closeRuleValidator = mock(InsuranceCaseCloseRequestValidator.class);
    getQKaskoQuery = mock(GetQKaskoQuery.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    qDateTime = mock(QDateTime.class);
    transactionAddUseCase = mock(TransactionAddUseCase.class);
    instanceUnderTest =
        new InsuranceCaseCloseUseCaseService(
            updatePort,
            loadPort,
            closeRuleValidator,
            getQKaskoQuery,
            qWeekQuery,
            driverQuery,
            transactionQuery,
            transactionTypeQuery,
            qDateTime,
            transactionAddUseCase);
  }

  @Test
  public void testPreCloseResponseWithoutQKasko() {
    // given
    final var insuranceCaseId = 55L;
    final var driverId = 44L;
    final var qWeekId = 33L;
    when(loadPort.loadById(insuranceCaseId))
        .thenReturn(
            InsuranceCase.builder()
                .id(insuranceCaseId)
                .damageAmount(BigDecimal.valueOf(3000))
                .driverId(driverId)
                .build());
    when(driverQuery.getObjectInfo(driverId)).thenReturn("Driver Object Info");
    when(qWeekQuery.getCurrentWeek()).thenReturn(QWeekResponse.builder().id(qWeekId).build());
    when(getQKaskoQuery.hasQKasko(driverId, qWeekId)).thenReturn(FALSE);
    when(transactionQuery.getAllByInsuranceCaseId(insuranceCaseId))
        .thenReturn(
            asList(
                TransactionResponse.builder().realAmount(BigDecimal.valueOf(-310)).build(),
                TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));

    // when
    final var result = instanceUnderTest.getPreCloseResponse(insuranceCaseId);

    // then
    assertNotNull(result);
    assertEquals(55L, result.getInsuranceCaseId());
    assertEquals(44L, result.getDriverId());
    assertEquals("Driver Object Info", result.getDriverInfo());
    assertEquals(false, result.getWithQKasko());
    assertEquals(BigDecimal.valueOf(3000), result.getOriginalAmount());
    assertEquals(BigDecimal.valueOf(510), result.getPaidAmount());
    assertTrue(BigDecimal.valueOf(2490).compareTo(result.getPaymentAmount()) == 0);
  }

  @Test
  public void testPreCloseResponseWithQKasko() {
    // given
    final var insuranceCaseId = 55L;
    final var driverId = 44L;
    final var qWeekId = 33L;
    when(loadPort.loadById(insuranceCaseId))
        .thenReturn(
            InsuranceCase.builder()
                .id(insuranceCaseId)
                .damageAmount(BigDecimal.valueOf(3500))
                .driverId(driverId)
                .build());
    when(driverQuery.getObjectInfo(driverId)).thenReturn("Driver Object Info");
    when(qWeekQuery.getCurrentWeek()).thenReturn(QWeekResponse.builder().id(qWeekId).build());
    when(getQKaskoQuery.hasQKasko(driverId, qWeekId)).thenReturn(TRUE);
    when(transactionQuery.getAllByInsuranceCaseId(insuranceCaseId))
        .thenReturn(
            asList(
                TransactionResponse.builder().realAmount(BigDecimal.valueOf(-310)).build(),
                TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));

    // when
    final var result = instanceUnderTest.getPreCloseResponse(insuranceCaseId);

    // then
    assertNotNull(result);
    assertEquals(55L, result.getInsuranceCaseId());
    assertEquals(44L, result.getDriverId());
    assertEquals("Driver Object Info", result.getDriverInfo());
    assertEquals(true, result.getWithQKasko());
    assertEquals(BigDecimal.valueOf(3500), result.getOriginalAmount());
    assertEquals(BigDecimal.valueOf(510), result.getPaidAmount());
    assertTrue(BigDecimal.valueOf(990).compareTo(result.getPaymentAmount()) == 0);
  }

  @Test
  public void testPreCloseResponseWithQKaskoButLessThenSelfResponsibility() {
    // given
    final var insuranceCaseId = 55L;
    final var driverId = 44L;
    final var qWeekId = 33L;
    when(loadPort.loadById(insuranceCaseId))
        .thenReturn(
            InsuranceCase.builder()
                .id(insuranceCaseId)
                .damageAmount(BigDecimal.valueOf(500))
                .driverId(driverId)
                .build());
    when(driverQuery.getObjectInfo(driverId)).thenReturn("Driver Object Info");
    when(qWeekQuery.getCurrentWeek()).thenReturn(QWeekResponse.builder().id(qWeekId).build());
    when(getQKaskoQuery.hasQKasko(driverId, qWeekId)).thenReturn(TRUE);
    when(transactionQuery.getAllByInsuranceCaseId(insuranceCaseId))
        .thenReturn(Collections.emptyList());

    // when
    final var result = instanceUnderTest.getPreCloseResponse(insuranceCaseId);

    // then
    assertNotNull(result);
    assertEquals(55L, result.getInsuranceCaseId());
    assertEquals(44L, result.getDriverId());
    assertEquals("Driver Object Info", result.getDriverInfo());
    assertEquals(true, result.getWithQKasko());
    assertEquals(BigDecimal.valueOf(500), result.getOriginalAmount());
    assertEquals(BigDecimal.valueOf(0), result.getPaidAmount());
    assertTrue(BigDecimal.valueOf(500).compareTo(result.getPaymentAmount()) == 0);
  }
}
