package ee.qrental.bonus.core.service;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FourWeeksPrepaymentBonusStrategyTest {
  private GetTransactionQuery transactionQuery;
  private GetTransactionTypeQuery transactionTypeQuery;

  private FourWeeksPrepaymentBonusStrategy instanceUnderTest;

  @BeforeEach
  void init() {
    transactionQuery = mock(GetTransactionQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    instanceUnderTest =
        new FourWeeksPrepaymentBonusStrategy(transactionQuery, transactionTypeQuery);
  }

  @Test
  public void testGetBonusCode() {

    // when
    final var bonusCode = instanceUnderTest.getBonusCode();

    // then
    assertEquals("4W", bonusCode);
  }

  @Test
  public void testCanApplyForNonActivated2WBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(false).code("2W").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivatedNon4WBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("2W").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivated2WBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("4W").build();

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
  public void testCalculateBonusForNotEnoughPrepayment() {
    // given
    final var obligation = Obligation.builder().matchCount(4).driverId(2L).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.valueOf(399d);
    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(addTransactionRequests.isEmpty());
  }

  @Test
  public void testCalculateBonus() {
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

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertEquals(1, addTransactionRequests.size());
    final var addRequestTransaction = addTransactionRequests.get(0);
    assertEquals(0, BigDecimal.valueOf(40).compareTo(addRequestTransaction.getAmount()));
    assertEquals("Bonus Transaction for 4W Strategy", addRequestTransaction.getComment());
    assertEquals(2L, addRequestTransaction.getDriverId());
    assertEquals(33L, addRequestTransaction.getTransactionTypeId());
  }
}
