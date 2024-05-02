package ee.qrental.transaction.core.service.balance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;

class BalanceQueryServiceTest {

  private GetBalanceQuery instanceUnderTest;
  private GetDriverQuery driverQuery;
  private GetQWeekQuery qWeekQuery;
  private GetTransactionQuery transactionQuery;
  private GetTransactionKindQuery transactionKindQuery;
  private BalanceLoadPort balanceLoadPort;
  private TransactionLoadPort transactionLoadPort;
  private BalanceResponseMapper balanceResponseMapper;
  private BalanceCalculatorStrategy calculatorStrategies;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    driverQuery = mock(GetDriverQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    transactionKindQuery = mock(GetTransactionKindQuery.class);
    balanceLoadPort = mock(BalanceLoadPort.class);
    transactionLoadPort = mock(TransactionLoadPort.class);
    balanceResponseMapper = mock(BalanceResponseMapper.class);
    calculatorStrategies = mock(BalanceCalculatorStrategy.class);

    instanceUnderTest =
        new BalanceQueryService(
            driverQuery,
            qWeekQuery,
            transactionQuery,
            transactionKindQuery,
            balanceLoadPort,
            transactionLoadPort,
            balanceResponseMapper,
            Arrays.asList(calculatorStrategies));
  }
}
