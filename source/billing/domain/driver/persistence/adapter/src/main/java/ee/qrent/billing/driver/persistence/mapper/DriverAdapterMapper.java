package ee.qrent.billing.driver.persistence.mapper;

import ee.qrent.billing.driver.persistence.repository.CallSignLinkRepository;
import ee.qrent.billing.driver.persistence.repository.FriendshipRepository;
import ee.qrent.billing.driver.domain.CallSign;
import ee.qrent.billing.driver.domain.Driver;
import ee.qrent.billing.driver.domain.Friendship;
import ee.qrent.billing.driver.domain.LegalEntityType;
import ee.qrent.billing.driver.persistence.entity.jakarta.DriverJakartaEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverAdapterMapper {

  private final CallSignLinkRepository callSignLinkRepository;
  private final FriendshipRepository friendshipRepository;
  private final FriendshipAdapterMapper friendshipAdapterMapper;

  public Driver mapToDomain(final DriverJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    final var callSign = getActiveCallSign(entity.getId());
    final var friendship = getFriendship(entity.getId());

    return Driver.builder()
        .id(entity.getId())
        .active(entity.getActive())
        .callSign(callSign)
        .friendship(friendship)
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .taxNumber(entity.getTaxNumber())
        .phone(entity.getPhone())
        .email(entity.getEmail())
        .legalEntityType(LegalEntityType.valueOf(entity.getLegalEntityType()))
        .lhvAccount(entity.getLhvAccount())
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
        .taxNumber(domain.getTaxNumber())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .legalEntityType(domain.getLegalEntityType().name())
        .lhvAccount(domain.getLhvAccount())
        .companyVat(domain.getCompanyVat())
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
