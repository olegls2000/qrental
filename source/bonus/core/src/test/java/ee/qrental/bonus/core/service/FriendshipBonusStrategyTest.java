package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.bonus.api.in.response.ObligationResponse;
import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.api.in.response.FriendshipResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FriendshipBonusStrategyTest {
  private GetTransactionQuery transactionQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private GetDriverQuery driverQuery;
  private GetObligationQuery obligationQuery;
  private GetQWeekQuery qWeekQuery;

  private FriendshipBonusStrategy instanceUnderTest;

  @BeforeEach
  void init() {
    transactionQuery = mock(GetTransactionQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    obligationQuery = mock(GetObligationQuery.class);
    qWeekQuery = mock(GetQWeekQuery.class);

    instanceUnderTest =
        new FriendshipBonusStrategy(
            transactionQuery, transactionTypeQuery, driverQuery, obligationQuery, qWeekQuery);
  }

  @Test
  public void testGetBonusCode() {

    // when
    final var bonusCode = instanceUnderTest.getBonusCode();

    // then
    assertEquals("BF", bonusCode);
  }

  @Test
  public void testCanApplyForNonActivatedFriendshipBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(false).code("FR").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivatedNonFriendshipBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("4W").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivatedFriendshipBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("BF").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertTrue(canApply);
  }

  @Test
  public void testCalculateBonusForNonMatchingCount() {
    // given
    final var obligation = Obligation.builder().matchCount(0).build();
    final var weekPositiveAmount = BigDecimal.ONE;

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(addTransactionRequests.isEmpty());
  }

  @Test
  public void testCalculateBonusForMatchingCountAndMissingFriends() {
    // given
    final var obligation = Obligation.builder().driverId(2L).matchCount(1).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(driverQuery.getFriendships(2L)).thenReturn(emptyList());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(addTransactionRequests.isEmpty());
  }

  @Test
  public void testCalculateBonusForMatchingCountAndFriendWithoutMatchingCount() {
    // given
    final var driverObligation =
        Obligation.builder().driverId(2L).matchCount(1).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(driverQuery.getFriendships(2L))
        .thenReturn(singletonList(FriendshipResponse.builder().friendId(222L).build()));
    when(obligationQuery.getByQWeekIdAndDriverId(9L, 222L))
        .thenReturn(ObligationResponse.builder().matchCount(0).build());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(driverObligation, weekPositiveAmount);

    // then
    assertTrue(addTransactionRequests.isEmpty());
  }

  @Test
  public void testCalculateBonusForMatchingCountAndFriendWithMatchingCountAndDontMatchingAge() {
    // given
    final var driverObligation =
        Obligation.builder().driverId(2L).matchCount(1).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(driverQuery.getFriendships(2L))
        .thenReturn(singletonList(FriendshipResponse.builder().friendId(222L).build()));

    final var friendCreationDate = LocalDate.now().minus(11, WEEKS);
    when(driverQuery.getById(222L))
        .thenReturn(DriverResponse.builder().id(222L).createdDate(friendCreationDate).build());
    when(obligationQuery.getByQWeekIdAndDriverId(9L, 222L))
        .thenReturn(ObligationResponse.builder().matchCount(1).build());
    when(qWeekQuery.getOneAfterById(9L))
        .thenReturn(
            QWeekResponse.builder()
                .id(222L)
                .year(2024)
                .number(30)
                .start(LocalDate.of(2024, Month.JULY, 9))
                .end(LocalDate.of(2024, Month.AUGUST, 20))
                .description("Test")
                .build());
    when(transactionTypeQuery.getByName("bonus"))
        .thenReturn(TransactionTypeResponse.builder().id(9L).build());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(driverObligation, weekPositiveAmount);

    // then
    assertFalse(addTransactionRequests.isEmpty());
  }

  @Test
  public void testCalculateBonus() {
    // given
    final var obligation = Obligation.builder().matchCount(1).driverId(2L).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.valueOf(200d);

    when(driverQuery.getFriendships(2L))
        .thenReturn(singletonList(FriendshipResponse.builder().friendId(222L).build()));
    when(obligationQuery.getByQWeekIdAndDriverId(9L, 222L))
        .thenReturn(ObligationResponse.builder().matchCount(1).build());
    final var friendCreationDate = LocalDate.now().minus(10, WEEKS);
    when(driverQuery.getById(222L))
        .thenReturn(DriverResponse.builder().id(222L).createdDate(friendCreationDate).build());
    when(qWeekQuery.getOneAfterById(9L))
        .thenReturn(
            QWeekResponse.builder()
                .id(222L)
                .year(2024)
                .number(30)
                .start(LocalDate.of(2024, Month.JULY, 9))
                .end(LocalDate.of(2024, Month.AUGUST, 20))
                .description("Test")
                .build());

    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(222L, 9L)).thenReturn(rentTransactions);
    when(transactionTypeQuery.getByName("bonus"))
        .thenReturn(TransactionTypeResponse.builder().id(33L).build());

    // when
    final var addTransactionRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertEquals(1, addTransactionRequests.size());
    final var addRequestTransaction = addTransactionRequests.get(0);
    assertEquals(0, BigDecimal.valueOf(5).compareTo(addRequestTransaction.getAmount()));
    assertEquals("Bonus Transaction for BF Strategy", addRequestTransaction.getComment());
    assertEquals(2L, addRequestTransaction.getDriverId());
    assertEquals(33L, addRequestTransaction.getTransactionTypeId());
  }
}
