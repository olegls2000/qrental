package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObligationCalculatorTest {

  private ObligationCalculator instanceUnderTest;
  private GetQWeekQuery qWeekQuery;
  private GetBalanceQuery balanceQuery;
  private GetDriverQuery driverQuery;
  private GetTransactionQuery transactionQuery;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    balanceQuery = mock(GetBalanceQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    instanceUnderTest =
        new ObligationCalculator(qWeekQuery, balanceQuery, driverQuery, transactionQuery);

    when(qWeekQuery.getOneBeforeById(9L)).thenReturn(QWeekResponse.builder().id(8L).build());
  }

  @Test
  public void testSmth(){

    final Integer weekYear = 2024;
    final Integer weekNumber = 5;
            final Long driverId = 66L;

    final var formattedWeekNumber = String.format("%02d", weekNumber);

      final var rr =  format("%d%s%d", weekYear, formattedWeekNumber, driverId);
      assertEquals("20240566", rr);
  }

  @Test
  public void testObligationWithDebtLess25PercentAndWithoutRequiredObligation() {
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
        .thenReturn(BigDecimal.valueOf(-24L));
    when(driverQuery.getById(2L))
        .thenReturn(DriverResponse.builder().id(2L).hasRequiredObligation(false).build());

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(124d).compareTo(obligationAmount));
  }

  @Test
  public void testObligationWithDebtMore25PercentsAndWithoutRequiredObligation() {
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
            .thenReturn(BigDecimal.valueOf(-26L));
    when(driverQuery.getById(2L))
            .thenReturn(DriverResponse.builder().id(2L).hasRequiredObligation(false).build());

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(125d).compareTo(obligationAmount));
  }

  @Test
  public void testObligationWithZeroBalanceAndWithoutRequiredObligation() {
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
    when(driverQuery.getById(2L))
        .thenReturn(DriverResponse.builder().id(2L).hasRequiredObligation(false).build());

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(100d).compareTo(obligationAmount));
  }

  @Test
  public void testObligationWithPositiveBalanceAndWithoutRequiredObligation() {
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
    when(driverQuery.getById(2L))
        .thenReturn(DriverResponse.builder().id(2L).hasRequiredObligation(false).build());

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(50d).compareTo(obligationAmount));
  }

  @Test
  public void testObligationWithZeroBalanceAndWithRequiredObligationBiggerThenCalculated() {
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
    when(driverQuery.getById(2L))
        .thenReturn(
            DriverResponse.builder()
                .id(2L)
                .hasRequiredObligation(true)
                .requiredObligation(BigDecimal.valueOf(200d))
                .build());

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(200d).compareTo(obligationAmount));
  }
  @Test
  public void testObligationWithZeroBalanceAndWithRequiredObligationLessThenCalculated() {
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
    when(driverQuery.getById(2L))
            .thenReturn(
                    DriverResponse.builder()
                            .id(2L)
                            .hasRequiredObligation(true)
                            .requiredObligation(BigDecimal.valueOf(50d))
                            .build());

    // when
    final var obligationAmount = instanceUnderTest.calculate(driverId, qWekId);

    // then
    assertEquals(0, BigDecimal.valueOf(100d).compareTo(obligationAmount));
  }
}
