package ee.qrental.driver.adapter.mapper;

import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;

public class DriverAdapterMapper {

    public Driver mapToDomain(final DriverJakartaEntity jpaEntity) {
        return Driver.builder()
                .id(null)
                .active(jpaEntity.getActive())
                .firstName(jpaEntity.getFirstName())
                .lastName(jpaEntity.getLastName())
                .isikukood(jpaEntity.getIsikukood())
                .phone(jpaEntity.getPhone())
                .email(jpaEntity.getEmail())
                .company(jpaEntity.getCompany())
                .regNumber(jpaEntity.getRegNumber())
                .companyAddress(jpaEntity.getCompanyAddress())
                .driverLicenseNumber(jpaEntity.getDriverLicenseNumber())
                .taxiLicense(jpaEntity.getTaxiLicense())
                .address(jpaEntity.getAddress())
                .deposit(jpaEntity.getDeposit())
                .comment(jpaEntity.getComment())
                .build();
    }

    public DriverJakartaEntity mapToEntity(final Driver domain) {
        return DriverJakartaEntity.builder()
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
                .driverLicenseExp(domain.getDriverLicenseExp())
                .taxiLicense(domain.getTaxiLicense())
                .address(domain.getAddress())
                .comment(domain.getComment())
                .deposit(domain.getDeposit())
                .build();
    }
}