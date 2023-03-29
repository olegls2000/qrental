package ee.qrental.driver.core.mapper;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.domain.Driver;

import static java.lang.String.format;

public class DriverResponseMapper
        implements ResponseMapper<DriverResponse, Driver> {
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
                .driverLicenseNumber(domain.getDriverLicenseNumber())
                .driverLicenseExp(domain.getDriverLicenseExp())
                .taxiLicense(domain.getTaxiLicense())
                .address(domain.getAddress())
                .comment(domain.getComment())
                .deposit(domain.getDeposit())
                .build();
    }

    @Override
    public String toObjectInfo(Driver domain) {
        return format("%s %s",
                domain.getLastName(),
                domain.getFirstName());
    }
}
