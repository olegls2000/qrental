package ee.qrental.insurance.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceAddPort;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class InsuranceCalculationUseCaseServiceTest {
  private InsuranceCalculationUseCaseService instanceUnderTest;

  private InsuranceCaseLoadPort insuranceCaseLoadPort;
  private InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort;
  private InsuranceCaseBalanceAddPort insuranceCaseBalanceAddPort;
  private GetTransactionQuery transactionQuery;
  private TransactionAddUseCase transactionAddUseCase;
  private GetTransactionTypeQuery transactionTypeQuery;
  private QDateTime qDateTime;

  @BeforeEach
  void init() {
    insuranceCaseLoadPort = mock(InsuranceCaseLoadPort.class);
    insuranceCaseBalanceLoadPort = mock(InsuranceCaseBalanceLoadPort.class);
    insuranceCaseBalanceAddPort = mock(InsuranceCaseBalanceAddPort.class);
    transactionQuery = mock(GetTransactionQuery.class);
    transactionAddUseCase = mock(TransactionAddUseCase.class);
    transactionTypeQuery = mock(GetTransactionTypeQuery.class);
    qDateTime = mock(QDateTime.class);

    instanceUnderTest =
        new InsuranceCalculationUseCaseService(
            insuranceCaseLoadPort,
            insuranceCaseBalanceLoadPort,
            insuranceCaseBalanceAddPort,
            transactionQuery,
            transactionAddUseCase,
            transactionTypeQuery,
            qDateTime);
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
