package ee.qrent.billing.bonus.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrent.billing.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrent.billing.bonus.api.out.ObligationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationCalculationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrent.billing.bonus.domain.ObligationCalculationResult;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.response.CarLinkResponse;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.billing.user.api.in.response.UserAccountResponse;
import ee.qrent.queue.api.in.EntryType;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationService implements ObligationCalculationAddUseCase {

  private final GetQWeekQuery qWeekQuery;
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
    final var qWeekId = addRequest.getQWeekId();
    final var qWeek = qWeekQuery.getById(qWeekId);

    carLinkQuery.getAllActiveByQWeekId(qWeekId).stream()
        .map(CarLinkResponse::getDriverId)
        .forEach(
            driverId -> {
              final var obligation = obligationCalculator.getObligationOnThursday(driverId, qWeek);
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
    emailProperties.put("calculationDate", qDateTime.getToday());
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
