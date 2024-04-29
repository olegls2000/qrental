package ee.qrental.transaction.core.service.balance.calculator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.domain.balance.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceCalculationStrategyDryRunTest {

  private BalanceCalculationStrategyDryRun instanceUnderTest;
  private BalanceDeriveService balanceDeriveService;
  private GetConstantQuery constantQuery;

  @BeforeEach
  void init() {

    balanceDeriveService = mock(BalanceDeriveService.class);
    constantQuery = mock(GetConstantQuery.class);

    instanceUnderTest = new BalanceCalculationStrategyDryRun(balanceDeriveService, constantQuery);
  }

  @Test
  public void testIfNoPreviousWeek() {
    // given
    final var driver = DriverResponse.builder().id(77L).needFee(Boolean.FALSE).build();

    final var qWeek = QWeekResponse.builder().id(11L).build();
    final var previousBalance = Balance.builder().build();
    final var transactions = Balance.builder().build();

    final var qWeekId = 5L;
    final var addRequest = new
            ansactions ansactionsObligationCalculationAddRequest();
    addRequest.setQWeekId(qWeekId);
    addRequest.setComment("test comment");

    final var currentQWeekId = 10L;
    final var currentQWeek = QWeekResponse.builder().id(currentQWeekId).build();
    when(qWeekQuery.getCurrentWeek()).thenReturn(currentQWeek);
    when(qWeekQuery.getOneBeforeById(qWeekId)).thenReturn(null);

    // when
    final var violationsCollector = instanceUnderTest.validate(addRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
    assertTrue(violationsCollector.getViolations().isEmpty());
  }
}
