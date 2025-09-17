package ee.qrent.billing.transaction.core.service.balance;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class BalanceQueryServiceTest {

  private GetBalanceQuery instanceUnderTest;
  private GetDriverQuery driverQuery;
  private GetQWeekQuery qWeekQuery;
  private GetTransactionQuery transactionQuery;
  private GetTransactionKindQuery transactionKindQuery;
  private BalanceLoadPort balanceLoadPort;
  private BalanceResponseMapper balanceResponseMapper;
  private BalanceCalculatorStrategy calculatorStrategies;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    transactionKindQuery = mock(GetTransactionKindQuery.class);
    balanceLoadPort = mock(BalanceLoadPort.class);
    balanceResponseMapper = mock(BalanceResponseMapper.class);
    calculatorStrategies = mock(BalanceCalculatorStrategy.class);

    instanceUnderTest =
        new BalanceQueryService(
            driverQuery,
            qWeekQuery,
            transactionQuery,
            transactionKindQuery,
            balanceLoadPort,
            balanceResponseMapper,
            asList(calculatorStrategies));
  }

  @Test
  public void testGetRawContextByDriverIdAndQWeekIdIfDriverDoesntHaveBalances() {
    // given
    final var driverId = 1L;
    final Long requestedWeekId = 10L;
    final Long previousWeekId = 9L;
    when(qWeekQuery.getOneBeforeById(requestedWeekId))
        .thenReturn(QWeekResponse.builder().id(previousWeekId).build());

    when(balanceLoadPort.loadLatestByDriver(driverId)).thenReturn(null);
    when(balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, requestedWeekId, true))
        .thenReturn(null);
    when(balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, previousWeekId, true))
        .thenReturn(null);

    // when
    final var rawContext =
        instanceUnderTest.getRawContextByDriverIdAndQWeekId(driverId, requestedWeekId);

    // then
    assertNull(rawContext);
  }
}
