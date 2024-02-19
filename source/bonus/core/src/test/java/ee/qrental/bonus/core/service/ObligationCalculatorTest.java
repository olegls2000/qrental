package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObligationCalculatorTest {

  private ObligationCalculator instanceUnderTest;
  private GetQWeekQuery qWeekQuery;
  private GetBalanceQuery balanceQuery;
  private GetTransactionQuery transactionQuery;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    balanceQuery = mock(GetBalanceQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    instanceUnderTest = new ObligationCalculator(qWeekQuery, balanceQuery, transactionQuery);

    when(qWeekQuery.getOneBeforeById(9L)).thenReturn(QWeekResponse.builder().id(8L).build());
  }

  @Test
  public void testObligationWithDebt() {
    // given
    final var driverId = 2L;
    final var qWekId = 9L;

    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);
    when(balanceQuery.getRawBalanceTotalByDriverIdAndQWeekId(2L, 8L))
        .thenReturn(BigDecimal.valueOf(-1L));

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(125d).compareTo(obligationAmount));
  }

  @Test
  public void testObligationWithZeroBalance() {
    // given
    final var driverId = 2L;
    final var qWekId = 9L;

    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);
    when(balanceQuery.getRawBalanceTotalByDriverIdAndQWeekId(2L, 8L)).thenReturn(ZERO);

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(100d).compareTo(obligationAmount));
  }

  @Test
  public void testObligationWithPositiveBalance() {
    // given
    final var driverId = 2L;
    final var qWekId = 9L;

    final var rentTransaction =
        TransactionResponse.builder()
            .type(TRANSACTION_TYPE_NAME_WEEKLY_RENT)
            .realAmount(BigDecimal.valueOf(-100d))
            .build();
    final var rentTransactions = singletonList(rentTransaction);

    when(transactionQuery.getAllByDriverIdAndQWeekId(2L, 9L)).thenReturn(rentTransactions);
    when(balanceQuery.getRawBalanceTotalByDriverIdAndQWeekId(2L, 8L))
        .thenReturn(BigDecimal.valueOf(50d));

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(50d).compareTo(obligationAmount));
  }
}
