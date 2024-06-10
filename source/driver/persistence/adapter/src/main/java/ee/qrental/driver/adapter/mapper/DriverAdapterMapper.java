package ee.qrental.driver.adapter.mapper;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.driver.adapter.repository.FriendshipRepository;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.domain.Friendship;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverAdapterMapper {

  private final CallSignLinkRepository callSignLinkRepository;
  private final FriendshipRepository friendshipRepository;
  private final FriendshipAdapterMapper friendshipAdapterMapper;

  public Driver mapToDomain(final DriverJakartaEntity entity) {
    final var callSign = getActiveCallSign(entity.getId());
    final var friendship = getFriendship(entity.getId());

    return Driver.builder()
        .id(entity.getId())
        .active(entity.getActive())
        .callSign(callSign)
        .friendship(friendship)
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .taxNumber(entity.getIsikukood())
        .phone(entity.getPhone())
        .email(entity.getEmail())
        .companyName(entity.getCompanyName())
        .companyCeoFirstName(entity.getCompanyCeoFirstName())
        .companyCeoLastName(entity.getCompanyCeoLastName())
        .companyCeoTaxNumber(entity.getCompanyCeoTaxNumber())
        .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
        .companyVat(entity.getCompanyVat())
        .companyAddress(entity.getCompanyAddress())
        .driverLicenseNumber(entity.getDriverLicenseNumber())
        .driverLicenseExp(entity.getDriverLicenseExp())
        .taxiLicense(entity.getTaxiLicense())
        .address(entity.getAddress())
        .needInvoicesByEmail(entity.getNeedInvoicesByEmail())
        .needFee(entity.getNeedFee())
        .byTelegram(entity.getByTelegram())
        .byWhatsApp(entity.getByWhatsApp())
        .byViber(entity.getByViber())
        .byEmail(entity.getByEmail())
        .bySms(entity.getBySms())
        .byPhone(entity.getByPhone())
        .deposit(entity.getDeposit())
        .requiredObligation(entity.getRequiredObligation())
        .comment(entity.getComment())
        .createdDate(entity.getCreatedDate())
        .qFirmId(entity.getQFirmId())
        .build();
  }

  public DriverJakartaEntity mapToEntity(final Driver domain) {

    return DriverJakartaEntity.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .isikukood(domain.getTaxNumber())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .companyName(domain.getCompanyName())
        .companyCeoFirstName(domain.getCompanyCeoFirstName())
        .companyCeoLastName(domain.getCompanyCeoLastName())
        .companyCeoTaxNumber(domain.getCompanyCeoTaxNumber())
        .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(domain.getCompanyAddress())
        .driverLicenseNumber(domain.getDriverLicenseNumber())
        .driverLicenseExp(domain.getDriverLicenseExp())
        .taxiLicense(domain.getTaxiLicense())
        .address(domain.getAddress())
        .needInvoicesByEmail(domain.getNeedInvoicesByEmail())
        .needFee(domain.getNeedFee())
        .byTelegram(domain.getByTelegram())
        .byWhatsApp(domain.getByWhatsApp())
        .byViber(domain.getByViber())
        .byEmail(domain.getByEmail())
        .bySms(domain.getBySms())
        .byPhone(domain.getByPhone())
        .deposit(domain.getDeposit())
        .requiredObligation(domain.getRequiredObligation())
        .qFirmId(domain.getQFirmId())
        .comment(domain.getComment())
        .createdDate(domain.getCreatedDate())
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

  private Friendship getFriendship(final Long driverId) {
    return friendshipAdapterMapper.mapToDomain(friendshipRepository.findByFriendId(driverId));
  }
}
