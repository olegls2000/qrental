package ee.qrent.billing.contract.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.contract.api.in.request.ContractAddRequest;
import ee.qrent.billing.contract.domain.Contract;
import ee.qrent.billing.contract.domain.ContractDuration;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.stream;

@AllArgsConstructor
public class ContractAddRequestMapper implements AddRequestMapper<ContractAddRequest, Contract> {

  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;
  private final QDateTime qDateTime;

  @Override
  public Contract toDomain(final ContractAddRequest request) {
    final var driverId = request.getDriverId();
    final var driver = driverQuery.getById(driverId);
    final var contractNumber = generateContractNumber(driverId, qDateTime.getNow());
    final var renterName = getRenterName(driver);
    final var renterAddress = getRenterAddress(driver);
    final var companyCeoTaxNumber = getCompanyCeoTaxNumber(driver);
    final var renterCeoName = getRenterCeoName(driver);
    final var renterRegistrationNumber = getRenterRegistrationNumber(driver);
    final var driverAddress = getDriverAddress(driver);

    final var qFirmId = request.getQFirmId();
    final var qFirm = firmQuery.getById(qFirmId);

    return Contract.builder()
        .id(null)
        .number(contractNumber)
        .renterName(renterName)
        .renterLhvAccount(driver.getLhvAccount())
        .renterRegistrationNumber(renterRegistrationNumber)
        .renterCeoName(renterCeoName)
        .renterCeoIsikukood(companyCeoTaxNumber)
        .renterPhone(driver.getPhone())
        .renterEmail(driver.getEmail())
        .driverId(driverId)
        .driverIsikukood(driver.getIsikukood())
        .driverLicenceNumber(driver.getDriverLicenseNumber())
        .qFirmId(qFirmId)
        .qFirmName(qFirm.getName())
        .qFirmRegistrationNumber(qFirm.getRegistrationNumber())
        .qFirmPostAddress(qFirm.getPostAddress())
        .qFirmEmail(qFirm.getEmail())
        .qFirmCeo(qFirm.getCeoName())
        .qFirmCeoDeputies(qFirm.getDeputies())
        .created(qDateTime.getToday())
        .dateStart(request.getDateStart())
        .dateEnd(null)
        .qFirmVatNumber(qFirm.getVatNumber())
        .qFirmIban(qFirm.getIban())
        .qFirmVatPhone(qFirm.getPhone())
        .renterAddress(renterAddress)
        .driverAddress(driverAddress)
        .contractDuration(getContractDurationByLabel(request.getContractDuration()))
        .build();
  }

  private ContractDuration getContractDurationByLabel(final String label) {
    return stream(ContractDuration.values())
        .filter(duration -> duration.getLabel().equals(label))
        .findFirst()
        .get();
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

  private Long getCompanyCeoTaxNumber(final DriverResponse driver) {
    final var companyCeoTaxNumber = driver.getCompanyCeoTaxNumber();
    if (companyCeoTaxNumber == null) {
      final var esindajaIsikukood = driver.getIsikukood();
      return esindajaIsikukood;
    }
    return companyCeoTaxNumber;
  }

  private String getRenterCeoName(final DriverResponse driver) {
    final var driverCompanyCeoName = driver.getCompanyCeoName();
    if (driverCompanyCeoName == null
        || driverCompanyCeoName.isEmpty()
        || driverCompanyCeoName.equals(" ")) {
      final var renterCeoFirstName = driver.getFirstName();
      final var renterCeoLastName = driver.getLastName();
      return format("%s %s", renterCeoFirstName, renterCeoLastName);
    }
    return driverCompanyCeoName;
  }

  private String generateContractNumber(final Long driverId, final LocalDateTime startTime) {
    final var dateTimeString = startTime.format(ofPattern("yyyyMMdd-HHmm"));

    return String.format("%s-%d", dateTimeString, driverId);
  }

  private String getRenterRegistrationNumber(final DriverResponse driver) {
    final var renterRegistrationNumber = driver.getCompanyRegistrationNumber();
    if (renterRegistrationNumber == null || renterRegistrationNumber.isEmpty()) {
      final var renterIsikukood = driver.getIsikukood();
      return format("%s", renterIsikukood);
    }
    return renterRegistrationNumber;
  }

  private String getRenterAddress(final DriverResponse driver) {
    final var renterAddress = driver.getCompanyAddress();
    if (renterAddress == null || renterAddress.isEmpty()) {
      final var renterAddress1 = driver.getAddress();
      return format("%s", renterAddress1);
    }
    return renterAddress;
  }

  private String getDriverAddress(final DriverResponse driver) {
    final var driverAddress = driver.getAddress();

    return driverAddress;
  }
}
