package ee.qrental.insurance.core.service.balance;

import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsuranceCaseBalanceDeriveServiceTest {

  private final InsuranceCaseBalanceDeriveService instanceUnderTest =
      new InsuranceCaseBalanceDeriveService();

  @Test
  public void testIfNoSelfRespRequestAndAutomaticWriteOffLessThanDamageRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(200))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(ZERO);

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(500), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(ZERO, selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(130), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(70), damageTransaction.getAmount());
  }

  @Test
  public void testIfNoSelfRespRequestAndAutomaticWriteOffGreaterThanDamageRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(50))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(ZERO);

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(480), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(20), selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(50), damageTransaction.getAmount());
  }

  @Test
  public void
      testIfNoSelfRespRequestAndAutomaticWriteOffGreaterThenSumOfDamageRemainingAndSelfResponsibilityRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(10))
            .damageRemaining(BigDecimal.valueOf(50))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(ZERO);

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(10), selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(50), damageTransaction.getAmount());
  }

  @Test
  public void testIfNoSelfRespRequestAndNonZeroAutomaticWriteOffAndZeroDamageRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(0))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(ZERO);

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(430), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(70), selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(0), damageTransaction.getAmount());
  }

  @Test
  public void
      testIfNoSelfRespRequestAndAutomaticWriteOffLessThenSumOfDamageRemainingAndSelfResponsibilityRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(50))
            .damageRemaining(BigDecimal.valueOf(50))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(ZERO);

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(30), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(20), selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(50), damageTransaction.getAmount());
  }

  @Test
  public void testIfNonZeroSelfRespRequestLessThanSelfResponsibilityRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(70))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(BigDecimal.valueOf(400));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(100), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(400), selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(70), damageTransaction.getAmount());
  }

  @Test
  public void testIfNonZeroSelfRespRequestEqualToSelfResponsibilityRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(70))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(BigDecimal.valueOf(500));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(500), selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(70), damageTransaction.getAmount());
  }

  @Test
  public void testIfNonZeroSelfRespRequestGreaterThanSelfResponsibilityRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(70))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(70));
    final var selfResponsibilityTransaction = getTransactionAddRequest(BigDecimal.valueOf(600));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransaction);

    // then
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(500), selfResponsibilityTransaction.getAmount());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(70), damageTransaction.getAmount());
  }

  private TransactionAddRequest getTransactionAddRequest(final BigDecimal amount) {
    final var damageTransaction = new TransactionAddRequest();
    damageTransaction.setAmount(amount);

    return damageTransaction;
  }
}
