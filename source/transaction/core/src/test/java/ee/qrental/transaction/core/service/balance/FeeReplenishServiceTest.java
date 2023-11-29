package ee.qrental.transaction.core.service.balance;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.domain.balance.Balance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class FeeReplenishServiceTest {

  private ReplenishService instanceUnderTest;
  private GetQWeekQuery qWeekQuery;
  private BalanceLoadPort balanceLoadPort;
  private TransactionAddUseCase transactionAddUseCase;
  private GetTransactionTypeQuery transactionTypeQuery;
  private GetTransactionQuery transactionQuery;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    balanceLoadPort = mock(BalanceLoadPort.class);
    transactionAddUseCase = mock(TransactionAddUseCase.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);

    instanceUnderTest =
        new ReplenishService(
            qWeekQuery,
            transactionQuery,
            transactionTypeQuery,
            transactionAddUseCase,
            balanceLoadPort);

    //when(balanceLoadPort.loadLatest()).thenReturn(LocalDate.of(2023, Month.JANUARY, 26));
  }

  @Test
  void testReplenishIfFeeBalanceIsPositive() {
    // given
    final var driverId = 2L;
    final var week =
        QWeekResponse.builder()
            .start(LocalDate.of(2023, Month.JANUARY, 9))
            .end(LocalDate.of(2023, Month.JANUARY, 16))
            .number(2)
            .build();
    final var balance = Balance.builder().feeAmount(BigDecimal.valueOf(2.5)).build();
    when(balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, 2023, 1))
        .thenReturn(balance);

    // when
    instanceUnderTest.replenishFee(week, 2L);

    // then
    verify(transactionAddUseCase, never()).add(any());
  }

  @Test
  void testReplenishIfFeeBalanceIsZero() {
    // given
    final var driverId = 2L;
    final var week =
        QWeekResponse.builder()
            .start(LocalDate.of(2023, Month.JANUARY, 9))
            .end(LocalDate.of(2023, Month.JANUARY, 16))
            .number(2)
            .build();
    final var balance = Balance.builder().feeAmount(BigDecimal.ZERO).build();
    when(balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, 2023, 1))
        .thenReturn(balance);

    // when
    instanceUnderTest.replenishFee(week, 2L);

    // then
    verify(transactionAddUseCase, never()).add(any());
  }

  @Test
  void testReplenishIfFeeBalanceIsNegativeAndFeeDebtIsLessThenPositiveTransactionAmount() {
    // given
    final var driverId = 2L;
    final var week =
        QWeekResponse.builder()
            .start(LocalDate.of(2023, Month.JANUARY, 9))
            .end(LocalDate.of(2023, Month.JANUARY, 16))
            .number(2)
            .build();
    final var balance = Balance.builder().feeAmount(BigDecimal.valueOf(-10)).build();
    when(balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, 2023, 1))
        .thenReturn(balance);

    final var donorTransaction =
        TransactionResponse.builder().realAmount(BigDecimal.valueOf(15)).build();
    when(transactionQuery.getAllByFilter(any(PeriodAndDriverFilter.class)))
        .thenReturn(singletonList(donorTransaction));

    final var replenishTransactionType =
        TransactionTypeResponse.builder().id(33L).name("fee replenish").build();
    when(transactionTypeQuery.getByName("fee replenish")).thenReturn(replenishTransactionType);

    final var compensationTransactionType =
        TransactionTypeResponse.builder().id(44L).name("compensation").build();
    when(transactionTypeQuery.getByName("compensation")).thenReturn(compensationTransactionType);

    // when
    instanceUnderTest.replenishFee(week, 2L);

    // then
    final var transactionAddRequestCaptor = ArgumentCaptor.forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(2)).add(transactionAddRequestCaptor.capture());
    final var transactionAddRequests = transactionAddRequestCaptor.getAllValues();
    final var compensationTransactionAddRequest =
        transactionAddRequests.stream()
            .filter(
                transactionAddRequest -> transactionAddRequest.getTransactionTypeId().equals(44L))
            .findFirst()
            .get();
    assertNotNull(compensationTransactionAddRequest);
    assertEquals(BigDecimal.valueOf(10), compensationTransactionAddRequest.getAmount());
    assertEquals(2, compensationTransactionAddRequest.getWeekNumber());

    final var replenishTransactionAddRequest =
        transactionAddRequests.stream()
            .filter(
                transactionAddRequest -> transactionAddRequest.getTransactionTypeId().equals(33L))
            .findFirst()
            .get();
    assertNotNull(replenishTransactionAddRequest);
    assertEquals(BigDecimal.valueOf(10), replenishTransactionAddRequest.getAmount());
    assertEquals(2, replenishTransactionAddRequest.getWeekNumber());
  }

  @Test
  void testReplenishIfFeeBalanceIsNegativeAndFeeDebtIsBiggerThenPositiveTransactionAmount() {
    // given
    final var driverId = 2L;
    final var week =
        QWeekResponse.builder()
            .start(LocalDate.of(2023, Month.JANUARY, 9))
            .end(LocalDate.of(2023, Month.JANUARY, 16))
            .number(2)
            .build();
    ;
    final var balance = Balance.builder().feeAmount(BigDecimal.valueOf(-10)).build();
    when(balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, 2023, 1))
        .thenReturn(balance);

    final var donorTransaction =
        TransactionResponse.builder().realAmount(BigDecimal.valueOf(15)).build();
    when(transactionQuery.getAllByFilter(any(PeriodAndDriverFilter.class)))
        .thenReturn(singletonList(donorTransaction));

    final var replenishTransactionType =
        TransactionTypeResponse.builder().id(33L).name("fee replenish").build();
    when(transactionTypeQuery.getByName("fee replenish")).thenReturn(replenishTransactionType);

    final var compensationTransactionType =
        TransactionTypeResponse.builder().id(44L).name("compensation").build();
    when(transactionTypeQuery.getByName("compensation")).thenReturn(compensationTransactionType);

    // when
    instanceUnderTest.replenishFee(week, 2L);

    // then
    final var transactionAddRequestCaptor = ArgumentCaptor.forClass(TransactionAddRequest.class);
    verify(transactionAddUseCase, times(2)).add(transactionAddRequestCaptor.capture());
    final var transactionAddRequests = transactionAddRequestCaptor.getAllValues();
    final var compensationTransactionAddRequest =
        transactionAddRequests.stream()
            .filter(
                transactionAddRequest -> transactionAddRequest.getTransactionTypeId().equals(44L))
            .findFirst()
            .get();
    assertNotNull(compensationTransactionAddRequest);
    assertEquals(BigDecimal.valueOf(10), compensationTransactionAddRequest.getAmount());
    assertEquals(2, compensationTransactionAddRequest.getWeekNumber());

    final var replenishTransactionAddRequest =
        transactionAddRequests.stream()
            .filter(
                transactionAddRequest -> transactionAddRequest.getTransactionTypeId().equals(33L))
            .findFirst()
            .get();
    assertNotNull(replenishTransactionAddRequest);
    assertEquals(BigDecimal.valueOf(10), replenishTransactionAddRequest.getAmount());
    assertEquals(2, replenishTransactionAddRequest.getWeekNumber());
  }

  void test() {
    // given

    // when

    // then
  }
}
