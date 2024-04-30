package ee.qrental.transaction.core.service.balance;

import ee.qrental.transaction.core.service.balance.calculator.BalanceDeriveService;
import ee.qrental.transaction.domain.balance.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;

class BalanceDeriveServiceTest {
  private BalanceDeriveService instanceUnderTest;

  @BeforeEach
  void init() {
    instanceUnderTest = new BalanceDeriveService();
  }

  @Test
  public void testDeriveIfNoPositive() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .driverId(88L)
            .derived(Boolean.FALSE)
            .created(LocalDate.now().minusDays(3))
            .qWeekId(55L)
            .positiveAmount(ZERO)
            .feeAmount(BigDecimal.valueOf(100))
            .nonFeeAbleAmount(BigDecimal.valueOf(200))
            .feeAbleAmount(BigDecimal.valueOf(300))
            .repairmentAmount(BigDecimal.valueOf(400))
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
    assertEquals(LocalDate.now(), derivedBalance.getCreated());
    assertEquals(55L, derivedBalance.getQWeekId());
    assertEquals(ZERO, derivedBalance.getPositiveAmount());
    assertEquals(BigDecimal.valueOf(100), derivedBalance.getFeeAmount());
    assertEquals(BigDecimal.valueOf(200), derivedBalance.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(300), derivedBalance.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(400), derivedBalance.getRepairmentAmount());
  }

  @Test
  public void testDeriveIfNoNegative() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .driverId(88L)
            .derived(Boolean.FALSE)
            .created(LocalDate.now().minusDays(3))
            .qWeekId(55L)
            .positiveAmount(BigDecimal.valueOf(50))
            .feeAmount(ZERO)
            .nonFeeAbleAmount(ZERO)
            .feeAbleAmount(ZERO)
            .repairmentAmount(ZERO)
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
    assertEquals(LocalDate.now(), derivedBalance.getCreated());
    assertEquals(55L, derivedBalance.getQWeekId());
    assertEquals(BigDecimal.valueOf(50), derivedBalance.getPositiveAmount());
    assertEquals(ZERO, derivedBalance.getFeeAmount());
    assertEquals(ZERO, derivedBalance.getFeeAbleAmount());
    assertEquals(ZERO, derivedBalance.getNonFeeAbleAmount());
    assertEquals(ZERO, derivedBalance.getRepairmentAmount());
  }

  @Test
  public void testDeriveIfPositiveLessThenFee() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .driverId(88L)
            .derived(Boolean.FALSE)
            .created(LocalDate.now().minusDays(3))
            .qWeekId(55L)
            .positiveAmount(BigDecimal.valueOf(99))
            .feeAmount(BigDecimal.valueOf(100))
            .nonFeeAbleAmount(BigDecimal.valueOf(200))
            .feeAbleAmount(BigDecimal.valueOf(300))
            .repairmentAmount(BigDecimal.valueOf(400))
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
    assertEquals(LocalDate.now(), derivedBalance.getCreated());
    assertEquals(55L, derivedBalance.getQWeekId());
    assertEquals(ZERO, derivedBalance.getPositiveAmount());
    assertEquals(BigDecimal.valueOf(1), derivedBalance.getFeeAmount());
    assertEquals(BigDecimal.valueOf(200), derivedBalance.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(300), derivedBalance.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(400), derivedBalance.getRepairmentAmount());
  }

  @Test
  public void testDeriveIfPositiveEqualsFee() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .driverId(88L)
            .derived(Boolean.FALSE)
            .created(LocalDate.now().minusDays(3))
            .qWeekId(55L)
            .positiveAmount(BigDecimal.valueOf(100))
            .feeAmount(BigDecimal.valueOf(100))
            .nonFeeAbleAmount(BigDecimal.valueOf(200))
            .feeAbleAmount(BigDecimal.valueOf(300))
            .repairmentAmount(BigDecimal.valueOf(400))
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
    assertEquals(LocalDate.now(), derivedBalance.getCreated());
    assertEquals(55L, derivedBalance.getQWeekId());
    assertEquals(ZERO, derivedBalance.getPositiveAmount());
    assertEquals(ZERO, derivedBalance.getFeeAmount());
    assertEquals(BigDecimal.valueOf(200), derivedBalance.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(300), derivedBalance.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(400), derivedBalance.getRepairmentAmount());
  }

  @Test
  public void testDeriveIfPositiveMoreThenFeeOnly() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .driverId(88L)
            .derived(Boolean.FALSE)
            .created(LocalDate.now().minusDays(3))
            .qWeekId(55L)
            .positiveAmount(BigDecimal.valueOf(110))
            .feeAmount(BigDecimal.valueOf(100))
            .nonFeeAbleAmount(BigDecimal.valueOf(200))
            .feeAbleAmount(BigDecimal.valueOf(300))
            .repairmentAmount(BigDecimal.valueOf(400))
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
    assertEquals(LocalDate.now(), derivedBalance.getCreated());
    assertEquals(55L, derivedBalance.getQWeekId());
    assertEquals(ZERO, derivedBalance.getPositiveAmount());
    assertEquals(ZERO, derivedBalance.getFeeAmount());
    assertEquals(BigDecimal.valueOf(190), derivedBalance.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(300), derivedBalance.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(400), derivedBalance.getRepairmentAmount());
  }

  @Test
  public void testDeriveIfPositiveMoreThenFeeAndNonFeeAble() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .driverId(88L)
            .derived(Boolean.FALSE)
            .created(LocalDate.now().minusDays(3))
            .qWeekId(55L)
            .positiveAmount(BigDecimal.valueOf(310))
            .feeAmount(BigDecimal.valueOf(100))
            .nonFeeAbleAmount(BigDecimal.valueOf(200))
            .feeAbleAmount(BigDecimal.valueOf(300))
            .repairmentAmount(BigDecimal.valueOf(400))
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
    assertEquals(LocalDate.now(), derivedBalance.getCreated());
    assertEquals(55L, derivedBalance.getQWeekId());
    assertEquals(ZERO, derivedBalance.getPositiveAmount());
    assertEquals(ZERO, derivedBalance.getFeeAmount());
    assertEquals(ZERO, derivedBalance.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(290), derivedBalance.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(400), derivedBalance.getRepairmentAmount());
  }

  @Test
  public void testDeriveIfPositiveMoreThenFeeAndNonFeeAbleAndFeeAble() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .driverId(88L)
            .derived(Boolean.FALSE)
            .created(LocalDate.now().minusDays(3))
            .qWeekId(55L)
            .positiveAmount(BigDecimal.valueOf(610))
            .feeAmount(BigDecimal.valueOf(100))
            .nonFeeAbleAmount(BigDecimal.valueOf(200))
            .feeAbleAmount(BigDecimal.valueOf(300))
            .repairmentAmount(BigDecimal.valueOf(400))
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
    assertEquals(LocalDate.now(), derivedBalance.getCreated());
    assertEquals(55L, derivedBalance.getQWeekId());
    assertEquals(BigDecimal.valueOf(10), derivedBalance.getPositiveAmount());
    assertEquals(ZERO, derivedBalance.getFeeAmount());
    assertEquals(ZERO, derivedBalance.getNonFeeAbleAmount());
    assertEquals(ZERO, derivedBalance.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(400), derivedBalance.getRepairmentAmount());
  }
}
