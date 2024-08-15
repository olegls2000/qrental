package ee.qrental.bonus.core.service;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrental.bonus.api.out.ObligationAddPort;
import ee.qrental.bonus.api.out.ObligationCalculationAddPort;
import ee.qrental.bonus.api.out.ObligationLoadPort;
import ee.qrental.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrental.bonus.core.validator.ObligationCalculationAddBusinessRuleValidator;
import ee.qrental.bonus.domain.Obligation;
import ee.qrental.bonus.domain.ObligationCalculationResult;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import ee.qrental.user.api.in.response.UserAccountResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationService implements ObligationCalculationAddUseCase {

  private final GetQWeekQuery qWeekQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetCarLinkQuery carLinkQuery;
  private final GetUserAccountQuery userAccountQuery;
  private final EmailSendUseCase emailSendUseCase;
  private final ObligationCalculationAddPort calculationAddPort;
  private final ObligationAddPort obligationAddPort;
  private final ObligationLoadPort loadPort;
  private final ObligationCalculationAddRequestMapper addRequestMapper;
  private final ObligationCalculationAddBusinessRuleValidator addBusinessRuleValidator;
  private final ObligationCalculator obligationCalculator;

  @Transactional
  @Override
  public Long add(final ObligationCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addBusinessRuleValidator.validate(addRequest);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var domain = addRequestMapper.toDomain(addRequest);
    final var qWeek = qWeekQuery.getById(addRequest.getQWeekId());
    final var qWeekId = qWeek.getId();
    final var previousWeek = qWeekQuery.getOneBeforeById(qWeekId);
    final var previousWeekId = previousWeek.getId();

    carLinkQuery.getActive().stream()
        .map(CarLinkResponse::getDriverId)
        .forEach(
            driverId -> {
              final var weekObligation = obligationCalculator.calculate(driverId, qWeekId);
              final var positiveAmount = getPositiveAmount(driverId, qWeekId);
              final var matchCount =
                  getMatchCount(driverId, qWeekId, previousWeekId, weekObligation, positiveAmount);
              final var obligation =
                  Obligation.builder()
                      .id(null)
                      .qWeekId(qWeekId)
                      .driverId(driverId)
                      .obligationAmount(weekObligation)
                      .positiveAmount(positiveAmount)
                      .matchCount(matchCount)
                      .build();
              final var savedObligation = obligationAddPort.add(obligation);
              final var result = getResult(savedObligation.getId());
              domain.getResults().add(result);
            });
    final var savedCalculation = calculationAddPort.add(domain);
    sendEmails(domain.getResults(), qWeek.getNumber());
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Obligation Calculation took %d milli seconds \n", calculationDuration);
    return savedCalculation.getId();
  }

  private Integer getMatchCount(
      final Long driverId,
      final Long qWeekId,
      final Long previousQWeekId,
      final BigDecimal obligationAmount,
      final BigDecimal positiveAmount) {
    final var rentTransactionCount =
        transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            .filter(
                transaction ->
                    List.of(TRANSACTION_TYPE_NAME_WEEKLY_RENT, TRANSACTION_TYPE_NO_LABEL_FINE)
                        .contains(transaction.getType()))
            .count();
    if (rentTransactionCount == 0) {
      System.out.println("No Rent transactions. Match count is O");

      return 0;
    }
    if (positiveAmount.compareTo(obligationAmount) >= 0) {
      final var previousWeekObligation =
          loadPort.loadByDriverIdAndByQWeekId(driverId, previousQWeekId);

      if (previousWeekObligation == null) {

        return 0;
      }

      var previousWeekObligationMatchCount = previousWeekObligation.getMatchCount();

      return ++previousWeekObligationMatchCount;
    }

    return 0;
  }

  private BigDecimal getPositiveAmount(final Long driverId, final Long qWeekId) {
    final var positiveAmount =
        transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            .filter(tr -> "P".equals(tr.getKind()))
            .map(TransactionResponse::getRealAmount)
            .reduce(ZERO, BigDecimal::add);

    return positiveAmount;
  }

  private ObligationCalculationResult getResult(final Long obligationId) {
    return ObligationCalculationResult.builder()
        .id(null)
        .obligationId(obligationId)
        .calculationId(null)
        .build();
  }

  private void sendEmails(
      final List<ObligationCalculationResult> results, final Integer weekNumber) {
    final var operators = userAccountQuery.getAllOperators();
    final var recipients = operators.stream().map(UserAccountResponse::getEmail).collect(toList());
    final var emailProperties = new HashMap<String, Object>();
    emailProperties.put("calculationType", "Weekly Obligation");
    emailProperties.put("calculationDate", LocalDate.now());
    emailProperties.put("weekNumber", weekNumber);
    emailProperties.put(
        "obligations",
        loadPort.loadAllByIds(
            results.stream().map(ObligationCalculationResult::getObligationId).collect(toList())));

    final var emailSendRequest =
        EmailSendRequest.builder()
            .type(EmailType.OBLIGATION_CALCULATION)
            .recipients(recipients)
            .properties(emailProperties)
            .build();

    emailSendUseCase.sendEmail(emailSendRequest);
  }
}
