package ee.qrental.driver.adapter.mapper;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverAdapterMapper {

  private final CallSignLinkRepository callSignLinkRepository;

  public Driver mapToDomain(final DriverJakartaEntity entity) {
    final var callSign = getActiveCallSign(entity.getId());

    return Driver.builder()
        .id(entity.getId())
        .active(entity.getActive())
        .callSign(callSign)
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .isikukood(entity.getIsikukood())
        .phone(entity.getPhone())
        .email(entity.getEmail())
        .companyName(entity.getCompanyName())
        .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
        .companyAddress(entity.getCompanyAddress())
        .driverLicenseNumber(entity.getDriverLicenseNumber())
        .driverLicenseExp(entity.getDriverLicenseExp())
        .taxiLicense(entity.getTaxiLicense())
        .address(entity.getAddress())
        .needInvoicesByEmail(entity.getNeedInvoicesByEmail())
        .needFee(entity.getNeedFee())
        .deposit(entity.getDeposit())
        .comment(entity.getComment())
        .qFirmId(entity.getQFirmId())
        .build();
  }

  public DriverJakartaEntity mapToEntity(final Driver domain) {

    return DriverJakartaEntity.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .isikukood(domain.getIsikukood())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .companyName(domain.getCompanyName())
        .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(domain.getCompanyAddress())
        .driverLicenseNumber(domain.getDriverLicenseNumber())
        .driverLicenseExp(domain.getDriverLicenseExp())
        .taxiLicense(domain.getTaxiLicense())
        .address(domain.getAddress())
        .needInvoicesByEmail(domain.getNeedInvoicesByEmail())
        .needFee(domain.getNeedFee())
        .deposit(domain.getDeposit())
        .qFirmId(domain.getQFirmId())
        .comment(domain.getComment())
        .build();
  }

  private CallSign getActiveCallSign(final Long driverId) {
    final var callSignLinkEntityActive =
        callSignLinkRepository.findActiveByDriverIdAndNowDate(driverId, LocalDate.now());
    if (callSignLinkEntityActive == null) {

      return null;
    }
    final var callSignEntity = callSignLinkEntityActive.getCallSign();

    return CallSign.builder()
        .id(callSignEntity.getId())
        .callSign(callSignEntity.getCallSign())
        .build();
  }
}
