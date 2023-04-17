package ee.qrental.driver.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.domain.Driver;

public class DriverResponseMapper implements ResponseMapper<DriverResponse, Driver> {
  @Override
  public DriverResponse toResponse(final Driver domain) {
    return DriverResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .isikukood(domain.getIsikukood())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .address(domain.getAddress())
        .companyName(domain.getCompanyName())
        .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(domain.getCompanyAddress())
        .companyVat(domain.getCompanyVat())
        .driverLicenseNumber(domain.getDriverLicenseNumber())
        .driverLicenseExp(domain.getDriverLicenseExp())
        .taxiLicense(domain.getTaxiLicense())
        .comment(domain.getComment())
        .deposit(domain.getDeposit())
        .qFirmId(domain.getQFirmId())
        .build();
  }

  @Override
  public String toObjectInfo(Driver domain) {
    return format("%s %s", domain.getLastName(), domain.getFirstName());
  }
}
