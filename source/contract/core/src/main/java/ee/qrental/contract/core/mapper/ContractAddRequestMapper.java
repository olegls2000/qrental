package ee.qrental.contract.core.mapper;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.contract.api.in.request.ContractAddRequest;
import ee.qrental.contract.domain.Contract;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractAddRequestMapper implements AddRequestMapper<ContractAddRequest, Contract> {

  private final GetDriverQuery driverQuery;

  @Override
  public Contract toDomain(ContractAddRequest request) {
    final var driverId = request.getDriverId();
    final var driver = driverQuery.getById(driverId);
    final var currentDate = LocalDate.now();
    final var contractNumber = getContractNumber(currentDate, driverId);
    final var renterName = getRenterName(driver);
    final var renterRegistrationNumber = getRenterRegistrationNumber(driver);

    return Contract.builder()
        .id(null)
        .number(contractNumber)
        .renterName(renterName)
        .renterRegistrationNumber(renterRegistrationNumber)
        .renterCeoName("Default CEO")
        .renterCeoIsikukood(38010100202L)
        .renterPhone(driver.getPhone())
        .renterEmail(driver.getEmail())
        .driverIsikukood(driver.getIsikukood())
        .driverLicenceNumber(driver.getDriverLicenseNumber())
        .build();
  }

  private String getRenterName(final DriverResponse driver) {
    final var driverCompanyName = driver.getCompanyName();
    if (driverCompanyName == null || driverCompanyName.isEmpty()) {
      final var driverFirstName = driver.getFirstName();
      final var driverLastName = driver.getLastName();
      return format("%s %s", driverFirstName, driverLastName);
    }
    return driverCompanyName;
  }

  private String getContractNumber(final LocalDate currentDate, final Long driverId) {
    final var dateString = currentDate.format(ofPattern("yyyyMMdd"));
    return format("%s-%d", dateString, driverId);
  }

  private String getRenterRegistrationNumber(final DriverResponse driver) {
    final var renterRegistrationNumber = driver.getCompanyRegistrationNumber();

    return renterRegistrationNumber;
  }
}
