package ee.qrent.billing.transaction.core.service.rent;

import static ee.qrent.queue.api.in.EntryType.RENT_CALCULATION_EMAIL;

import static java.util.stream.Collectors.toList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.response.CarLinkResponse;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetAbsenceQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import ee.qrent.billing.transaction.api.out.rent.RentCalculationAddPort;
import ee.qrent.billing.transaction.core.mapper.rent.RentCalculationAddRequestMapper;
import ee.qrent.billing.transaction.core.service.TransactionUseCaseService;
import ee.qrent.billing.transaction.domain.rent.RentCalculation;
import ee.qrent.billing.transaction.domain.rent.RentCalculationResult;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.billing.user.api.in.response.UserAccountResponse;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;

// TODO add Unit Test
@AllArgsConstructor
public class RentCalculationService implements RentCalculationAddUseCase {

  private final RentTransactionGenerator rentTransactionGenerator;
  private final GetCarLinkQuery carLinkQuery;
  private final GetTransactionQuery transactionQuery;
  private final TransactionUseCaseService transactionUseCaseService;
  private final RentCalculationAddPort rentCalculationAddPort;
  private final RentCalculationAddRequestMapper addRequestMapper;
  private final AddRequestValidator<RentCalculationAddRequest> addRequestValidator;
  private final GetUserAccountQuery userAccountQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetAbsenceQuery absenceQuery;
  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final QDateTime qDateTime;

  @Transactional
  @Override
  public Long add(final RentCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addRequestValidator.validate(addRequest);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var requestedQWeekId = addRequest.getQWeekId();
    final var domain = addRequestMapper.toDomain(addRequest);
    final var qWeek = qWeekQuery.getById(requestedQWeekId);
    final var activeCarLinks = carLinkQuery.getAllActiveByQWeekId(requestedQWeekId);
    for (final var activeCarLink : activeCarLinks) {
      processRent(domain, activeCarLink, qWeek);
      processNoLabelFine(domain, activeCarLink, qWeek);
      processAbsenceAdjustment(domain, activeCarLink, qWeek);
    }
    final var savedCalculation = rentCalculationAddPort.add(domain);
    sendEmails(domain.getResults(), qWeek.getNumber());
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf("----> Time: Rent Calculation took %d milli seconds \n", calculationDuration);

    return savedCalculation.getId();
  }

  private void processRent(
      final RentCalculation domain,
      final CarLinkResponse activeCarLink,
      final QWeekResponse qWeek) {
    final var rentTransactionAddRequest =
        rentTransactionGenerator.getRentTransactionAddRequest(qWeek, activeCarLink);
    saveTransaction(rentTransactionAddRequest, activeCarLink.getId(), domain);
  }

  private void processAbsenceAdjustment(
      final RentCalculation domain,
      final CarLinkResponse activeCarLink,
      final QWeekResponse qWeek) {
    final var driverId = activeCarLink.getDriverId();
    final var carLinkId = activeCarLink.getId();
    final var absencesDaysCount =
        absenceQuery.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeek.getId());
    final var absenceAdjustmentTransactionAddRequestOpt =
        rentTransactionGenerator.getAbsenceAdjustmentTransactionAddRequest(
            activeCarLink, qWeek, absencesDaysCount);
    absenceAdjustmentTransactionAddRequestOpt.ifPresent(
        request -> saveTransaction(request, carLinkId, domain));
  }

  private void processNoLabelFine(
      final RentCalculation domain,
      final CarLinkResponse activeCarLink,
      final QWeekResponse qWeek) {
    final var noLabelFineTransactionAddRequestOpt =
        rentTransactionGenerator.getNoLabelFineTransactionAddRequest(qWeek, activeCarLink);
    noLabelFineTransactionAddRequestOpt.ifPresent(
        request -> saveTransaction(request, activeCarLink.getId(), domain));
  }

  private void saveTransaction(
      final TransactionAddRequest transactionAddRequest,
      final Long carLinkId,
      final RentCalculation domain) {
    final var absenceAdjustmentTransactionId = transactionUseCaseService.add(transactionAddRequest);
    final var calculationResultForAbsenceAdjustment =
        getResult(carLinkId, absenceAdjustmentTransactionId);
    domain.getResults().add(calculationResultForAbsenceAdjustment);
  }

  private RentCalculationResult getResult(final Long carLinkId, final Long transactionId) {

    return RentCalculationResult.builder()
        .carLinkId(carLinkId)
        .transactionId(transactionId)
        .build();
  }

  private void sendEmails(final List<RentCalculationResult> results, final Integer weekNumber) {
    final var operators = userAccountQuery.getAllOperators();
    final var recipients = operators.stream().map(UserAccountResponse::getEmail).collect(toList());
    final var emailProperties = new HashMap<String, Object>();
    emailProperties.put("calculationType", "Weekly Rent");
    emailProperties.put("calculationDate", LocalDate.now());
    emailProperties.put("weekNumber", weekNumber);
    emailProperties.put(
        "transactions",
        transactionQuery.getAllByIds(
            results.stream().map(RentCalculationResult::getTransactionId).collect(toList())));
    final var notificationQueuePushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .type(RENT_CALCULATION_EMAIL)
            .payloadRecipients(recipients)
            .payloadProperties(emailProperties)
            .build();

    notificationQueuePushUseCase.push(notificationQueuePushRequest);
  }
}
