package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.adapter.repository.CallSignRepository;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.entity.jakarta.CallSignLinkJakartaEntity;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignHandler {

  private final CallSignLinkRepository callSignLinkRepository;
  private final CallSignRepository callSignRepository;

  public void addHandle(final Driver domain, final DriverJakartaEntity driverSaved) {
    final var callSignLinkToSave = getCallSignLinkToSave(driverSaved, domain);
    if (callSignLinkToSave == null) {
      return;
    }
    callSignLinkRepository.save(callSignLinkToSave);
  }

  private CallSignLinkJakartaEntity getCallSignLinkToSave(
      final DriverJakartaEntity driverJakartaEntity, final Driver domain) {

    final var callSign = domain.getCallSign();
    if (callSign == null) {
      return null;
    }
    final var callSignJakartaEntity = callSignRepository.getReferenceById(callSign.getId());

    return CallSignLinkJakartaEntity.builder()
        .driver(driverJakartaEntity)
        .callSign(callSignJakartaEntity)
        .dateStart(LocalDate.now())
        .dateEnd(null)
        .build();
  }

  public void updateHandle(final Driver domain, final DriverJakartaEntity driverSaved) {
    final var driverId = domain.getId();
    final var activeCallSignLink =
        callSignLinkRepository.findActiveByDriverIdAndNowDate(driverId, LocalDate.now());
    final var callSignFromDomain = domain.getCallSign().getId();
    if (callSignFromDomain == null && activeCallSignLink == null) {
      System.out.printf(
          "Call Sign for Driver with id=%d was not assigned, no changes required", driverId);
      return;
    }
    if (callSignFromDomain == null) {
      System.out.printf("Call Sign for Driver with id=%d must be unassigned", driverId);
      disableActiveCallSign(activeCallSignLink);
      return;
    }

    if (activeCallSignLink == null) {
      System.out.printf("Call Sign for Driver with id=%d must be assigned", driverId);
      final var callSignLinkToSave = getCallSignLinkToSave(driverSaved, domain);
      callSignLinkRepository.save(callSignLinkToSave);
      return;
    }

    if (!callSignFromDomain.equals(activeCallSignLink.getCallSign().getId())) {
      System.out.printf("Call Sign for Driver with id=%d must be unassigned", driverId);
      disableActiveCallSign(activeCallSignLink);
      final var callSignLinkToSave = getCallSignLinkToSave(driverSaved, domain);
      callSignLinkRepository.save(callSignLinkToSave);
    }
  }

  private void disableActiveCallSign(final CallSignLinkJakartaEntity activeCallSignLink) {
    activeCallSignLink.setDateEnd(LocalDate.now().minusDays(1L));
    callSignLinkRepository.save(activeCallSignLink);
  }
}
