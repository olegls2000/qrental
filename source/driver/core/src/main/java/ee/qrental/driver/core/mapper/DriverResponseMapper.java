package ee.qrental.driver.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverResponseMapper implements ResponseMapper<DriverResponse, Driver> {

  private static final int STRING_VALUE_MAX_LENGTH = 14;
  private static final int EMAIL_VALUE_MAX_LENGTH = 20;
  private final GetFirmQuery firmQuery;

  private static String contract(final String value, int maxLength) {
    if (value.length() > maxLength) {
      return value.substring(0, maxLength - 2) + "..";
    }
    return value;
  }

  @Override
  public DriverResponse toResponse(final Driver domain) {
    final var qFirmId = domain.getQFirmId();
    final var qFirmName = firmQuery.getById(qFirmId).getFirmName();
    final var callSign = getCallSign(domain.getCallSign());

    return DriverResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .callSign(callSign)
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .isikukood(domain.getIsikukood())
        .phone(contract(domain.getPhone(), STRING_VALUE_MAX_LENGTH))
        .email(contract(domain.getEmail(), EMAIL_VALUE_MAX_LENGTH))
        .address(contract(domain.getAddress(), STRING_VALUE_MAX_LENGTH))
        .companyName(contract(domain.getCompanyName(), STRING_VALUE_MAX_LENGTH))
        .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(contract(domain.getCompanyAddress(), STRING_VALUE_MAX_LENGTH))
        .companyVat(domain.getCompanyVat())
        .driverLicenseNumber(domain.getDriverLicenseNumber())
        .driverLicenseExp(domain.getDriverLicenseExp())
        .taxiLicense(domain.getTaxiLicense())
        .needInvoicesByEmail(domain.getNeedInvoicesByEmail())
        .needFee(domain.getNeedFee())
        .deposit(domain.getDeposit())
        .qFirmId(qFirmId)
        .qFirmName(contract(qFirmName, STRING_VALUE_MAX_LENGTH))
        .comment(contract(domain.getComment(), STRING_VALUE_MAX_LENGTH))
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
