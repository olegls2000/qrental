package ee.qrental.insurance.core.service;

import static org.mockito.Mockito.mock;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceCalculator;
import ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceDeriveService;
import ee.qrental.insurance.core.validator.InsuranceCalculationAddBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InsuranceCalculationUseCaseServiceTest {
  private InsuranceCalculationUseCaseService instanceUnderTest;

  private InsuranceCaseLoadPort caseLoadPort;
  private InsuranceCaseUpdatePort caseUpdatePort;
  private InsuranceCalculationAddPort calculationAddPort;
  private InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private GetQWeekQuery qWeekQuery;
  private InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;
  private InsuranceCalculationAddBusinessRuleValidator addBusinessRuleValidator;

  @BeforeEach
  void init() {
    caseLoadPort = mock(InsuranceCaseLoadPort.class);
    caseUpdatePort = mock(InsuranceCaseUpdatePort.class);
    calculationAddPort = mock(InsuranceCalculationAddPort.class);
    calculationAddRequestMapper = mock(InsuranceCalculationAddRequestMapper.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    insuranceCaseBalanceCalculator = mock(InsuranceCaseBalanceCalculator.class);
    addBusinessRuleValidator = mock(InsuranceCalculationAddBusinessRuleValidator.class);

    instanceUnderTest =
        new InsuranceCalculationUseCaseService(
            caseLoadPort,
            caseUpdatePort,
            calculationAddPort,
            calculationAddRequestMapper,
            qWeekQuery,
            insuranceCaseBalanceCalculator,
            addBusinessRuleValidator);
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
