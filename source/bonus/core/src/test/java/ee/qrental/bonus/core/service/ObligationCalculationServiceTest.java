package ee.qrental.bonus.core.service;

import static org.mockito.Mockito.mock;

import ee.qrental.bonus.api.out.ObligationAddPort;
import ee.qrental.bonus.api.out.ObligationCalculationAddPort;
import ee.qrental.bonus.api.out.ObligationLoadPort;
import ee.qrental.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrental.bonus.core.validator.ObligationCalculationAddBusinessRuleValidator;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import org.junit.jupiter.api.BeforeEach;

class ObligationCalculationServiceTest {

  private ObligationCalculationService instanceUnderTest;

  private GetQWeekQuery qWeekQuery;
  private GetBalanceQuery balanceQuery;
  private GetTransactionTypeQuery transactionTypeQuery;
  private GetTransactionQuery transactionQuery;
  private GetCarLinkQuery carLinkQuery;
  private GetUserAccountQuery userAccountQuery;
  private EmailSendUseCase emailSendUseCase;
  private ObligationCalculationAddPort calculationAddPort;
  private ObligationAddPort obligationAddPort;
  private ObligationLoadPort loadPort;
  private ObligationCalculationAddRequestMapper addRequestMapper;
  private ObligationCalculationAddBusinessRuleValidator addBusinessRuleValidator;
  private ObligationCalculator obligationCalculator;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    balanceQuery = mock(GetBalanceQuery.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    transactionQuery = mock(GetTransactionQuery.class);
    carLinkQuery = mock(GetCarLinkQuery.class);
    userAccountQuery = mock(GetUserAccountQuery.class);
    emailSendUseCase = mock(EmailSendUseCase.class);
    calculationAddPort = mock(ObligationCalculationAddPort.class);
    obligationAddPort = mock(ObligationAddPort.class);
    loadPort = mock(ObligationLoadPort.class);
    addRequestMapper = mock(ObligationCalculationAddRequestMapper.class);
    addBusinessRuleValidator = mock(ObligationCalculationAddBusinessRuleValidator.class);
    obligationCalculator = mock(ObligationCalculator.class);

    instanceUnderTest =
        new ObligationCalculationService(
            qWeekQuery,
            transactionQuery,
            carLinkQuery,
            userAccountQuery,
            emailSendUseCase,
            calculationAddPort,
            obligationAddPort,
            loadPort,
            addRequestMapper,
            addBusinessRuleValidator,
            obligationCalculator);
  }
  // TODO ...
}
