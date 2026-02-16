package ee.qrent.billing.car.core.service;

import lombok.AllArgsConstructor;
import ee.qrent.billing.car.api.in.request.BrandingVerificationCalculationAddRequest;
import ee.qrent.billing.car.api.in.usecase.BrandingVerificationCalculationAddUseCase;
import ee.qrent.billing.car.api.out.BrandingVerificationCalculationAddPort;
import ee.qrent.billing.car.api.out.CarLinkLoadPort;
import ee.qrent.billing.car.api.out.CarLoadPort;
import ee.qrent.billing.car.domain.BrandingVerification;
import ee.qrent.billing.car.domain.BrandingVerificationCalculation;
import ee.qrent.billing.car.domain.BrandingVerificationCalculationResult;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.EntryType;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import jakarta.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class BrandingVerificationCalculationUseCaseService
    implements BrandingVerificationCalculationAddUseCase {

  private final CarLoadPort carLoadPort;
  private final CarLinkLoadPort carLinkLoadPort;
  private final BrandingVerificationCalculationAddPort calculationAddPort;
  private final GetDriverQuery driverQuery;
  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final QDateTime qDateTime;

  @Transactional
  @Override
  public Long add(final BrandingVerificationCalculationAddRequest request) {
    final var actionDate =
        request.getActionDate() == null ? qDateTime.getToday() : request.getActionDate();
    final var verificationDate =
        request.getDate() == null ? qDateTime.getToday() : request.getDate();
    final var type = request.getType();

    final var results = new ArrayList<BrandingVerificationCalculationResult>();
    final var activeCars = carLoadPort.loadByActive(true);
    for (final var car : activeCars) {
      if (car.getBrandingControl() == null || !car.getBrandingControl()) {
        continue;
      }
      final var activeLinks = carLinkLoadPort.loadActiveByCarId(car.getId());
      if (activeLinks == null || activeLinks.isEmpty()) {
        continue;
      }
      final var activeLink = activeLinks.get(0);
      final var verification =
          BrandingVerification.builder()
              .date(verificationDate)
              .driverId(activeLink.getDriverId())
              .carId(car.getId())
              .carLinkId(activeLink.getId())
              .brandingExpirationDate(car.getBrandingExpirationDate())
              .build();
      final var result =
          BrandingVerificationCalculationResult.builder().verification(verification).build();
      results.add(result);
    }

    final var calculation =
        BrandingVerificationCalculation.builder()
            .actionDate(actionDate)
            .comment(request.getComment())
            .type(type)
            .results(results)
            .build();

    final var savedCalculation = calculationAddPort.add(calculation);
    sendNotifications(results);

    return savedCalculation.getId();
  }

  private void sendNotifications(final List<BrandingVerificationCalculationResult> results) {
    final var today = qDateTime.getToday();
    for (final var result : results) {
      final var verification = result.getVerification();
      if (verification == null || verification.getBrandingExpirationDate() == null) {
        continue;
      }
      final var daysLeft = ChronoUnit.DAYS.between(today, verification.getBrandingExpirationDate());
      final var shouldNotify = daysLeft == 10 || (daysLeft >= 0 && daysLeft < 7);
      if (!shouldNotify) {
        continue;
      }
      final var driver = driverQuery.getById(verification.getDriverId());
      if (driver == null || driver.getEmail() == null || driver.getEmail().isBlank()) {
        continue;
      }
      final var car = carLoadPort.loadById(verification.getCarId());
      final var properties = new HashMap<String, Object>();
      properties.put("driverFirstName", driver.getFirstName());
      properties.put("driverLastName", driver.getLastName());
      properties.put("carRegNumber", car == null ? "" : car.getRegNumber());
      properties.put("brandingExpirationDate", verification.getBrandingExpirationDate());
      final var recipients = new ArrayList<String>();
      recipients.add(driver.getEmail());
      recipients.add("eleonora@qtakso.ee");
      final var notificationQueuePushRequest =
          QueueEntryPushRequest.builder()
              .occurredAt(qDateTime.getNow())
              .type(EntryType.BRANDING_VERIFICATION_EMAIL)
              .payloadRecipients(recipients)
              .payloadProperties(properties)
              .build();
      notificationQueuePushUseCase.push(notificationQueuePushRequest);
    }
  }
}
