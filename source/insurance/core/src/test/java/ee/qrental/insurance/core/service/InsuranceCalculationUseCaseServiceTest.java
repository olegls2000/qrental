package ee.qrental.insurance.core.service;

import static org.mockito.Mockito.mock;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsuranceCalculationUseCaseServiceTest {
  private InsuranceCalculationUseCaseService instanceUnderTest;

  private InsuranceCaseLoadPort insuranceCaseLoadPort;
  private InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort;
  private InsuranceCaseBalanceAddPort insuranceCaseBalanceAddPort;
  private InsuranceCaseBalanceDeriveService deriveService;
  private InsuranceCalculationLoadPort calculationLoadPort;
  private InsuranceCalculationAddPort calculationAddPort;
  private InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private GetTransactionQuery transactionQuery;
  private TransactionAddUseCase transactionAddUseCase;
  private GetTransactionTypeQuery transactionTypeQuery;
  private GetQWeekQuery qWeekQuery;

  @BeforeEach
  void init() {
    insuranceCaseLoadPort = mock(InsuranceCaseLoadPort.class);
    insuranceCaseBalanceLoadPort = mock(InsuranceCaseBalanceLoadPort.class);
    deriveService = mock(InsuranceCaseBalanceDeriveService.class);
    calculationLoadPort = mock(InsuranceCalculationLoadPort.class);
    insuranceCaseBalanceAddPort = mock(InsuranceCaseBalanceAddPort.class);
    calculationAddRequestMapper = mock(InsuranceCalculationAddRequestMapper.class);
    transactionQuery = mock(GetTransactionQuery.class);
    transactionAddUseCase = mock(TransactionAddUseCase.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    qWeekQuery = mock(GetQWeekQuery.class);

    instanceUnderTest =
        new InsuranceCalculationUseCaseService(
            insuranceCaseLoadPort,
            insuranceCaseBalanceLoadPort,
            deriveService,
            calculationLoadPort,
            calculationAddPort,
            calculationAddRequestMapper,
            transactionQuery,
            transactionAddUseCase,
            transactionTypeQuery,
            qWeekQuery);
  }

  @Test
  public void testIfNoActiveInsuranceCase() {
    // given
    // when
    // then
  }

  @Test
  public void testIf___() {
    // given
    // when
    // then
  }
}
