package ee.qrental.driver.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverResponseMapper implements ResponseMapper<DriverResponse, Driver> {
  private final GetFirmQuery firmQuery;

  @Override
  public DriverResponse toResponse(final Driver domain) {
    final var qFirmId = domain.getQFirmId();
    final var qFirmName = firmQuery.getById(qFirmId).getName();
    final var callSign = getCallSign(domain.getCallSign());

    return DriverResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .callSign(callSign)
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .isikukood(domain.getTaxNumber())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .address(domain.getAddress())
        .companyName(domain.getCompanyName())
        .companyCeoName(
            format("%s %s", domain.getCompanyCeoFirstName(), domain.getCompanyCeoLastName()))
        .companyCeoTaxNumber(domain.getCompanyCeoTaxNumber())
        .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(domain.getCompanyAddress())
        .companyVat(domain.getCompanyVat())
        .driverLicenseNumber(domain.getDriverLicenseNumber())
        .driverLicenseExp(domain.getDriverLicenseExp())
        .taxiLicense(domain.getTaxiLicense())
        .needInvoicesByEmail(domain.getNeedInvoicesByEmail())
        .needFee(domain.getNeedFee())
        .byTelegram(domain.getByTelegram())
        .byWhatsApp(domain.getByWhatsApp())
        .byViber(domain.getByViber())
        .byEmail(domain.getByEmail())
        .bySms(domain.getBySms())
        .byPhone(domain.getByPhone())
        .deposit(domain.getDeposit())
        .hasRequiredObligation(domain.hasRequiredObligation())
        .requiredObligation(domain.getRequiredObligation())
        .qFirmId(qFirmId)
        .qFirmName(qFirmName)
        .comment(domain.getComment())
        .createdDate(domain.getCreatedDate())
        .build();
  }

  private Integer getCallSign(final CallSign callSign) {
    if (callSign == null) {
      return null;
    }
    return callSign.getCallSign();
  }

  @Override
  public String toObjectInfo(Driver domain) {
    return format("%s %s ", domain.getFirstName(), domain.getLastName());
  }
}
