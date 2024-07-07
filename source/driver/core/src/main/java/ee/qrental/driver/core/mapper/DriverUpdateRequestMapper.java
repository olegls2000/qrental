package ee.qrental.driver.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverUpdateRequestMapper implements UpdateRequestMapper<DriverUpdateRequest, Driver> {

  private final FriendshipDomainMapper friendshipDomainMapper;

  @Override
  public Driver toDomain(final DriverUpdateRequest request) {
    return Driver.builder()
        .id(request.getId())
        .active(request.getActive())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .taxNumber(request.getTaxNumber())
        .phone(request.getPhone())
        .email(request.getEmail())
        .companyName(request.getCompanyName())
        .companyCeoFirstName(request.getCompanyCeoFirstName())
        .companyCeoLastName(request.getCompanyCeoLastName())
        .companyCeoTaxNumber(request.getCompanyCeoTaxNumber())
        .companyRegistrationNumber(request.getRegNumber())
        .companyAddress(request.getCompanyAddress())
        .companyVat(request.getCompanyVat())
        .driverLicenseNumber(request.getDriverLicenseNumber())
        .driverLicenseExp(request.getDriverLicenseExp())
        .taxiLicense(request.getTaxiLicense())
        .address(request.getAddress())
        .needInvoicesByEmail(request.getNeedInvoicesByEmail())
        .needFee(request.getNeedFee())
        .byTelegram(request.getByTelegram())
        .byWhatsApp(request.getByWhatsApp())
        .byViber(request.getByViber())
        .byEmail(request.getByEmail())
        .bySms(request.getBySms())
        .byPhone(request.getByPhone())
        .deposit(request.getDeposit())
        .requiredObligation(getRequiredObligation(request))
        .qFirmId(request.getQFirmId())
        .comment(request.getComment())
        .callSign(CallSign.builder().id(request.getCallSignId()).build())
        .friendship(friendshipDomainMapper.toDomain(request))
        .build();
  }

  private BigDecimal getRequiredObligation(final DriverUpdateRequest driverUpdateRequest) {
    if (driverUpdateRequest.getHasRequiredObligation()) {
      return driverUpdateRequest.getRequiredObligation();
    }

    return BigDecimal.ZERO;
  }

  @Override
  public DriverUpdateRequest toRequest(final Driver domain) {
    final var friendship = domain.getFriendship();

    return DriverUpdateRequest.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .recommendedByDriverId(friendship == null ? null : friendship.getDriverId())
        .callSignId(domain.getCallSignId())
        .callSign(domain.getCallSignValue())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .taxNumber(domain.getTaxNumber())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .companyName(domain.getCompanyName())
        .companyCeoFirstName(domain.getCompanyCeoFirstName())
        .companyCeoLastName(domain.getCompanyCeoLastName())
        .companyCeoTaxNumber(domain.getCompanyCeoTaxNumber())
        .regNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(domain.getCompanyAddress())
        .companyVat(domain.getCompanyVat())
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
        .qFirmId(domain.getQFirmId())
        .hasRequiredObligation(domain.hasRequiredObligation())
        .requiredObligation(domain.getRequiredObligation())
        .comment(domain.getComment())
        .build();
  }
}
