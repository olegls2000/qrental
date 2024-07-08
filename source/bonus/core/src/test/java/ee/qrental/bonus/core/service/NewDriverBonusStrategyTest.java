package ee.qrental.bonus.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.contract.api.in.response.ContractResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NewDriverBonusStrategyTest {
  private GetTransactionQuery transactionQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private GetCarLinkQuery carLinkQuery;
  private GetContractQuery contractQuery;
  private GetQWeekQuery qWeekQuery;
  private QDateTime qDateTime;

  private NewDriverBonusStrategy instanceUnderTest;

  @BeforeEach
  void init() {
    transactionQuery = mock(GetTransactionQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    carLinkQuery = mock(GetCarLinkQuery.class);
    contractQuery = mock(GetContractQuery.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    qDateTime = mock(QDateTime.class);
    instanceUnderTest =
        new NewDriverBonusStrategy(
            transactionQuery,
            transactionTypeQuery,
            carLinkQuery,
            contractQuery,
            qWeekQuery,
            qDateTime);
  }

  @Test
  public void testGetBonusCode() {

    // when
    final var bonusCode = instanceUnderTest.getBonusCode();

    // then
    assertEquals("ND", bonusCode);
  }

  @Test
  public void testCanApplyForNonActivatedNDBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(false).code("ND").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivatedNonNDBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("2W").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testCanApplyForActivatedNDBonusProgram() {
    // given
    final var bonusProgram = BonusProgram.builder().active(true).code("ND").build();

    // when
    final var canApply = instanceUnderTest.canApply(bonusProgram);

    // then
    assertTrue(canApply);
  }

  @Test
  public void testCalculateBonusForDriverWithoutMatchedObligation() {
    // given
    final var obligation = Obligation.builder().driverId(55L).matchCount(0).build();
    final var weekPositiveAmount = BigDecimal.ONE;

    // when
    final var bonusTransactionsAddRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(bonusTransactionsAddRequests.isEmpty());
  }

  @Test
  public void testCalculateBonusForDriverWithMatchedObligationAndWithoutCarLink() {
    // given
    final var obligation = Obligation.builder().driverId(55L).matchCount(1).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(carLinkQuery.getFirstLinkByDriverId(55L)).thenReturn(null);

    // when
    final var bonusTransactionsAddRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(bonusTransactionsAddRequests.isEmpty());
  }

  @Test
  public void
      testCalculateBonusForDriverWithMatchedObligationAndWithCarLinkCalculationAfter4Weeks() {
    // given
    final var obligation = Obligation.builder().driverId(55L).matchCount(1).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(carLinkQuery.getFirstLinkByDriverId(55L))
        .thenReturn(
            CarLinkResponse.builder()
                .driverId(55L)
                .dateStart(LocalDate.of(2023, Month.JANUARY, 11)) // Wednesday
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2023, Month.FEBRUARY, 13)); // Monday

    // when
    final var bonusTransactionsAddRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(bonusTransactionsAddRequests.isEmpty());
  }

  @Test
  public void
      testCalculateBonusForDriverWithMatchedObligationAndWithCarLinkCalculationWithoutContractWithin4Weeks() {
    // given
    final var obligation = Obligation.builder().driverId(55L).matchCount(1).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(carLinkQuery.getFirstLinkByDriverId(55L))
        .thenReturn(
            CarLinkResponse.builder()
                .driverId(55L)
                .dateStart(LocalDate.of(2023, Month.JANUARY, 11)) // Wednesday
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2023, Month.FEBRUARY, 12)); // Monday
    when(contractQuery.getActiveContractByDriverId(55L)).thenReturn(null);

    // when
    final var bonusTransactionsAddRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(bonusTransactionsAddRequests.isEmpty());
  }

  @Test
  public void
      testCalculateBonusForDriverWithMatchedObligationAndWithCarLinkCalculationWithNon12WeeksContractWithin4Weeks() {
    // given
    final var obligation = Obligation.builder().driverId(55L).matchCount(1).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(carLinkQuery.getFirstLinkByDriverId(55L))
        .thenReturn(
            CarLinkResponse.builder()
                .driverId(55L)
                .dateStart(LocalDate.of(2023, Month.JANUARY, 11)) // Wednesday
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2023, Month.FEBRUARY, 12)); // Monday
    when(contractQuery.getActiveContractByDriverId(55L))
        .thenReturn(ContractResponse.builder().active(true).duration(11).build());

    // when
    final var bonusTransactionsAddRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertTrue(bonusTransactionsAddRequests.isEmpty());
  }

  @Test
  public void
      testCalculateBonusForDriverWithMatchedObligationAndWithCarLinkCalculationWith12WeeksContractWithin4Weeks() {
    // given
    final var obligation = Obligation.builder().driverId(55L).matchCount(1).qWeekId(9L).build();
    final var weekPositiveAmount = BigDecimal.ONE;
    when(carLinkQuery.getFirstLinkByDriverId(55L))
        .thenReturn(
            CarLinkResponse.builder()
                .driverId(55L)
                .dateStart(LocalDate.of(2023, Month.JANUARY, 11)) // Wednesday
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2023, Month.FEBRUARY, 12)); // Monday
    when(contractQuery.getActiveContractByDriverId(55L))
        .thenReturn(ContractResponse.builder().active(true).duration(12).build());
    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(55L, 9L)).thenReturn(rentTransactions);
    when(transactionTypeQuery.getByName("bonus"))
        .thenReturn(TransactionTypeResponse.builder().id(33L).build());
    when(qWeekQuery.getOneAfterById(9L))
        .thenReturn(QWeekResponse.builder().start(LocalDate.of(2023, Month.FEBRUARY, 13)).build());

    // when
    final var bonusTransactionsAddRequests =
        instanceUnderTest.calculateBonus(obligation, weekPositiveAmount);

    // then
    assertEquals(1, bonusTransactionsAddRequests.size());
    final var addRequestTransaction = bonusTransactionsAddRequests.get(0);
    assertEquals(0, BigDecimal.valueOf(25).compareTo(addRequestTransaction.getAmount()));
    assertEquals("Bonus Transaction for ND Strategy", addRequestTransaction.getComment());
    assertEquals(55L, addRequestTransaction.getDriverId());
    assertEquals(33L, addRequestTransaction.getTransactionTypeId());
    assertEquals(LocalDate.of(2023, Month.FEBRUARY, 13), addRequestTransaction.getDate());
  }
}
