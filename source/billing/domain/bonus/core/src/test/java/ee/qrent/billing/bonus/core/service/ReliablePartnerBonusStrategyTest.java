package ee.qrent.billing.bonus.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrent.billing.bonus.domain.BonusProgram;
import ee.qrent.billing.bonus.domain.Obligation;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.type.TransactionTypeResponse;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReliablePartnerBonusStrategyTest {
  private GetTransactionQuery transactionQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private GetQWeekQuery qWeekQuery;

  private BonusStrategy instanceUnderTest;

  @BeforeEach
  void init() {
    transactionQuery = mock(GetTransactionQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    instanceUnderTest =
        new ReliablePartnerBonusStrategy(transactionQuery, transactionTypeQuery, qWeekQuery);
  }

  @Test
  public void testGetBonusCode() {

    // when
    final var bonusCode = instanceUnderTest.getBonusCode();

    // then
    assertEquals("RP", bonusCode);
  }

  @Test
  public void testCanApplyForNonActivatedRpBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(false).code("RP").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivatedNonRpBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("2W").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivatedRpBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("RP").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertTrue(canApply);
  }

  @Test
  public void testCalculateBonusForNonMatchingCount() {
    // given
    final var obligation = Obligation.builder().matchCount(3).build();
    final var weekPositiveAmount = BigDecimal.ONE;

    // when
    final var addTransactionRequestOpt =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(addTransactionRequestOpt.isEmpty());
  }

  @Test
  public void testCalculate4Matches1PercentBonus() {
    // given
    final var obligation = Obligation.builder().matchCount(4).driverId(2L).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.valueOf(400d);
    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);
    when(transactionTypeQuery.getByName("bonus"))
        .thenReturn(TransactionTypeResponse.builder().id(33L).build());
    when(qWeekQuery.getOneAfterById(9L))
        .thenReturn(QWeekResponse.builder().start(LocalDate.now()).build());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertEquals(1, addTransactionRequests.size());
    final var addRequestTransaction = addTransactionRequests.get(0);
    assertEquals(0, BigDecimal.valueOf(1).compareTo(addRequestTransaction.getAmount()));
    assertEquals("Bonus Transaction for RP Strategy", addRequestTransaction.getComment());
    assertEquals(2L, addRequestTransaction.getDriverId());
    assertEquals(33L, addRequestTransaction.getTransactionTypeId());
  }

  @Test
  public void testCalculate5Matches1PercentBonus() {
    // given
    final var obligation = Obligation.builder().matchCount(5).driverId(2L).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.valueOf(400d);
    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);
    when(transactionTypeQuery.getByName("bonus"))
        .thenReturn(TransactionTypeResponse.builder().id(33L).build());
    when(qWeekQuery.getOneAfterById(9L))
        .thenReturn(QWeekResponse.builder().start(LocalDate.now()).build());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertEquals(1, addTransactionRequests.size());
    final var addRequestTransaction = addTransactionRequests.get(0);
    assertEquals(0, BigDecimal.valueOf(1).compareTo(addRequestTransaction.getAmount()));
  }

  @Test
  public void testCalculate8Matches2PercentBonus() {
    // given
    final var obligation = Obligation.builder().matchCount(8).driverId(2L).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.valueOf(400d);
    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);
    when(transactionTypeQuery.getByName("bonus"))
        .thenReturn(TransactionTypeResponse.builder().id(33L).build());
    when(qWeekQuery.getOneAfterById(9L))
        .thenReturn(QWeekResponse.builder().start(LocalDate.now()).build());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertEquals(1, addTransactionRequests.size());
    final var addRequestTransaction = addTransactionRequests.get(0);
    assertEquals(0, BigDecimal.valueOf(2).compareTo(addRequestTransaction.getAmount()));
  }

  @Test
  public void testCalculate24Matches5PercentBonus() {
    // given
    final var obligation = Obligation.builder().matchCount(24).driverId(2L).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.valueOf(400d);
    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);
    when(transactionTypeQuery.getByName("bonus"))
        .thenReturn(TransactionTypeResponse.builder().id(33L).build());
    when(qWeekQuery.getOneAfterById(9L))
        .thenReturn(QWeekResponse.builder().start(LocalDate.now()).build());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertEquals(1, addTransactionRequests.size());
    final var addRequestTransaction = addTransactionRequests.get(0);
    assertEquals(0, BigDecimal.valueOf(5).compareTo(addRequestTransaction.getAmount()));
  }
}
