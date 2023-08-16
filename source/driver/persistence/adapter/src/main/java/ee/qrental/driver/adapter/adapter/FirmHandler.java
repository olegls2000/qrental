package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import ee.qrental.driver.entity.jakarta.FirmLinkJakartaEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmHandler {

  private final FirmLinkRepository firmLinkRepository;

  public void addHandle(final Driver domain, final DriverJakartaEntity driverSaved) {
    final var firLinkToSave = getFirmLinkToSave(driverSaved, domain);
    firmLinkRepository.save(firLinkToSave);
  }

  private FirmLinkJakartaEntity getFirmLinkToSave(
      final DriverJakartaEntity driverJakartaEntity, final Driver domain) {
    final var firmId = domain.getQFirmId();
    return FirmLinkJakartaEntity.builder()
        .driver(driverJakartaEntity)
        .firmId(firmId)
        .dateStart(LocalDate.now())
        .dateEnd(null)
        .build();
  }

  public void updateHandle(final Driver domain, final DriverJakartaEntity driverSaved) {
    final var driverId = domain.getId();
    final var activeFirmLink =
            firmLinkRepository.findOneByDriverIdAndRequiredDate(driverId, LocalDate.now());
    final var firmIdFromDomain = domain.getQFirmId();
    if (firmIdFromDomain == null && activeFirmLink == null) {
      System.out.printf("Firm for Driver with id=%d was not assigned, no changes required", driverId);
      return;
    }
    if (firmIdFromDomain == null) {
      System.out.printf("Firm for Driver with id=%d must be unassigned", driverId);
      disableActiveFirmLink(activeFirmLink);
      return;
    }

    if (activeFirmLink == null) {
      System.out.printf("Firm for Driver with id=%d must be assigned", driverId);
      final var firmLinkToSave = getFirmLinkToSave(driverSaved, domain);
      firmLinkRepository.save(firmLinkToSave);
      return;
    }

    if (!firmIdFromDomain.equals(activeFirmLink.getFirmId()) ) {
      System.out.printf("Firm for Driver with id=%d must be unassigned", driverId);
      disableActiveFirmLink(activeFirmLink);
      final var callSignLinkToSave = getFirmLinkToSave(driverSaved, domain);
      firmLinkRepository.save(callSignLinkToSave);
    }
  }

  private void disableActiveFirmLink(final FirmLinkJakartaEntity activeFirmLink) {
    activeFirmLink.setDateEnd(LocalDate.now().minusDays(1L));
    firmLinkRepository.save(activeFirmLink);
  }
}
