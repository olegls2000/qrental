package ee.qrental.driver.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.driver.api.in.request.DriverAddRequest;
import ee.qrental.driver.domain.Driver;

public class DriverAddRequestMapper
        implements AddRequestMapper<DriverAddRequest, Driver> {

    @Override
    public Driver toDomain(DriverAddRequest request) {
    return Driver.builder()
        .id(null)
        .active(request.getActive())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .isikukood(request.getIsikukood())
        .phone(request.getPhone())
        .email(request.getEmail())
        .companyName(request.getCompany())
        .companyRegistrationNumber(request.getRegNumber())
        .companyAddress(request.getCompanyAddress())
        .driverLicenseNumber(request.getDriverLicenseNumber())
        .taxiLicense(request.getTaxiLicense())
        .address(request.getAddress())
        .needInvoicesByEmail(request.getNeedInvoicesByEmail())
         .deposit(request.getDeposit())
        .comment(request.getComment())
        .build();
    }
}
