package ee.qrental.insurance.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.InsuranceCalculationAddRequestMapper;
import ee.qrental.insurance.domain.InsuranceCase;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static ee.qrental.constant.api.in.query.GetQWeekQuery.DEFAULT_COMPARATOR;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

@AllArgsConstructor
public class InsuranceCalculationUseCaseService implements InsuranceCalculationAddUseCase {

  private final String DAMAGE_COMPENSATION_TRANSACTION_TYPE_NAME = "damage payment";
  private static final BigDecimal DEFAULT_SELF_RESPONSIBILITY = BigDecimal.valueOf(500);
  private static final BigDecimal PERCENTAGE_FROM_RENT_AMOUNT = BigDecimal.valueOf(0.25d);

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCaseBalanceLoadPort caseBalanceLoadPort;
  private final InsuranceCaseBalanceAddPort caseBalanceAddPort;
  private final InsuranceCalculationLoadPort calculationLoadPort;
  private final InsuranceCalculationAddPort calculationAddPort;
  private final InsuranceCalculationAddRequestMapper calculationAddRequestMapper;
  private final GetTransactionQuery transactionQuery;
  private final TransactionAddUseCase transactionAddUseCase;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final GetQWeekQuery qWeekQuery;
  private final QDateTime qDateTime;

  private QWeekResponse getStartWeek(final InsuranceCalculationAddRequest request) {
    final var startCalculationQWeekId = calculationLoadPort.loadLastCalculatedQWeekId();
    if (startCalculationQWeekId == null) {

      return qWeekQuery.getById(request.getQWeekId());
    }

    return qWeekQuery.getById(startCalculationQWeekId);
  }

  private QWeekResponse getEndWeek(final InsuranceCalculationAddRequest request) {

    return qWeekQuery.getById(request.getQWeekId());
  }

  @Transactional
  @Override
  public Long add(final InsuranceCalculationAddRequest request) {
    final var calculationStartTime = System.currentTimeMillis();

    final var startWeek = getStartWeek(request);
    final var startWeekId = startWeek.getId();
    final var endWeek = getEndWeek(request);
    final var endWeekId = endWeek.getId();
    final var domain = calculationAddRequestMapper.toDomain(request);
    domain.setStartQWeekId(startWeekId);
    final var weeksForCalculation =
        qWeekQuery.getQWeeksFromPeriodOrdered(startWeekId, endWeekId, DEFAULT_COMPARATOR);
    weeksForCalculation.forEach(
        week ->
            caseLoadPort
                .loadActive()
                .forEach(insuranceCase -> handleInsuranceCase(week, insuranceCase)));
    calculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Insurance Cases Balance Calculation took %d milli seconds \n",
        calculationDuration);
    return null;
  }

  private void handleInsuranceCase(final QWeekResponse week, final InsuranceCase insuranceCase) {
    final var qWeekId = week.getId();
    final var driverId = insuranceCase.getDriverId();
    final var insuranceCaseBalance = getInsuranceCaseBalance(insuranceCase, qWeekId);
    final var damageCompensationAmount = getDamageCompensationAmount(driverId, qWeekId);
    final var damageTransaction =
        getTransactionAddRequest(driverId, damageCompensationAmount, week);
    reduceInsuranceCaseBalanceOrTransactionAmount(insuranceCaseBalance, damageTransaction);
    transactionAddUseCase.add(damageTransaction);
    caseBalanceAddPort.add(insuranceCaseBalance);
  }

  private TransactionAddRequest getTransactionAddRequest(
      final Long driverId,
      final BigDecimal amountForDamageCompensation,
      final QWeekResponse qWeek) {
    final var damagePaymentTransaction = new TransactionAddRequest();
    damagePaymentTransaction.setDate(qDateTime.getToday());
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

  private void reduceInsuranceCaseBalanceOrTransactionAmount(
      final InsuranceCaseBalance balance, final TransactionAddRequest damagePaymentTransaction) {
    final var damageRemaining = balance.getDamageRemaining();
    final var damagePaymentTransactionAmount = damagePaymentTransaction.getAmount();
    if (damageRemaining.compareTo(damagePaymentTransactionAmount) > 0) {
      balance.setDamageRemaining(damageRemaining.subtract(damagePaymentTransactionAmount));
    } else {
      balance.setDamageRemaining(ZERO);
      damagePaymentTransaction.setAmount(damageRemaining);
    }
  }

  private InsuranceCaseBalance getInsuranceCaseBalance(
      final InsuranceCase insuranceCase, final Long qWeekId) {
    var latestBalance = caseBalanceLoadPort.loadLatestByInsuranceCseId(insuranceCase.getId());
    if (latestBalance == null) {
      final var damageRemaining =
          insuranceCase.getDamageAmount().subtract(DEFAULT_SELF_RESPONSIBILITY);
      return InsuranceCaseBalance.builder()
          .id(null)
          .damageRemaining(damageRemaining)
          .selfResponsibilityRemaining(DEFAULT_SELF_RESPONSIBILITY)
          .qWeekId(qWeekId)
          .build();
    }
    return InsuranceCaseBalance.builder()
        .id(null)
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
