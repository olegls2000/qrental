package ee.qrent.billing.contract.core.mapper;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.contract.api.in.response.ContractResponse;
import ee.qrent.billing.contract.domain.Contract;
import lombok.AllArgsConstructor;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.DAYS;

@AllArgsConstructor
public class ContractResponseMapper implements ResponseMapper<ContractResponse, Contract> {
  private final QDateTime qDateTime;

  @Override
  public ContractResponse toResponse(final Contract domain) {
    if (domain == null) {
      return null;
    }
    return ContractResponse.builder()
        .id(domain.getId())
        .number(domain.getNumber())
        .duration(domain.getContractDuration().getWeeksCount())
        .renterName(domain.getRenterName())
        .renterRegistrationNumber(domain.getRenterRegistrationNumber())
        .renterAddress(domain.getRenterAddress())
        .renterCeoName(domain.getRenterCeoName())
        .renterCeoIsikukood(domain.getRenterCeoIsikukood())
        .renterPhone(domain.getRenterPhone())
        .renterEmail(domain.getRenterEmail())
        .driverId(domain.getDriverId())
        .driverIsikukood(domain.getDriverIsikukood())
        .driverLicenceNumber(domain.getDriverLicenceNumber())
        .driverAddress(domain.getDriverAddress())
        .qFirmId(domain.getQFirmId())
        .qFirmName(domain.getQFirmName())
        .qFirmRegistrationNumber(domain.getQFirmRegistrationNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmIban(domain.getQFirmIban())
        .qFirmPostAddress(domain.getQFirmPostAddress())
        .qFirmVatPhone(domain.getQFirmVatPhone())
        .qFirmEmail(domain.getQFirmEmail())
        .qFirmCeo(domain.getQFirmCeo())
        .carVin(domain.getCarVin())
        .carManufacturer(domain.getCarManufacturer())
        .carModel(domain.getCarModel())
        .created(domain.getCreated())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .weeksToEnd(getWeeksLeftCount(domain))
        .active(isActive(domain))
        .build();
  }

  private boolean isActive(final Contract domain) {
    if (domain.getDateEnd() == null) {
      return true;
    }
    return domain.getDateEnd().isAfter(qDateTime.getToday());
  }

  @Override
  public String toObjectInfo(final Contract domain) {
    return format("Number: %s, Renter: %s", domain.getNumber(), domain.getRenterName());
  }

  private Long getWeeksLeftCount(final Contract domain) {
    final var endDate = domain.getDateEnd();
    final var activeDays = DAYS.between(qDateTime.getToday(), endDate);
    final var activeWeeks = activeDays / 7;

    return activeWeeks;
  }
}
