package ee.qrent.billing.bonus.core.service;

import static org.mockito.Mockito.mock;

import ee.qrent.billing.bonus.api.out.ObligationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationCalculationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrent.billing.bonus.core.validator.ObligationCalculationAddRequestValidator;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import org.junit.jupiter.api.BeforeEach;

class ObligationCalculationServiceTest {

  private ObligationCalculationService instanceUnderTest;

  private GetQWeekQuery qWeekQuery;
  private GetBalanceQuery balanceQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private GetTransactionQuery transactionQuery;
  private GetCarLinkQuery carLinkQuery;
  private GetUserAccountQuery userAccountQuery;
  private QueueEntryPushUseCase queueEntryPushUseCase;
  private ObligationCalculationAddPort calculationAddPort;
  private ObligationAddPort obligationAddPort;
  private ObligationLoadPort loadPort;
  private ObligationCalculationAddRequestMapper addRequestMapper;
  private ObligationCalculationAddRequestValidator addRequestValidator;
  private ObligationCalculator obligationCalculator;
  private QDateTime qDateTime;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    balanceQuery = mock(GetBalanceQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    carLinkQuery = mock(GetCarLinkQuery.class);
    userAccountQuery = mock(GetUserAccountQuery.class);
    queueEntryPushUseCase = mock(QueueEntryPushUseCase.class);
    calculationAddPort = mock(ObligationCalculationAddPort.class);
    obligationAddPort = mock(ObligationAddPort.class);
    loadPort = mock(ObligationLoadPort.class);
    addRequestMapper = mock(ObligationCalculationAddRequestMapper.class);
    addRequestValidator = mock(ObligationCalculationAddRequestValidator.class);
    obligationCalculator = mock(ObligationCalculator.class);
    qDateTime = mock(QDateTime.class);

    instanceUnderTest =
        new ObligationCalculationService(
            qWeekQuery,
            transactionQuery,
            carLinkQuery,
            userAccountQuery,
            queueEntryPushUseCase,
            calculationAddPort,
            obligationAddPort,
            loadPort,
            addRequestMapper,
            addRequestValidator,
            obligationCalculator,
            qDateTime);
  }
  // TODO ...
}
