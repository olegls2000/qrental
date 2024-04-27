package ee.qrental.transaction.core.service.balance;

import ee.qrental.transaction.core.service.balance.calculator.BalanceDeriveService;
import ee.qrental.transaction.domain.balance.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BalanceDeriveServiceTest {
  private BalanceDeriveService instanceUnderTest;

  @BeforeEach
  void init() {
    instanceUnderTest = new BalanceDeriveService();
  }

  @Test
  public void testGetBonusCode() {

    // given
    final var balanceToDerive =
        Balance.builder()
            .positiveAmount(BigDecimal.valueOf(100))
            .feeAmount(BigDecimal.valueOf(100))
            .nonFeeAbleAmount(BigDecimal.valueOf(100))
            .repairmentAmount(BigDecimal.valueOf(0))
            .derived(false)
            .build();

    // when
    final var derivedBalance = instanceUnderTest.getDerivedBalance(balanceToDerive);

    // then
    assertTrue(derivedBalance.getDerived());
  }
}
