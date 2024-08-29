package ee.qrental.transaction.core.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionBusinessRuleValidatorTest {
  private TransactionUpdateDeleteBusinessRuleValidator instanceUnderTest;
  private TransactionLoadPort transactionLoadPort;
  private BalanceLoadPort balanceLoadPort;
  private GetQWeekQuery qWeekQuery;

  @BeforeEach
  void init() {
    transactionLoadPort = mock(TransactionLoadPort.class);
    balanceLoadPort = mock(BalanceLoadPort.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    instanceUnderTest =
        new TransactionUpdateDeleteBusinessRuleValidator(
            qWeekQuery, transactionLoadPort, balanceLoadPort);

    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().build());
  }

  @Test
  public void testAddIfBalanceWasNotCalculated() {
    // given
    final var transaction =
        Transaction.builder().date(LocalDate.of(2023, Month.JANUARY, 25)).driverId(2L).build();
    when(balanceLoadPort.loadLatestByDriverId(2L)).thenReturn(null);

    // when
    final var violationsCollector = instanceUnderTest.validateAdd(transaction);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testAddIfTransactionDateBeforeCalculationDate() {
    // given
    final var transaction =
        Transaction.builder().date(LocalDate.of(2023, Month.JANUARY, 25)).driverId(2L).build();
    when(balanceLoadPort.loadLatestByDriverId(2L))
        .thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateAdd(transaction);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .contains("must be after the latest calculated Balance date"));
  }

  @Test
  public void testAddIfTransactionDateEqualToCalculationDate() {
    // given
    final var transaction =
        Transaction.builder().date(LocalDate.of(2023, Month.JANUARY, 26)).driverId(2L).build();
    when(balanceLoadPort.loadLatestByDriverId(2L))
        .thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());
    // when
    final var violationsCollector = instanceUnderTest.validateAdd(transaction);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .contains("must be after the latest calculated Balance date"));
  }

  @Test
  public void testAddIfTransactionDateAfterCalculationDate() {
    // given
    final var transaction =
        Transaction.builder().date(LocalDate.of(2023, Month.JANUARY, 27)).build();

    // when
    final var violationsCollector = instanceUnderTest.validateAdd(transaction);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testUpdateIfBalanceWasNotCalculated() {
    // given
    when(balanceLoadPort.loadLatest()).thenReturn(null);
    final var transactionNew =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 28)).build();
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 25)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);

    // when
    final var violationsCollector = instanceUnderTest.validateUpdate(transactionNew);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testUpdateIfTransactionDateBeforeCalculationDate() {
    // given
    final var transactionNew =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 28)).build();
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 25)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateUpdate(transactionNew);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .contains(
                "Update for the Transaction with id=1 is prohibited. Transaction is already calculated in Balance"));
  }

  @Test
  public void testUpdateIfTransactionDateEqualToCalculationDate() {
    // given
    final var transactionNew =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 28)).build();
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 26)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateUpdate(transactionNew);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .contains(
                "Update for the Transaction with id=1 is prohibited. Transaction is already calculated in Balance"));
  }

  @Test
  public void testUpdateIfTransactionDateAfterCalculationDate() {
    // given
    final var transactionNew =
        Transaction.builder()
            .id(1L)
            .driverId(2L)
            .date(LocalDate.of(2023, Month.JANUARY, 28))
            .build();
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 27)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
        .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateUpdate(transactionNew);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testUpdateIfTransactionNewDateBeforeCalculationDate() {
    // given
    final var transactionNew =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 25)).build();
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 27)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());


    // when
    final var violationsCollector = instanceUnderTest.validateUpdate(transactionNew);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertEquals("Transaction new date 25-01-2023 must be after the latest calculated Balance date: 26-01-2023", violationsCollector.getViolations().get(0));
  }

  @Test
  public void testUpdateIfTransactionNewDateEqualToCalculationDate() {
    // given
    final var transactionNew =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 26)).build();
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 27)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateUpdate(transactionNew);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertEquals("Transaction new date 26-01-2023 must be after the latest calculated Balance date: 26-01-2023", violationsCollector.getViolations().get(0));
  }

  @Test
  public void testUpdateIfTransactionNewDateAfterCalculationDate() {
    // given
    final var transactionNew =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 28)).build();
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 27)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateUpdate(transactionNew);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testDeleteIfBalanceWasNotCalculated() {
    // given
    when(balanceLoadPort.loadLatest()).thenReturn(null);
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 25)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);

    // when
    final var violationsCollector = instanceUnderTest.validateDelete(1L);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testDeleteIfTransactionDateBeforeCalculationDate() {
    // given
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 25)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateDelete(1L);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .equals(
                "Delete for the Transaction with id=1 is prohibited. Transaction is already calculated in Balance"));
  }

  @Test
  public void testDeleteIfTransactionDateEqualToCalculationDate() {
    // given
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 26)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateDelete(1L);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(1, violationsCollector.getViolations().size());
    assertTrue(
        violationsCollector
            .getViolations()
            .get(0)
            .equals(
                "Delete for the Transaction with id=1 is prohibited. Transaction is already calculated in Balance"));
  }

  @Test
  public void testDeleteIfTransactionDateAfterCalculationDate() {
    // given
    final var transactionFromDb =
        Transaction.builder().id(1L).date(LocalDate.of(2023, Month.JANUARY, 27)).build();
    when(transactionLoadPort.loadById(1L)).thenReturn(transactionFromDb);
    when(balanceLoadPort.loadLatest()).thenReturn(Balance.builder().qWeekId(99L).build());
    when(qWeekQuery.getById(99L))
            .thenReturn(QWeekResponse.builder().end(LocalDate.of(2023, Month.JANUARY, 26)).build());

    // when
    final var violationsCollector = instanceUnderTest.validateDelete(1L);

    // then
    assertFalse(violationsCollector.hasViolations());
  }
}
