package ee.qrent.billing.transaction.core.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.domain.Transaction;
import ee.qrent.billing.transaction.domain.balance.Balance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionUpdateRequestValidatorTest {
  private TransactionUpdateRequestValidator instanceUnderTest;
  private TransactionLoadPort transactionLoadPort;
  private BalanceLoadPort balanceLoadPort;
  private GetQWeekQuery qWeekQuery;

  @BeforeEach
  void init() {
    transactionLoadPort = mock(TransactionLoadPort.class);
    balanceLoadPort = mock(BalanceLoadPort.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    instanceUnderTest =
        new TransactionUpdateRequestValidator(qWeekQuery, balanceLoadPort, transactionLoadPort);

    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().build());
  }

  @Test
  public void testUpdateIfBalanceWasNotCalculated() {
    // given
    when(balanceLoadPort.loadLatest()).thenReturn(null);
    final var updateRequest = new TransactionUpdateRequest();
    updateRequest.setId(1L);
    updateRequest.setDate(LocalDate.of(2023, Month.JANUARY, 28));
    updateRequest.setAmount(BigDecimal.valueOf(100L));
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 25)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);

    // when
    final var violationsCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testUpdateIfTransactionDateInDbIsBeforeCalculationDate() {
    // given
    final var updateRequest = new TransactionUpdateRequest();
    updateRequest.setId(1L);
    updateRequest.setDate(LocalDate.of(2023, Month.JANUARY, 28));
    updateRequest.setAmount(BigDecimal.valueOf(100L));
    updateRequest.setDriverId(3L);
    final var transactionFromDb =
        Transaction.builder()
            .id(1L)
            .date(LocalDate.of(2023, Month.JANUARY, 25))
            .driverId(3L)
            .build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatestByDriverId(3L))
        .thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .contains(
                "Any operations with Transaction are prohibited because a Transaction's date (new or existing) Jan 25, 2023 is before or equals the latest calculated Balance date: Jan 26, 2023"));
  }

  @Test
  public void testUpdateIfTransactionDateEqualToCalculationDate() {
    // given
    final var updateRequest = new TransactionUpdateRequest();
    updateRequest.setId(1L);
    updateRequest.setDate(LocalDate.of(2023, Month.JANUARY, 28));
    updateRequest.setAmount(BigDecimal.valueOf(100L));
    updateRequest.setDriverId(3L);
    final var transactionFromDb =
        Transaction.builder()
            .id(1L)
            .date(LocalDate.of(2023, Month.JANUARY, 26))
            .driverId(3L)
            .build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatestByDriverId(3L))
        .thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .contains(
                "Any operations with Transaction are prohibited because a Transaction's date (new or existing) Jan 26, 2023 is before or equals the latest calculated Balance date: Jan 26, 2023"));
  }

  @Test
  public void testUpdateIfTransactionDateAfterCalculationDate() {
    // given
    final var updateRequest = new TransactionUpdateRequest();
    updateRequest.setId(1L);
    updateRequest.setDate(LocalDate.of(2023, Month.JANUARY, 28));
    updateRequest.setDriverId(2L);
    updateRequest.setAmount(BigDecimal.valueOf(100L));
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 27)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testUpdateIfTransactionNewDateBeforeCalculationDate() {
    // given
    final var updateRequest = new TransactionUpdateRequest();
    updateRequest.setId(1L);
    updateRequest.setDate(LocalDate.of(2023, Month.JANUARY, 25));
    updateRequest.setAmount(BigDecimal.valueOf(100L));
    updateRequest.setDriverId(3L);
    final var transactionFromDb =
        Transaction.builder()
            .id(1L)
            .date(LocalDate.of(2023, Month.JANUARY, 27))
            .driverId(3L)
            .build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatestByDriverId(3L))
        .thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertEquals(
            "Any operations with Transaction are prohibited because a Transaction's date (new or existing) Jan 25, 2023 is before or equals the latest calculated Balance date: Jan 26, 2023",
            violationsCollector.getViolations().get(0));
  }

  @Test
  public void testUpdateIfTransactionNewDateEqualToCalculationDate() {
    // given
    final var updateRequest = new TransactionUpdateRequest();
    updateRequest.setId(1L);
    updateRequest.setDate(LocalDate.of(2023, Month.JANUARY, 26));
    updateRequest.setAmount(BigDecimal.valueOf(100L));
    updateRequest.setDriverId(3L);
    final var transactionFromDb =
        Transaction.builder()
            .id(1L)
            .date(LocalDate.of(2023, Month.JANUARY, 27))
            .driverId(3L)
            .build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatestByDriverId(3L))
        .thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertEquals(
            "Any operations with Transaction are prohibited because a Transaction's date (new or existing) Jan 26, 2023 is before or equals the latest calculated Balance date: Jan 26, 2023",
            violationsCollector.getViolations().get(0));
  }

  @Test
  public void testUpdateIfTransactionNewDateAfterCalculationDate() {
    // given
    final var updateRequest = new TransactionUpdateRequest();
    updateRequest.setId(1L);
    updateRequest.setDate(LocalDate.of(2023, Month.JANUARY, 28));
    updateRequest.setAmount(BigDecimal.valueOf(100L));
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 27)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
  }
}
