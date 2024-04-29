package ee.qrental.transaction.core.service.balance.calculator;

import static ee.qrental.transaction.domain.kind.TransactionKindsCode.*;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
  public void testIfNoPreviousWeek() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(TRUE).build();

    final var requestedWeek = QWeekResponse.builder().id(11L).build();
    final Balance previousBalance = null;
    final var transactionsByKind = new HashMap<TransactionKindsCode, List<TransactionResponse>>();
    transactionsByKind.put(
        FA, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-100)).build()));
    transactionsByKind.put(
        F, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-10)).build()));
    transactionsByKind.put(
        NFA, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));
    transactionsByKind.put(
        R, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-400)).build()));
    transactionsByKind.put(
        P, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(500)).build()));

    // when
    instanceUnderTest.calculateBalance(driver, requestedWeek, previousBalance, transactionsByKind);

    // then
    final var balanceCaptor = forClass(Balance.class);
    verify(deriveService, times(1)).getDerivedBalance(balanceCaptor.capture());
    final var balanceToDerive = balanceCaptor.getValue();

    assertEquals(11L, balanceToDerive.getQWeekId());
    assertFalse(balanceToDerive.getDerived());
    assertEquals(77L, balanceToDerive.getDriverId());
    assertEquals(BigDecimal.valueOf(100), balanceToDerive.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(10), balanceToDerive.getFeeAmount());
    assertEquals(BigDecimal.valueOf(200), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(400), balanceToDerive.getRepairmentAmount());
    assertEquals(BigDecimal.valueOf(500), balanceToDerive.getPositiveAmount());
  }

  @Test
  public void testWithPreviousWeekIfDriverHasFee() {
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
    final var transactionsByKind = new HashMap<TransactionKindsCode, List<TransactionResponse>>();
    transactionsByKind.put(
        FA, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-100)).build()));
    transactionsByKind.put(
        F, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-10)).build()));
    transactionsByKind.put(
        NFA, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-200)).build()));
    transactionsByKind.put(
        R, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(-400)).build()));
    transactionsByKind.put(
        P, asList(TransactionResponse.builder().realAmount(BigDecimal.valueOf(500)).build()));

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
    assertEquals(BigDecimal.valueOf(120), balanceToDerive.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(17d), balanceToDerive.getFeeAmount());
    assertEquals(BigDecimal.valueOf(230), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(440), balanceToDerive.getRepairmentAmount());
    assertEquals(BigDecimal.valueOf(500), balanceToDerive.getPositiveAmount());
  }

  @Test
  public void testWithPreviousWeekIfDriverHasNoTransactionInRequestedWeek() {
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
    final var transactionsByKind = new HashMap<TransactionKindsCode, List<TransactionResponse>>();
    transactionsByKind.put(FA, null);
    transactionsByKind.put(F, null);
    transactionsByKind.put(NFA, null);
    transactionsByKind.put(R, null);
    transactionsByKind.put(P, null);

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
    assertEquals(BigDecimal.valueOf(20), balanceToDerive.getFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(7d), balanceToDerive.getFeeAmount());
    assertEquals(BigDecimal.valueOf(30), balanceToDerive.getNonFeeAbleAmount());
    assertEquals(BigDecimal.valueOf(40), balanceToDerive.getRepairmentAmount());
    assertEquals(ZERO, balanceToDerive.getPositiveAmount());
  }
}
