package ee.qrental.insurance.core.service;

import static ee.qrental.constant.api.in.query.GetQWeekQuery.DEFAULT_COMPARATOR;
import static ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceCalculatorStrategy.SAVING;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.*;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrental.insurance.core.service.balance.InsuranceCaseBalanceCalculatorStrategy;
import ee.qrental.insurance.domain.InsuranceCase;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final String DAMAGE_COMPENSATION_TRANSACTION_TYPE_NAME = "damage payment";
  private static final BigDecimal DEFAULT_SELF_RESPONSIBILITY = BigDecimal.valueOf(500);
  private static final BigDecimal PERCENTAGE_FROM_RENT_AMOUNT = BigDecimal.valueOf(0.25d);

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCaseBalanceLoadPort caseBalanceLoadPort;
  private final InsuranceCalculationLoadPort calculationLoadPort;
  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final GetQWeekQuery qWeekQuery;
  private final List<InsuranceCaseBalanceCalculatorStrategy> calculatorStrategies;

  @Transactional
  @Override
  public Long add(final InsuranceCalculationAddRequest request) {
    System.out.println("----> Insurance Cases Balance Calculation started ...");
    final var calculationStartTime = System.currentTimeMillis();
    final var startWeekId = getStartWeekId();
    final var endWeekId = getEndWeekId(request);
    final var domain = calculationAddRequestMapper.toDomain(request);
    domain.setStartQWeekId(startWeekId);
    final var saveStrategy = getSaveStrategy();
    final var weeksForCalculation =
        qWeekQuery.getQWeeksFromPeriodOrdered(startWeekId, endWeekId, DEFAULT_COMPARATOR);
    weeksForCalculation.forEach(
        qWeek -> {
          final var qWeekId = qWeek.getId();
          final var activeCases = caseLoadPort.loadActiveByQWeekId(qWeekId);
          if (activeCases.isEmpty()) {
            return;
          }
          final var activeCase = activeCases.stream().findFirst().get();
          System.out.println();
          final var previousWeekBalance = getPreviousWeekBalance(activeCase, qWeek);
          final var requestedWeekBalance =
              saveStrategy.calculateBalance(activeCase, qWeek, previousWeekBalance);
          domain.getInsuranceCaseBalances().add(requestedWeekBalance);
        });
    final var savedCalculation = calculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Insurance Cases Balance Calculation took %d milli seconds \n",
        calculationDuration);
    return savedCalculation.getId();
  }

  private InsuranceCaseBalance getPreviousWeekBalance(
      final InsuranceCase insuranceCase, final QWeekResponse qWeek) {
    final var previousQWeek = qWeekQuery.getOneBeforeById(qWeek.getId());
    final var previousQWeekId = previousQWeek.getId();
    final var previousWeekBalance =
        caseBalanceLoadPort.loadByInsuranceCaseIdAndQWeekId(insuranceCase.getId(), previousQWeekId);

    return previousWeekBalance;
  }

  private InsuranceCaseBalanceCalculatorStrategy getSaveStrategy() {
    return calculatorStrategies.stream()
        .filter(strategy -> strategy.canApply(SAVING))
        .findFirst()
        .orElseThrow(
            () ->
                new RuntimeException(
                    "No Insurance Case Balance Calculator strategy found for 'saving'"));
  }

  private Long getStartWeekId() {
    final var lastCalculatedQWeekId = calculationLoadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {

      return qWeekQuery.getFirstWeek().getId();
    }

    final var startWeek = qWeekQuery.getOneAfterById(lastCalculatedQWeekId);
    if (startWeek == null) {
      throw new RuntimeException(
          "Could not find start week for Insurance calculation. Make sure that Week after week with id: "
              + lastCalculatedQWeekId
              + " exists.");
    }

    return startWeek.getId();
  }

  private Long getEndWeekId(final InsuranceCalculationAddRequest request) {
    return request.getQWeekId();
  }

  private TransactionAddRequest getDamageTransaction(
      final Long driverId,
      final QWeekResponse qWeek,
      final InsuranceCaseBalance insuranceCaseBalance) {
    var amountForDamageCompensation = getDamageCompensationAmount(driverId, qWeek.getId());

    if (amountForDamageCompensation.compareTo(ZERO) == 0) {
      amountForDamageCompensation = insuranceCaseBalance.getDamageRemaining();
    }

    final var damagePaymentTransaction = new TransactionAddRequest();
    damagePaymentTransaction.setComment(
        "Automatically created transaction for the damage compensation.");
    damagePaymentTransaction.setDriverId(driverId);
    damagePaymentTransaction.setAmount(amountForDamageCompensation);
    damagePaymentTransaction.setTransactionTypeId(getDamageCompensationTransactionTypeId());
    damagePaymentTransaction.setDate(qWeek.getStart());

    return damagePaymentTransaction;
  }

  private BigDecimal getDamageCompensationAmount(final Long driverId, final Long qWeekId) {
    final var rentAndNonLabelFineTransactionsAmountAbs =
        getAbsRentAndNonLabelFineTransactionsAmountAbs(driverId, qWeekId);

    return rentAndNonLabelFineTransactionsAmountAbs.multiply(PERCENTAGE_FROM_RENT_AMOUNT);
  }

  private InsuranceCaseBalance getInsuranceCaseBalance(
      final InsuranceCase insuranceCase, final Long qWeekId) {
    var latestBalance = caseBalanceLoadPort.loadLatestByInsuranceCaseId(insuranceCase.getId());
    if (latestBalance == null) {
      final var damageRemaining =
          insuranceCase.getDamageAmount().subtract(DEFAULT_SELF_RESPONSIBILITY);
      return InsuranceCaseBalance.builder()
          .id(null)
          .insuranceCase(insuranceCase)
          .damageRemaining(damageRemaining)
          .selfResponsibilityRemaining(DEFAULT_SELF_RESPONSIBILITY)
          .qWeekId(qWeekId)
          .build();
    }
    return InsuranceCaseBalance.builder()
        .id(null)
        .insuranceCase(insuranceCase)
        .damageRemaining(latestBalance.getDamageRemaining())
        .selfResponsibilityRemaining(latestBalance.getSelfResponsibilityRemaining())
        .qWeekId(qWeekId)
        .build();
  }

  private BigDecimal getAbsRentAndNonLabelFineTransactionsAmountAbs(
      final Long driverId, final Long qWeekId) {
    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(
            transaction ->
                List.of(TRANSACTION_TYPE_NAME_WEEKLY_RENT, TRANSACTION_TYPE_NO_LABEL_FINE)
                    .contains(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }

  private BigDecimal getAbsSelfResponsibilityTransactionsAmountAbs(
      final Long driverId, final Long qWeekId) {
    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(transaction -> TRANSACTION_TYPE_SELF_RESPONSIBILITY.equals(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }

  final Long getDamageCompensationTransactionTypeId() {
    final var damageCompensationTransactionType =
        transactionTypeQuery.getByName(DAMAGE_COMPENSATION_TRANSACTION_TYPE_NAME);
    if (damageCompensationTransactionType == null) {
      throw new RuntimeException(
          format(
              "Transaction Type with name: %s, does not exist. "
                  + "Please create it, before Insurance Case Damage calculation",
              DAMAGE_COMPENSATION_TRANSACTION_TYPE_NAME));
    }
    return damageCompensationTransactionType.getId();
  }
}
