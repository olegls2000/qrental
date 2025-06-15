package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.bolt.api.in.query.GetBoltRidesCountQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.common.in.time.QDateTime;

import java.math.BigDecimal;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.*;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;

public class SimpleInsuranceStrategy extends AbstractInsuranceCalculationStrategy {

  private static final BigDecimal DAMAGE_LIMIT = BigDecimal.valueOf(600);

  private final GetTransactionTypeQuery transactionTypeQuery;
  private final TransactionAddUseCase transactionAddUseCase;
  private final QDateTime qDateTime;


  public SimpleInsuranceStrategy(
      final GetContractQuery contractQuery,
      final InsuranceCaseUpdatePort caseUpdatePort,
      final GetTransactionTypeQuery transactionTypeQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final QDateTime qDateTime) {
    super(contractQuery, caseUpdatePort);

    this.transactionTypeQuery = transactionTypeQuery;
    this.transactionAddUseCase = transactionAddUseCase;
    this.qDateTime = qDateTime;

  }

  @Override
  public boolean canApply(
      final DriverResponse driver, final QWeekResponse qWeek, InsuranceCase insuranceCase) {
    final var contract =
        getContractQuery().getActiveByDriverIdAndQWeekId(driver.getId(), qWeek.getId());
    if (contract == null) {

      return false;
    }
    final var contractStartDate = contract.getDateStart();
    final var occurrenceDate = insuranceCase.getOccurrenceDate();

    final var isCaseNew =
        occurrenceDate.isEqual(NEW_CONTRACTS_START_DATE)
            || occurrenceDate.isAfter(NEW_CONTRACTS_START_DATE);
    final var isContractNew =
        contractStartDate.isEqual(NEW_CONTRACTS_START_DATE)
            || contractStartDate.isAfter(NEW_CONTRACTS_START_DATE);

    return isCaseNew && isContractNew;
  }

  @Override
  public void apply(
      final DriverResponse driver,
      final QWeekResponse qWeek,
      final InsuranceCalculation calculation,
      final InsuranceCase insuranceCase) {
    final var driverId = driver.getId();
    final var qWeekId = qWeek.getId();

    createAndSaveDamageWriteOffTransaction(insuranceCase);
    final var writeOffTransaction = getDamageWriteOffTransaction(insuranceCase);
    final var writeOffTransactionId = transactionAddUseCase.add(writeOffTransaction);

    final var requestedBalance =
        InsuranceCaseBalance.builder()
            .insuranceCase(insuranceCase)
            .damageRemaining(ZERO)
            .selfResponsibilityRemaining(ZERO)
            .transactionIds(asList(writeOffTransactionId))
            .qWeekId(qWeekId)
            .withQKasko(FALSE)
            .build();
    calculation.getInsuranceCaseBalances().add(requestedBalance);
    checkAndDeactivateIfNecessary(requestedBalance, insuranceCase);
  }



  private void createAndSaveDamageWriteOffTransaction(final InsuranceCase insuranceCase) {
    final var transactionAddRequest = getDamageWriteOffTransaction(insuranceCase);
    transactionAddUseCase.add(transactionAddRequest);
  }

  private TransactionAddRequest getDamageWriteOffTransaction(final InsuranceCase insuranceCase) {
    final var writeOffAmount = getDamageWriteOffAmount(insuranceCase);
    final var damageWriteOffTransaction = new TransactionAddRequest();
    damageWriteOffTransaction.setComment(
        "Automatically created transaction for the damage compensation.");
    damageWriteOffTransaction.setDriverId(insuranceCase.getDriverId());
    damageWriteOffTransaction.setAmount(writeOffAmount);
    final var transactionTypeId =
        transactionTypeQuery.getByName(TRANSACTION_TYPE_DAMAGE_WRITE_OFF).getId();
    damageWriteOffTransaction.setTransactionTypeId(transactionTypeId);
    damageWriteOffTransaction.setDate(qDateTime.getToday());

    return damageWriteOffTransaction;
  }

  private BigDecimal getDamageWriteOffAmount(final InsuranceCase insuranceCase) {
    final var damage = insuranceCase.getDamageAmount();
    if (damage.compareTo(DAMAGE_LIMIT) >= 0) {

      return DAMAGE_LIMIT;
    } else {

      return damage;
    }
  }




}
