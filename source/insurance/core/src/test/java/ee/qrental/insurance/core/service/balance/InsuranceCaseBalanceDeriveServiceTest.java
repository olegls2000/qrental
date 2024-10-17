package ee.qrental.insurance.core.service.balance;

import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsuranceCaseBalanceDeriveServiceTest {

  private final InsuranceCaseBalanceDeriveService instanceUnderTest =
      new InsuranceCaseBalanceDeriveService();

  @Test
  public void testIfNoSRRequestAndSRRemainingGreaterThanAutomaticTransactionAmount() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(100));

    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt = Optional.empty();

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(400), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(100), balanceToDerive.getDamageRemaining());
  }

  @Test
  public void testIfNoSRRequestAndSRRemainingLessThanAutomaticTransactionAmount() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(50))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(100));
    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt = Optional.empty();

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(50), balanceToDerive.getDamageRemaining());
  }

  @Test
  public void testIfSRRequestLesThanSRRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(100));
    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt =
        Optional.of(getTransactionAddRequest(BigDecimal.valueOf(450)));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(50), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
  }

  @Test
  public void testIfSRRequestEqualsSRRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(100));
    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt =
        Optional.of(getTransactionAddRequest(BigDecimal.valueOf(500)));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
  }

  @Test
  public void testIfSRRequestGreaterThanSRRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(100));
    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt =
        Optional.of(getTransactionAddRequest(BigDecimal.valueOf(550)));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
  }

  @Test
  public void testIfAutomaticTransactionAmountLessThanDamageRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(60));
    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt =
        Optional.of(getTransactionAddRequest(BigDecimal.valueOf(200)));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(300), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(40), balanceToDerive.getDamageRemaining());
  }

  @Test
  public void testIfAutomaticTransactionAmountEqualsToDamageRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(100));
    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt =
        Optional.of(getTransactionAddRequest(BigDecimal.valueOf(200)));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(300), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
  }

  @Test
  public void testIfAutomaticTransactionAmountGreaterThanDamageRemaining() {
    // given
    final var balanceToDerive =
        InsuranceCaseBalance.builder()
            .selfResponsibilityRemaining(BigDecimal.valueOf(500))
            .damageRemaining(BigDecimal.valueOf(100))
            .build();
    final var damageTransaction = getTransactionAddRequest(BigDecimal.valueOf(140));
    final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt =
        Optional.of(getTransactionAddRequest(BigDecimal.valueOf(200)));

    // when
    instanceUnderTest.derive(balanceToDerive, damageTransaction, selfResponsibilityTransactionOpt);

    // then
    assertEquals(BigDecimal.valueOf(300), balanceToDerive.getSelfResponsibilityRemaining());
    assertEquals(BigDecimal.valueOf(0), balanceToDerive.getDamageRemaining());
    assertEquals(BigDecimal.valueOf(100), damageTransaction.getAmount());
  }

  private TransactionAddRequest getTransactionAddRequest(final BigDecimal amount) {
    final var damageTransaction = new TransactionAddRequest();
    damageTransaction.setAmount(amount);

    return damageTransaction;
  }
}
