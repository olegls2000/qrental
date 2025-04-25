package ee.qrent.billing.transaction.core.service.balance.calculator;

import static ee.qrent.common.utils.QNumberUtils.qRound;
import static ee.qrent.billing.transaction.domain.kind.TransactionKindsCode.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import ee.qrent.billing.constant.api.in.query.GetConstantQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.domain.balance.Balance;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceCalculationStrategyDryRunTest {

  private BalanceCalculationStrategyDryRun instanceUnderTest;
  private BalanceDeriveService deriveService;
  private GetConstantQuery constantQuery;

  @BeforeEach
  void init() {

    deriveService = mock(BalanceDeriveService.class);
    constantQuery = mock(GetConstantQuery.class);

    instanceUnderTest = new BalanceCalculationStrategyDryRun(deriveService, constantQuery);
  }

  @Test
  public void testIfNoBalanceForPreviousWeek() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(TRUE).build();

    final var requestedWeek = QWeekResponse.builder().id(11L).build();
    final Balance previousBalance = null;
    final var transactionsByKind = new HashMap<String, List<TransactionResponse>>();
    transactionsByKind.put(
        FA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-100)).build()));
    transactionsByKind.put(
        F.name(),
        new ArrayList<>(
            asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-10)).build())));
    transactionsByKind.put(
        NFA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));
    transactionsByKind.put(
        R.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-400)).build()));
    transactionsByKind.put(
        P.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(500)).build()));

    // when
    instanceUnderTest.calculateBalance(driver, requestedWeek, previousBalance, transactionsByKind);

    // then
    final var balanceCaptor = forClass(Balance.class);
    verify(deriveService, times(1)).getDerivedBalance(balanceCaptor.capture());
    final var balanceToDerive = balanceCaptor.getValue();

    assertEquals(11L, balanceToDerive.getQWeekId());
    assertFalse(balanceToDerive.getDerived());
    assertEquals(77L, balanceToDerive.getDriverId());
    assertEquals(qRound(BigDecimal.valueOf(100d)), balanceToDerive.getFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(10d)), balanceToDerive.getFeeAmount());
    assertEquals(qRound(BigDecimal.valueOf(200d)), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(400d)), balanceToDerive.getRepairmentAmount());
    assertEquals(qRound(BigDecimal.valueOf(500d)), balanceToDerive.getPositiveAmount());
  }

  @Test
  public void testWithBalancePreviousWeekBalanceIfDriverHasFee() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(TRUE).build();

    final var requestedWeek = QWeekResponse.builder().id(11L).build();
    final var previousBalance =
        Balance.builder()
            .feeAbleAmount(BigDecimal.valueOf(20))
            .feeAmount(BigDecimal.valueOf(5))
            .nonFeeAbleAmount(BigDecimal.valueOf(30))
            .repairmentAmount(BigDecimal.valueOf(40))
            .positiveAmount(ZERO)
            .build();
    final var transactionsByKind = new HashMap<String, List<TransactionResponse>>();
    transactionsByKind.put(
        FA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-100)).build()));
    transactionsByKind.put(
        F.name(),
        new ArrayList<>(
            asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-10)).build())));
    transactionsByKind.put(
        NFA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));
    transactionsByKind.put(
        R.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-400)).build()));
    transactionsByKind.put(
        P.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(500)).build()));

    when(constantQuery.getFeeWeeklyInterest()).thenReturn(BigDecimal.valueOf(0.1d));

    // when
    instanceUnderTest.calculateBalance(driver, requestedWeek, previousBalance, transactionsByKind);

    // then
    final var balanceCaptor = forClass(Balance.class);
    verify(deriveService, times(1)).getDerivedBalance(balanceCaptor.capture());
    final var balanceToDerive = balanceCaptor.getValue();

    assertEquals(11L, balanceToDerive.getQWeekId());
    assertFalse(balanceToDerive.getDerived());
    assertEquals(77L, balanceToDerive.getDriverId());
    assertEquals(qRound(BigDecimal.valueOf(120d)), balanceToDerive.getFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(17d)), balanceToDerive.getFeeAmount());
    assertEquals(qRound(BigDecimal.valueOf(230d)), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(440d)), balanceToDerive.getRepairmentAmount());
    assertEquals(qRound(BigDecimal.valueOf(500d)), balanceToDerive.getPositiveAmount());
  }

  @Test
  public void testWithBalancePreviousWeekBalanceIfDriverHasFeeAndFeeAbleLessThenThreshold() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(TRUE).build();

    final var requestedWeek = QWeekResponse.builder().id(11L).build();
    final var previousBalance =
        Balance.builder()
            .feeAbleAmount(ZERO)
            .feeAmount(BigDecimal.valueOf(20))
            .nonFeeAbleAmount(BigDecimal.valueOf(30))
            .repairmentAmount(BigDecimal.valueOf(40))
            .positiveAmount(ZERO)
            .build();
    final var transactionsByKind = new HashMap<String, List<TransactionResponse>>();
    transactionsByKind.put(
        FA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-100)).build()));
    transactionsByKind.put(
        F.name(),
        new ArrayList<>(
            asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-10)).build())));
    transactionsByKind.put(
        NFA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));
    transactionsByKind.put(
        R.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-400)).build()));
    transactionsByKind.put(
        P.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(500)).build()));

    when(constantQuery.getFeeWeeklyInterest()).thenReturn(BigDecimal.valueOf(0.1d));

    // when
    instanceUnderTest.calculateBalance(driver, requestedWeek, previousBalance, transactionsByKind);

    // then
    final var balanceCaptor = forClass(Balance.class);
    verify(deriveService, times(1)).getDerivedBalance(balanceCaptor.capture());
    final var balanceToDerive = balanceCaptor.getValue();

    assertEquals(11L, balanceToDerive.getQWeekId());
    assertFalse(balanceToDerive.getDerived());
    assertEquals(77L, balanceToDerive.getDriverId());
    assertEquals(qRound(BigDecimal.valueOf(100d)), balanceToDerive.getFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(30)), balanceToDerive.getFeeAmount());
    assertEquals(qRound(BigDecimal.valueOf(230)), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(440)), balanceToDerive.getRepairmentAmount());
    assertEquals(qRound(BigDecimal.valueOf(500)), balanceToDerive.getPositiveAmount());
  }

  @Test
  public void testWithBalanceForPreviousWeekBalanceIfDriverDoesNotHaveFee() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(FALSE).build();

    final var requestedWeek = QWeekResponse.builder().id(11L).build();
    final var previousBalance =
        Balance.builder()
            .feeAbleAmount(BigDecimal.valueOf(20))
            .feeAmount(ZERO)
            .nonFeeAbleAmount(BigDecimal.valueOf(30))
            .repairmentAmount(BigDecimal.valueOf(40))
            .positiveAmount(ZERO)
            .build();
    final var transactionsByKind = new HashMap<String, List<TransactionResponse>>();
    transactionsByKind.put(
        FA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-100)).build()));
    transactionsByKind.put(
        NFA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));
    transactionsByKind.put(
        R.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-400)).build()));
    transactionsByKind.put(
        P.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(500)).build()));

    when(constantQuery.getFeeWeeklyInterest()).thenReturn(BigDecimal.valueOf(0.1d));

    // when
    instanceUnderTest.calculateBalance(driver, requestedWeek, previousBalance, transactionsByKind);

    // then
    final var balanceCaptor = forClass(Balance.class);
    verify(deriveService, times(1)).getDerivedBalance(balanceCaptor.capture());
    final var balanceToDerive = balanceCaptor.getValue();

    assertEquals(11L, balanceToDerive.getQWeekId());
    assertFalse(balanceToDerive.getDerived());
    assertEquals(77L, balanceToDerive.getDriverId());
    assertEquals(qRound(BigDecimal.valueOf(120)), balanceToDerive.getFeeAbleAmount());
    assertEquals(qRound(ZERO), balanceToDerive.getFeeAmount());
    assertEquals(qRound(BigDecimal.valueOf(230)), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(440)), balanceToDerive.getRepairmentAmount());
    assertEquals(qRound(BigDecimal.valueOf(500)), balanceToDerive.getPositiveAmount());
  }

  @Test
  public void testWithBalancePreviousWeekBalanceIfDriverHasFeeAndFeeDebtBiggerThenFeeAble() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(TRUE).build();

    final var requestedWeek = QWeekResponse.builder().id(11L).build();
    final var previousBalance =
        Balance.builder()
            .feeAbleAmount(BigDecimal.valueOf(20))
            .feeAmount(BigDecimal.valueOf(40))
            .nonFeeAbleAmount(BigDecimal.valueOf(30))
            .repairmentAmount(BigDecimal.valueOf(40))
            .positiveAmount(ZERO)
            .build();
    final var transactionsByKind = new HashMap<String, List<TransactionResponse>>();
    transactionsByKind.put(
        FA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-20)).build()));
    transactionsByKind.put(
        NFA.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));
    transactionsByKind.put(
        R.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-400)).build()));
    transactionsByKind.put(
        P.name(),
        asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(500)).build()));

    when(constantQuery.getFeeWeeklyInterest()).thenReturn(BigDecimal.valueOf(0.1d));

    // when
    instanceUnderTest.calculateBalance(driver, requestedWeek, previousBalance, transactionsByKind);

    // then
    final var balanceCaptor = forClass(Balance.class);
    verify(deriveService, times(1)).getDerivedBalance(balanceCaptor.capture());
    final var balanceToDerive = balanceCaptor.getValue();

    assertEquals(11L, balanceToDerive.getQWeekId());
    assertFalse(balanceToDerive.getDerived());
    assertEquals(77L, balanceToDerive.getDriverId());
    assertEquals(qRound(BigDecimal.valueOf(40d)), balanceToDerive.getFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(60d)), balanceToDerive.getFeeAmount());
    assertEquals(qRound(BigDecimal.valueOf(230)), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(440)), balanceToDerive.getRepairmentAmount());
    assertEquals(qRound(BigDecimal.valueOf(500)), balanceToDerive.getPositiveAmount());
  }

  @Test
  public void testWithPreviousWeekBalanceIfDriverHasNoTransactionInRequestedWeek() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(TRUE).build();

    final var requestedWeek = QWeekResponse.builder().id(11L).build();
    final var previousBalance =
        Balance.builder()
            .feeAbleAmount(BigDecimal.valueOf(20))
            .feeAmount(BigDecimal.valueOf(5))
            .nonFeeAbleAmount(BigDecimal.valueOf(30))
            .repairmentAmount(BigDecimal.valueOf(40))
            .positiveAmount(ZERO)
            .build();
    final var transactionsByKind = new HashMap<String, List<TransactionResponse>>();
    transactionsByKind.put(FA.name(), null);
    transactionsByKind.put(F.name(), null);
    transactionsByKind.put(NFA.name(), null);
    transactionsByKind.put(R.name(), null);
    transactionsByKind.put(P.name(), null);

    when(constantQuery.getFeeWeeklyInterest()).thenReturn(BigDecimal.valueOf(0.1d));

    // when
    instanceUnderTest.calculateBalance(driver, requestedWeek, previousBalance, transactionsByKind);

    // then
    final var balanceCaptor = forClass(Balance.class);
    verify(deriveService, times(1)).getDerivedBalance(balanceCaptor.capture());
    final var balanceToDerive = balanceCaptor.getValue();

    assertEquals(11L, balanceToDerive.getQWeekId());
    assertFalse(balanceToDerive.getDerived());
    assertEquals(77L, balanceToDerive.getDriverId());
    assertEquals(qRound(BigDecimal.valueOf(20)), balanceToDerive.getFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(7d)), balanceToDerive.getFeeAmount());
    assertEquals(qRound(BigDecimal.valueOf(30)), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(qRound(BigDecimal.valueOf(40)), balanceToDerive.getRepairmentAmount());
    assertEquals(qRound(ZERO), balanceToDerive.getPositiveAmount());
  }
}
