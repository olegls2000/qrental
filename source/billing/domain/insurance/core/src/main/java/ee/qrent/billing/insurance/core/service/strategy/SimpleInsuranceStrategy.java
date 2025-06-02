package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.common.in.time.QDateTime;

import java.math.BigDecimal;
import java.util.Arrays;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

public class SimpleInsuranceStrategy extends AbstractInsuranceCalculationStrategy {

  private static final BigDecimal DAMAGE_LIMIT = BigDecimal.valueOf(600);

  private final InsuranceCaseLoadPort caseLoadPort;
  private final GetTransactionQuery transactionQuery;
  private final TransactionAddUseCase transactionAddUseCase;
  private final QDateTime qDateTime;

  public SimpleInsuranceStrategy(
      final GetContractQuery contractQuery,
      final InsuranceCaseLoadPort caseLoadPort,
      final GetTransactionQuery transactionQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final QDateTime qDateTime) {
    super(contractQuery);
    this.caseLoadPort = caseLoadPort;
    this.transactionQuery = transactionQuery;
    this.transactionAddUseCase = transactionAddUseCase;
    this.qDateTime = qDateTime;
  }

  @Override
  public boolean canApply(
      final DriverResponse driver, final QWeekResponse qWeek, InsuranceCase insuranceCase) {
    final var contract =
        getContractQuery().getActiveByDriverIdAndQWeekId(driver.getId(), qWeek.getId());

    final var isCaseNew =
        insuranceCase.getOccurrenceDate().isEqual(NEW_CONTRACTS_START_DATE)
            || insuranceCase.getOccurrenceDate().isAfter(NEW_CONTRACTS_START_DATE);

    return contract.getDateStart().isEqual(NEW_CONTRACTS_START_DATE)
        || contract.getDateEnd().isAfter(NEW_CONTRACTS_START_DATE) && isCaseNew;
  }

  @Override
  public void apply(
      final DriverResponse driver,
      final QWeekResponse qWeek,
      final InsuranceCalculation calculation,
      final InsuranceCase insuranceCase) {
    final var driverId = driver.getId();
    final var qWeekId = qWeek.getId();
    final var driverInfo =
        format(
            "Driver: %s %s, tax number: %d",
            driver.getFirstName(), driver.getLastName(), driver.getTaxNumber());
    final var weekInfo = format("QWeek: %d - %d", qWeek.getYear(), qWeek.getNumber());
    final var strategyInfo = this.getClass().getSimpleName();
    System.out.println(
        format("Strategy %s, will be applied for %s and %s", strategyInfo, driverInfo, weekInfo));

    final var activeCases = caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (activeCases.isEmpty()) {
      System.out.println(format("No Active insurance cases for %s and %s", driverInfo, weekInfo));

      return;
    }
    final var activeCaseForProcessing = activeCases.stream().findFirst().get();
    final var weeklyTransaction = getWeeklyPaymentTransaction(driverId, qWeekId);
    final var writeOffTransaction = getDamageWriteOffTransaction(driverId, activeCaseForProcessing);
    final var weeklyTransactionId = transactionAddUseCase.add(weeklyTransaction);
    final var writeOffTransactionId = transactionAddUseCase.add(writeOffTransaction);

    final var requestedBalance =
        InsuranceCaseBalance.builder()
            .insuranceCase(activeCaseForProcessing)
            .damageRemaining(ZERO)
            .selfResponsibilityRemaining(ZERO)
            .transactionIds(Arrays.asList(weeklyTransactionId, writeOffTransactionId))
            .qWeekId(qWeekId)
            .withQKasko(FALSE)
            .build();
  }

  private TransactionAddRequest getDamageWriteOffTransaction(
      final Long driverId, final InsuranceCase insuranceCase) {

    final var writeOffAmount = getDamageWriteOffAmount(insuranceCase);
    final var damageWriteOffTransaction = new TransactionAddRequest();
    damageWriteOffTransaction.setComment(
        "Automatically created transaction for the damage compensation.");
    damageWriteOffTransaction.setDriverId(driverId);
    damageWriteOffTransaction.setAmount(writeOffAmount);

    // TODO as about type
    final var transactionTypeNameForDamage = "damage payment";
    /* damageWriteOffTransaction.setTransactionTypeId(
    getTransactionTypeIdByName(transactionTypeNameForDamage));*/
    damageWriteOffTransaction.setDate(qDateTime.getToday());

    return damageWriteOffTransaction;
  }

  private BigDecimal getDamageWriteOffAmount(InsuranceCase insuranceCase) {
    final var damage = insuranceCase.getDamageAmount();
    if (damage.compareTo(DAMAGE_LIMIT) >= 0) {
      return DAMAGE_LIMIT;
    } else {
      return damage;
    }
  }

  private TransactionAddRequest getWeeklyPaymentTransaction(
      final Long driverId, final Long qWeekId) {

    final var rentAmount =
        transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            .filter(
                transactionResponse ->
                    TRANSACTION_TYPE_NAME_WEEKLY_RENT.equals(transactionResponse.getType()))
            .map(tr -> tr.getRealAmount())
            .reduce(BigDecimal::add)
            .orElse(ZERO);

    final var transactionAmount = rentAmount.multiply(BigDecimal.valueOf(0.05));

    final var insurancePaymentTransaction = new TransactionAddRequest();
    insurancePaymentTransaction.setComment("Weekly Insurance payment for the new drivers");
    insurancePaymentTransaction.setDriverId(driverId);
    insurancePaymentTransaction.setAmount(transactionAmount);

    // TODO add type ?
    final var transactionTypeNameForSelfResponsibility = "self responsibility payment";
    insurancePaymentTransaction.setDate(qDateTime.getToday());

    return insurancePaymentTransaction;
  }
}
