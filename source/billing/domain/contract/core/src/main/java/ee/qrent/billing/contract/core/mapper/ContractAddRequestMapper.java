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
    final var qFirmId = request.getQFirmId();
    final var qFirm = firmQuery.getById(qFirmId);

    return Contract.builder()
        .id(null)
        .number(contractNumber)
        .renter(getRenter(driver))
        .renterLhvAccount(driver.getLhvAccount())
        .renterRegistrationNumber(getRenterRegistrationNumber(driver))
        .renterSignerName(getRenterSignerName(driver))
        .renterSignerTaxNumber(getRenterSignerTaxNumber(driver))
        .renterAddress(getRenterAddress(driver))
        .renterPhone(driver.getPhone())
        .renterEmail(driver.getEmail())
        .driverId(driverId)
        .driverAddress(driver.getAddress())
        .driverTaxNumber(driver.getTaxNumber())
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
        .contractDuration(getContractDurationByLabel(request.getContractDuration()))
        .build();
  }

  private ContractDuration getContractDurationByLabel(final String label) {
    return stream(ContractDuration.values())
        .filter(duration -> duration.getLabel().equals(label))
        .findFirst()
        .orElseThrow(
            () -> new RuntimeException(format("No contract duration found for label '%s'", label)));
  }

  private String getRenter(final DriverResponse driver) {
    final var legalEntity = driver.getLegalEntityType();
    final var driverFirstName = driver.getFirstName();
    final var driverLastName = driver.getLastName();

    return switch (legalEntity) {
      case "PERSON", "LHV_ACCOUNT" -> format("%s %s", driverFirstName, driverLastName);
      case "SELF_EMPLOYED" -> format("%s %s FIE", driverFirstName, driverLastName);
      case "COMPANY" -> driver.getCompanyName();
      default -> throw new RuntimeException(format("Unknown legal entity type: %s", legalEntity));
    };
  }

  private String getRenterSignerName(final DriverResponse driver) {
    final var legalEntity = driver.getLegalEntityType();
    final var driverFirstName = driver.getFirstName();
    final var driverLastName = driver.getLastName();

    return switch (legalEntity) {
      case "PERSON", "LHV_ACCOUNT", "SELF_EMPLOYED" ->
          format("%s %s", driverFirstName, driverLastName);
      case "COMPANY" -> driver.getCompanyCeoName();
      default -> throw new RuntimeException(format("Unknown legal entity type: %s", legalEntity));
    };
  }

  private String generateContractNumber(final Long driverId, final LocalDateTime startTime) {
    final var dateTimeString = startTime.format(ofPattern("yyyyMMdd-HHmm"));

    return String.format("%s-%d", dateTimeString, driverId);
  }

  private String getRenterRegistrationNumber(final DriverResponse driver) {
    final var driverTaxNumberString = driver.getTaxNumber().toString();
    final var legalEntity = driver.getLegalEntityType();
    return switch (legalEntity) {
      case "PERSON", "LHV_ACCOUNT" -> driverTaxNumberString;
      case "SELF_EMPLOYED", "COMPANY" -> driver.getCompanyRegistrationNumber();
      default -> throw new RuntimeException(format("Unknown legal entity type: %s", legalEntity));
    };
  }

  private Long getRenterSignerTaxNumber(final DriverResponse driver) {
    final var legalEntity = driver.getLegalEntityType();
    return switch (legalEntity) {
      case "PERSON", "LHV_ACCOUNT", "SELF_EMPLOYED" -> driver.getTaxNumber();
      case "COMPANY" -> driver.getCompanyCeoTaxNumber();
      default -> throw new RuntimeException(format("Unknown legal entity type: %s", legalEntity));
    };
  }

  private String getRenterAddress(final DriverResponse driver) {
    final var legalEntity = driver.getLegalEntityType();
    return switch (legalEntity) {
      case "PERSON", "LHV_ACCOUNT"  -> driver.getAddress();
      case "COMPANY", "SELF_EMPLOYED" -> driver.getCompanyAddress();
      default -> throw new RuntimeException(format("Unknown legal entity type: %s", legalEntity));
    };
  }
}
