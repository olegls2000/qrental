package ee.qrental.transaction.core.service.balance;

import static java.math.BigDecimal.ZERO;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrental.transaction.api.in.response.kind.TransactionKindResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.kind.TransactionKind;
import ee.qrental.transaction.domain.type.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceQueryServiceTest {

  private GetBalanceQuery instanceUnderTest;
  private GetDriverQuery driverQuery;
  private GetQWeekQuery qWeekQuery;
  private GetConstantQuery constantQuery;
  private GetTransactionQuery transactionQuery;
  private GetTransactionKindQuery transactionKindQuery;
  private BalanceLoadPort balanceLoadPort;
  private TransactionLoadPort transactionLoadPort;
  private BalanceResponseMapper balanceResponseMapper;
  private BalanceCalculatorStrategy calculatorStrategies;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    constantQuery = mock(GetConstantQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    transactionKindQuery = mock(GetTransactionKindQuery.class);
    balanceLoadPort = mock(BalanceLoadPort.class);
    transactionLoadPort = mock(TransactionLoadPort.class);
    balanceResponseMapper = mock(BalanceResponseMapper.class);
    calculatorStrategies = mock(BalanceCalculatorStrategy.class);

    instanceUnderTest =
        new BalanceQueryService(
            driverQuery,
            qWeekQuery,
            constantQuery,
            transactionQuery,
            transactionKindQuery,
            balanceLoadPort,
            transactionLoadPort,
            balanceResponseMapper,
            Arrays.asList(calculatorStrategies));
  }

  private TransactionType getTransactionType(
      final Long transactionKindId, final String transactionKindCode) {
    return TransactionType.builder()
        .kind(TransactionKind.builder().id(transactionKindId).code(transactionKindCode).build())
        .build();
  }

  @Test
  void getRawFeeTotalByDriverIfNoBalances() {
    // given
    final var driverId = 9L;
    final var firstQWeekId = 44L;
    final var currentQWeekId = 49L;
    // final var earliestNotBalanceCalculatedQWeekId = 45L;

    when(balanceLoadPort.loadLatestByDriver(driverId)).thenReturn(null /*Balance.builder()
                .driverId(driverId)
                .qWeekId(latestBalanceCalculatedQWeekId)
                .feeAbleAmount(BigDecimal.valueOf(1000L))
                .nonFeeAbleAmount(BigDecimal.valueOf(100L))
                .feeAmount(BigDecimal.valueOf(20L))
                .positiveAmount(ZERO)
                .repairmentAmount(ZERO)
                .derived(true)
                .build()*/);

    when(qWeekQuery.getFirstWeek())
        .thenReturn(
            QWeekResponse.builder()
                .id(firstQWeekId)
                .year(2024)
                .number(4)
                .start(LocalDate.of(2024, JANUARY, 22))
                .end(LocalDate.of(2024, JANUARY, 28))
                .build());

    when(qWeekQuery.getCurrentWeek())
        .thenReturn(
            QWeekResponse.builder()
                .id(currentQWeekId)
                .year(2024)
                .number(5)
                .start(LocalDate.of(2024, JANUARY, 29))
                .end(LocalDate.of(2024, FEBRUARY, 4))
                .build());

    when(qWeekQuery.getAllBetweenByIdsReversedOrder(anyLong(), anyLong()))
        .thenReturn(
            asList(
                QWeekResponse.builder()
                    .id(firstQWeekId)
                    .year(2024)
                    .number(4)
                    .start(LocalDate.of(2024, JANUARY, 22))
                    .end(LocalDate.of(2024, JANUARY, 28))
                    .build(),
                QWeekResponse.builder()
                    .id(currentQWeekId)
                    .year(2024)
                    .number(5)
                    .start(LocalDate.of(2024, JANUARY, 29))
                    .end(LocalDate.of(2024, FEBRUARY, 4))
                    .build()));

    when(transactionKindQuery.getAllNonRepairmentExceptNonFeeAble())
        .thenReturn(
            asList(
                TransactionKindResponse.builder().id(11L).code("FA").build(),
                TransactionKindResponse.builder().id(13L).code("P").build(),
                TransactionKindResponse.builder().id(14L).code("F").build()));
    when(constantQuery.getFeeWeeklyInterest()).thenReturn(BigDecimal.valueOf(0.1d));
    // will be called 2 times
    // 1st week: total -200, fee = -20
    // 2nd week: total: -400, fee = -20 + (-40)
    when(transactionLoadPort.loadAllByDriverIdAndKindIdAndBetweenDays(
            anyLong(), anyList(), any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(
            asList(
                Transaction.builder()
                    .amount(BigDecimal.valueOf(200))
                    .type(getTransactionType(11L, "FA"))
                    .build(),
                Transaction.builder()
                    .amount(BigDecimal.valueOf(10))
                    .type(getTransactionType(13L, "P"))
                    .build(),
                Transaction.builder()
                    .amount(BigDecimal.valueOf(10))
                    .type(getTransactionType(14L, "F"))
                    .build()));

    // when
    final var feeRawTotal = instanceUnderTest.getAmountFeeByDriver(driverId);

    // then
    assertTrue(BigDecimal.valueOf(-60d).compareTo(feeRawTotal) == 0);

    final var startQWeekIdCaptor = forClass(Long.class);
    final var endQWeekIdCaptor = forClass(Long.class);
    verify(qWeekQuery, times(1))
        .getAllBetweenByIdsReversedOrder(startQWeekIdCaptor.capture(), endQWeekIdCaptor.capture());
    assertEquals(44L, startQWeekIdCaptor.getValue());
    assertEquals(49L, endQWeekIdCaptor.getValue());

    final var driverIdCaptor = forClass(Long.class);
    final var transactionKindIdsCaptor = forClass(List.class);
    final var startDateCaptor = forClass(LocalDate.class);
    final var endDateCaptor = forClass(LocalDate.class);
    verify(transactionLoadPort, times(2))
        .loadAllByDriverIdAndKindIdAndBetweenDays(
            driverIdCaptor.capture(),
            transactionKindIdsCaptor.capture(),
            startDateCaptor.capture(),
            endDateCaptor.capture());

    assertEquals(9L, driverIdCaptor.getValue());
    assertTrue(transactionKindIdsCaptor.getValue().containsAll(asList(11L, 13L, 14L)));
  }

  @Test
  void getRawFeeTotalByDriverIfBalanceCalculatedAndRawTransactionsArePresent() {
    // given
    final var driverId = 9L;
    final var latestBalanceCalculatedQWeekId = 45L;
    final var firstRawQWeekId = 45L;
    final var currentQWeekId = 49L;

    when(balanceLoadPort.loadLatestByDriver(driverId))
        .thenReturn(
            Balance.builder()
                .driverId(driverId)
                .qWeekId(latestBalanceCalculatedQWeekId)
                .feeAbleAmount(BigDecimal.valueOf(-200L))
                .feeAmount(BigDecimal.valueOf(-10L))
                .positiveAmount(ZERO)
                .repairmentAmount(ZERO)
                .derived(true)
                .build());

    when(qWeekQuery.getOneAfterById(latestBalanceCalculatedQWeekId))
        .thenReturn(
            QWeekResponse.builder()
                .id(firstRawQWeekId)
                .year(2024)
                .number(4)
                .start(LocalDate.of(2024, JANUARY, 22))
                .end(LocalDate.of(2024, JANUARY, 28))
                .build());

    when(qWeekQuery.getCurrentWeek())
        .thenReturn(
            QWeekResponse.builder()
                .id(currentQWeekId)
                .year(2024)
                .number(5)
                .start(LocalDate.of(2024, JANUARY, 29))
                .end(LocalDate.of(2024, FEBRUARY, 4))
                .build());

    when(qWeekQuery.getAllBetweenByIdsReversedOrder(anyLong(), anyLong()))
        .thenReturn(
            asList(
                QWeekResponse.builder()
                    .id(firstRawQWeekId)
                    .year(2024)
                    .number(4)
                    .start(LocalDate.of(2024, JANUARY, 22))
                    .end(LocalDate.of(2024, JANUARY, 28))
                    .build(),
                QWeekResponse.builder()
                    .id(currentQWeekId)
                    .year(2024)
                    .number(5)
                    .start(LocalDate.of(2024, JANUARY, 29))
                    .end(LocalDate.of(2024, FEBRUARY, 4))
                    .build()));

    when(transactionKindQuery.getAllNonRepairmentExceptNonFeeAble())
        .thenReturn(
            asList(
                TransactionKindResponse.builder().id(11L).code("FA").build(),
                TransactionKindResponse.builder().id(13L).code("P").build(),
                TransactionKindResponse.builder().id(14L).code("F").build()));
    when(constantQuery.getFeeWeeklyInterest()).thenReturn(BigDecimal.valueOf(0.1d));
    // will be called 2 times
    // 1st week: total -200, fee = -20
    // 2nd week: total: -400, fee = -20 + (-40)
    when(transactionLoadPort.loadAllByDriverIdAndKindIdAndBetweenDays(
            anyLong(), anyList(), any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(
            asList(
                Transaction.builder()
                    .amount(BigDecimal.valueOf(200))
                    .type(getTransactionType(11L, "FA"))
                    .build(),
                Transaction.builder()
                    .amount(BigDecimal.valueOf(10))
                    .type(getTransactionType(13L, "P"))
                    .build(),
                Transaction.builder()
                    .amount(BigDecimal.valueOf(10))
                    .type(getTransactionType(14L, "F"))
                    .build()));

    // when
    final var feeRawTotal = instanceUnderTest.getAmountFeeByDriver(driverId);

    // then
    assertTrue(BigDecimal.valueOf(-120d).compareTo(feeRawTotal) == 0);

    final var startQWeekIdCaptor = forClass(Long.class);
    final var endQWeekIdCaptor = forClass(Long.class);
    verify(qWeekQuery, times(1))
        .getAllBetweenByIdsReversedOrder(startQWeekIdCaptor.capture(), endQWeekIdCaptor.capture());
    assertEquals(44L, startQWeekIdCaptor.getValue());
    assertEquals(49L, endQWeekIdCaptor.getValue());

    final var driverIdCaptor = forClass(Long.class);
    final var transactionKindIdsCaptor = forClass(List.class);
    final var startDateCaptor = forClass(LocalDate.class);
    final var endDateCaptor = forClass(LocalDate.class);
    verify(transactionLoadPort, times(2))
        .loadAllByDriverIdAndKindIdAndBetweenDays(
            driverIdCaptor.capture(),
            transactionKindIdsCaptor.capture(),
            startDateCaptor.capture(),
            endDateCaptor.capture());

    assertEquals(9L, driverIdCaptor.getValue());
    assertTrue(transactionKindIdsCaptor.getValue().containsAll(asList(11L, 13L, 14L)));
  }
}
