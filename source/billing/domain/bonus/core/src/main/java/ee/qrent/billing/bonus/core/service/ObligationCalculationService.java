package ee.qrent.billing.bonus.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NAME_WEEKLY_RENT;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_NO_LABEL_FINE;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrent.billing.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrent.billing.bonus.api.out.ObligationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationCalculationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrent.billing.bonus.domain.Obligation;
import ee.qrent.billing.bonus.domain.ObligationCalculationResult;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.response.CarLinkResponse;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.billing.user.api.in.response.UserAccountResponse;
import ee.qrent.queue.api.in.EntryType;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
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
  private final QueueEntryPushUseCase queueEntryPushUseCase;
  private final ObligationCalculationAddPort calculationAddPort;
  private final ObligationAddPort obligationAddPort;
  private final ObligationLoadPort loadPort;
  private final ObligationCalculationAddRequestMapper addRequestMapper;
  private final AddRequestValidator<ObligationCalculationAddRequest> addRequestValidator;
  private final ObligationCalculator obligationCalculator;
  private final QDateTime qDateTime;

  @Transactional
  @Override
  public Long add(final ObligationCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addRequestValidator.validate(addRequest);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var domain = addRequestMapper.toDomain(addRequest);
    final var qWeek = qWeekQuery.getById(addRequest.getQWeekId());
    final var qWeekId = qWeek.getId();
    final var previousWeek = qWeekQuery.getOneBeforeById(qWeekId);
    final var previousWeekId = previousWeek.getId();

    carLinkQuery.getAllActiveByQWeekId(qWeekId).stream()
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

    final var queueEntryPushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .payloadRecipients(recipients)
            .type(EntryType.OBLIGATION_CALCULATION_EMAIL)
            .payloadProperties(emailProperties)
            .build();

    queueEntryPushUseCase.push(queueEntryPushRequest);
  }
}
