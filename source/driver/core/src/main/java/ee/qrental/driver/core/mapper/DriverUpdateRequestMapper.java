package ee.qrental.driver.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;

import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.domain.Driver;

public class DriverUpdateRequestMapper
        implements UpdateRequestMapper<DriverUpdateRequest, Driver> {

    @Override
    public Driver toDomain(final DriverUpdateRequest request) {
        return Driver.builder()
                .id(null)
                .active(request.getActive())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isikukood(request.getIsikukood())
                .phone(request.getPhone())
                .email(request.getEmail())
                .company(request.getCompany())
                .regNumber(request.getRegNumber())
                .companyAddress(request.getCompanyAddress())
                .driverLicenseNumber(request.getDriverLicenseNumber())
                .taxiLicense(request.getTaxiLicense())
                .address(request.getAddress())
                .deposit(request.getDeposit())
                .comment(request.getComment())
                .build();
    }

    @Override
    public DriverUpdateRequest toRequest(final Driver domain) {
       return DriverUpdateRequest.builder()
               .id(domain.getId())
               .active(domain.getActive())
               .firstName(domain.getFirstName())
               .lastName(domain.getLastName())
               .isikukood(domain.getIsikukood())
               .phone(domain.getPhone())
               .email(domain.getEmail())
               .company(domain.getCompany())
               .regNumber(domain.getRegNumber())
               .companyAddress(domain.getCompanyAddress())
               .driverLicenseNumber(domain.getDriverLicenseNumber())
               .taxiLicense(domain.getTaxiLicense())
               .address(domain.getAddress())
               .deposit(domain.getDeposit())
               .comment(domain.getComment())
               .build();
    }
}
