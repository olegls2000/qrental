package ee.qrent.billing.transaction.core.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.transaction.api.in.request.TransactionDeleteRequest;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.domain.Transaction;
import ee.qrent.billing.transaction.domain.balance.Balance;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionDeleteRequestValidatorTest {
  private TransactionDeleteRequestValidator instanceUnderTest;
  private TransactionLoadPort transactionLoadPort;
  private BalanceLoadPort balanceLoadPort;
  private GetQWeekQuery qWeekQuery;

  @BeforeEach
  void init() {
    transactionLoadPort = mock(TransactionLoadPort.class);
    balanceLoadPort = mock(BalanceLoadPort.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    instanceUnderTest =
        new TransactionDeleteRequestValidator(qWeekQuery, balanceLoadPort, transactionLoadPort);

    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().build());
  }

  @Test
  public void testDeleteIfBalanceWasNotCalculated() {
    // given
    final var deleteRequest = new TransactionDeleteRequest(1L);
    when(balanceLoadPort.loadLatest()).thenReturn(null);
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 25)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);

    // when
    final var violationsCollector = instanceUnderTest.validate(deleteRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testDeleteIfTransactionDateBeforeCalculationDate() {
    // given
    final var deleteRequest = new TransactionDeleteRequest(1L);
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
    final var violationsCollector = instanceUnderTest.validate(deleteRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertEquals(
        "Any operations with Transaction are prohibited because a Transaction's date (new or existing) Jan 25, 2023 is before or equals the latest calculated Balance date: Jan 26, 2023",
        violationsCollector.getViolations().get(0));
  }

  @Test
  public void testDeleteIfTransactionDateEqualToCalculationDate() {
    // given
    final var deleteRequest = new TransactionDeleteRequest(1L);
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
    final var violationsCollector = instanceUnderTest.validate(deleteRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertEquals(
        "Any operations with Transaction are prohibited because a Transaction's date (new or existing) Jan 26, 2023 is before or equals the latest calculated Balance date: Jan 26, 2023",
        violationsCollector.getViolations().get(0));
  }

  @Test
  public void testDeleteIfTransactionDateAfterCalculationDate() {
    // given
    final var deleteRequest = new TransactionDeleteRequest(1L);
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
    final var violationsCollector = instanceUnderTest.validate(deleteRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
  }
}
