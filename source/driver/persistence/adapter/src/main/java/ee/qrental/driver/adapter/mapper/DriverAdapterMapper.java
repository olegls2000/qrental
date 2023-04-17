package ee.qrental.driver.adapter.mapper;

import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;

public class DriverAdapterMapper {

  public Driver mapToDomain(final DriverJakartaEntity entity) {
    return Driver.builder()
        .id(entity.getId())
        .active(entity.getActive())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .isikukood(entity.getIsikukood())
        .phone(entity.getPhone())
        .email(entity.getEmail())
        .companyName(entity.getCompanyName())
        .companyRegistrationNumber(entity.getCompanyRegistrationNumber())
        .companyAddress(entity.getCompanyAddress())
        .driverLicenseNumber(entity.getDriverLicenseNumber())
        .taxiLicense(entity.getTaxiLicense())
        .address(entity.getAddress())
        .deposit(entity.getDeposit())
        .comment(entity.getComment())
        .qFirmId(entity.getQFirmId())
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
        .companyName(domain.getCompanyName())
        .companyRegistrationNumber(domain.getCompanyRegistrationNumber())
        .companyAddress(domain.getCompanyAddress())
        .driverLicenseNumber(domain.getDriverLicenseNumber())
        .driverLicenseExp(domain.getDriverLicenseExp())
        .taxiLicense(domain.getTaxiLicense())
        .address(domain.getAddress())
        .deposit(domain.getDeposit())
        .comment(domain.getComment())
        .qFirmId(domain.getQFirmId())
        .build();
  }
}
