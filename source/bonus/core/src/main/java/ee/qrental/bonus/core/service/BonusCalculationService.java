package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.*;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrental.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrental.bonus.api.in.usecase.BonusCalculationAddUseCase;
import ee.qrental.bonus.api.out.*;
import ee.qrental.bonus.core.mapper.BonusCalculationAddRequestMapper;
import ee.qrental.bonus.core.validator.BonusCalculationAddBusinessRuleValidator;
import ee.qrental.bonus.domain.BonusCalculationResult;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import ee.qrental.user.api.in.response.UserAccountResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationService implements BonusCalculationAddUseCase {
  private final GetQWeekQuery qWeekQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final BonusProgramLoadPort bonusProgramLoadPort;
  private final GetCarLinkQuery carLinkQuery;
  private final GetUserAccountQuery userAccountQuery;
  private final EmailSendUseCase emailSendUseCase;
  private final BonusCalculationAddPort calculationAddPort;
  private final ObligationLoadPort obligationLoadPort;
  private final BonusCalculationAddRequestMapper addRequestMapper;
  private final BonusCalculationAddBusinessRuleValidator addBusinessRuleValidator;

  @Transactional
  @Override
  public void add(final BonusCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addBusinessRuleValidator.validate(addRequest);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());
      return;
    }
    final var domain = addRequestMapper.toDomain(addRequest);
    final var qWeek = qWeekQuery.getById(addRequest.getQWeekId());
    final var qWeekId = qWeek.getId();
    final var transactionTypes = getRentTransactionTypes();
    final var previousWeek = qWeekQuery.getOneBeforeById(qWeekId);
    final var activeBonusPrograms = bonusProgramLoadPort.loadAll();
    carLinkQuery.getActive().stream()
        .map(CarLinkResponse::getDriverId)
        .forEach(
            driverId -> {
              final var obligation =
                  obligationLoadPort.loadByDriverIdAndByQWeekId(driverId, qWeekId);
              final var bonusTransactionAddRequests = new ArrayList<TransactionAddRequest>();
              activeBonusPrograms.stream()
                  .forEach(
                      bonusProgram -> {
                        switch (bonusProgram.getCode()) {
                          case "2W" ->
                              handle2WeeksBonusProgram(obligation, bonusTransactionAddRequests);
                          case "4W" ->
                              handle4WeeksBonusProgram(obligation, bonusTransactionAddRequests);
                        }
                      });

              //final var result = getResult(savedObligation.getId());
              //domain.getResults().add(result);
            });
    calculationAddPort.add(domain);

    sendEmails(domain.getResults(), qWeek.getNumber());
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Obligation Calculation took %d milli seconds \n", calculationDuration);
  }

  private void handle2WeeksBonusProgram(
      final Obligation obligation, final List<TransactionAddRequest> bonusTransactions) {
    if (obligation.getMatchCount() < 4) {
      return;
    }
    final var driverId = obligation.getDriverId();
    final var qWeekId = obligation.getQWeekId();
    final var positiveAmount = getPositiveAmount(driverId, qWeekId);
    final var bonusThreshold = obligation.getObligationAmount().multiply(BigDecimal.valueOf(2l));
    if (positiveAmount.compareTo(bonusThreshold) > 0) {
      final var bonusTransaction = new TransactionAddRequest();
      bonusTransaction.setDate(LocalDate.now());
      bonusTransaction.setComment("Bonus Transaction for 2W prepayment");
      bonusTransaction.setDriverId(driverId);
      bonusTransaction.setAmount(
          obligation.getObligationAmount().multiply(BigDecimal.valueOf(0.05)));
      bonusTransaction.setTransactionTypeId(transactionTypeQuery.getByName("bonus").getId());

      bonusTransactions.add(bonusTransaction);
    }
  }

  private BigDecimal getPositiveAmount(final Long driverId, final Long qWeekId) {
    final var positiveAmount =
        transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            .filter(tr -> "P".equals(tr.getKind()))
            .map(TransactionResponse::getRealAmount)
            .reduce(ZERO, BigDecimal::add);

    return positiveAmount;
  }

  private void handle4WeeksBonusProgram(
      final Obligation obligation, final List<TransactionAddRequest> bonusTransactions) {}

  private List<String> getRentTransactionTypes() {
    return List.of(TRANSACTION_TYPE_BONUS);
  }

  private BonusCalculationResult getResult(final Long obligationId) {
    return BonusCalculationResult.builder()
        .id(null)
        .transactionId(null)
        .bonusProgramId(null)
        .calculationId(null)
        .build();
  }

  private void sendEmails(final List<BonusCalculationResult> results, final Integer weekNumber) {
    final var operators = userAccountQuery.getAllOperators();
    final var recipients = operators.stream().map(UserAccountResponse::getEmail).collect(toList());
    final var emailProperties = new HashMap<String, Object>();
    emailProperties.put("calculationType", "Weekly Obligation");
    emailProperties.put("calculationDate", LocalDate.now());
    emailProperties.put("weekNumber", weekNumber);
    /*
        emailProperties.put(
            "obligations",
            loadPort.loadAllByIds(
                results.stream().map(ObligationCalculationResult::getObligationId).collect(toList())));
    */

    final var emailSendRequest =
        EmailSendRequest.builder()
            .type(EmailType.OBLIGATION_CALCULATION)
            .recipients(recipients)
            .properties(emailProperties)
            .build();

    emailSendUseCase.sendEmail(emailSendRequest);
  }
}
