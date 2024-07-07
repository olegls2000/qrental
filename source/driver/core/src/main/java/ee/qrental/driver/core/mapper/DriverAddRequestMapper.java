package ee.qrental.driver.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.driver.api.in.request.DriverAddRequest;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.domain.Friendship;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DriverAddRequestMapper implements AddRequestMapper<DriverAddRequest, Driver> {

  @Override
  public Driver toDomain(final DriverAddRequest request) {
    return Driver.builder()
        .id(null)
        .friendship(getFriendship(request))
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
        .companyVat(request.getCompanyVat())
        .companyAddress(request.getCompanyAddress())
        .driverLicenseNumber(request.getDriverLicenseNumber())
        .driverLicenseExp(request.getDriverLicenseExp())
        .taxiLicense(request.getTaxiLicense())
        .address(request.getAddress())
        .needInvoicesByEmail(request.getNeedInvoicesByEmail())
        .needFee(request.getNeedInvoicesByEmail())
        .deposit(request.getDeposit())
        .byTelegram(request.getByTelegram())
        .byWhatsApp(request.getByWhatsApp())
        .byViber(request.getByViber())
        .byEmail(request.getByEmail())
        .bySms(request.getBySms())
        .byPhone(request.getByPhone())
        .qFirmId(request.getQFirmId())
        .requiredObligation(getRequiredObligation(request))
        .comment(request.getComment())
        .createdDate(LocalDate.now())
        .callSign(getCallSign(request))
        .build();
  }

  private BigDecimal getRequiredObligation(final DriverAddRequest request) {
    return request.getRequiredObligation() == null
        ? BigDecimal.ZERO
        : request.getRequiredObligation();
  }

  private CallSign getCallSign(final DriverAddRequest request) {
    if (request.getCallSignId() == null) {
      return null;
    }
    return CallSign.builder().id(request.getCallSignId()).build();
  }

  private Friendship getFriendship(final DriverAddRequest request) {
    if (request.getRecommendedByDriverId() == null) {
      return null;
    }
    return Friendship.builder().driverId(request.getRecommendedByDriverId()).build();
  }
}
