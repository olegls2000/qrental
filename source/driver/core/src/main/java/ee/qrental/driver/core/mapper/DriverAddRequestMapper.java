package ee.qrental.driver.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.driver.api.in.request.DriverAddRequest;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;

import java.time.LocalDate;

public class DriverAddRequestMapper implements AddRequestMapper<DriverAddRequest, Driver> {

  @Override
  public Driver toDomain(final DriverAddRequest request) {
    return Driver.builder()
        .id(null)
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
        .comment(request.getComment())
        .createdDate(LocalDate.now())
        .callSign(CallSign.builder().id(request.getCallSignId()).build())
        .build();
  }
}
