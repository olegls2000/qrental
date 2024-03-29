package ee.qrental.driver.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.Driver;

public class
DriverUpdateRequestMapper implements UpdateRequestMapper<DriverUpdateRequest, Driver> {

  @Override
  public Driver toDomain(final DriverUpdateRequest request) {
    return Driver.builder()
        .id(request.getId())
        .active(request.getActive())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .isikukood(request.getIsikukood())
        .phone(request.getPhone())
        .email(request.getEmail())
        .companyName(request.getCompany())
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
        .qFirmId(request.getQFirmId())
        .comment(request.getComment())
        .callSign(CallSign.builder().id(request.getCallSignId()).build())
        .build();
  }

  @Override
  public DriverUpdateRequest toRequest(final Driver domain) {
    return DriverUpdateRequest.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .callSignId(domain.getCallSignId())
        .callSign(domain.getCallSignValue())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .isikukood(domain.getIsikukood())
        .phone(domain.getPhone())
        .email(domain.getEmail())
        .company(domain.getCompanyName())
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
        .comment(domain.getComment())
        .build();
  }
}
