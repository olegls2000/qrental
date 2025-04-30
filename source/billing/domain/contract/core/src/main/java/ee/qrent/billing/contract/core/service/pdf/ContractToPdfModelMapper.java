package ee.qrent.billing.contract.core.service.pdf;

import ee.qrent.billing.contract.domain.Contract;
import ee.qrent.billing.contract.domain.ContractDuration;

public class ContractToPdfModelMapper {

  public ContractPdfModel getPdfModel(final Contract contract) {

    final var duration1 = getStringDuration1(contract.getContractDuration());

    return ContractPdfModel.builder()
        .driverId(contract.getDriverId())
        .durationWeeksCount(contract.getContractDuration().getWeeksCount())
        .number(contract.getNumber())
        .dateStart(contract.getDateStart())
        .duration(contract.getContractDuration().name())
        .duration1(duration1)
        .renter(contract.getRenter())
        .renterLhvAccount(contract.getRenterLhvAccount())
        .renterRegistrationNumber(contract.getRenterRegistrationNumber())
        .renterAddress(contract.getRenterAddress())
        .renterSignerName(contract.getRenterSignerName())
        .renterSignerTaxNumber(contract.getRenterSignerTaxNumber())
        .renterEmail(contract.getRenterEmail())
        .driverTaxNumber(contract.getDriverTaxNumber())
        .driverLicenceNumber(contract.getDriverLicenceNumber())
        .driverAddress(contract.getDriverAddress())
        .renterPhone(contract.getRenterPhone())
        .created(contract.getCreated())
        .qFirmName(contract.getQFirmName())
        .qFirmPostAddress(contract.getQFirmPostAddress())
        .qFirmRegNumber(contract.getQFirmRegistrationNumber())
        .qFirmVatNumber(contract.getQFirmVatNumber())
        .qFirmIban(contract.getQFirmIban())
        .qFirmEmail(contract.getQFirmEmail())
        .qFirmPhone(contract.getQFirmVatPhone())
        .qFirmCeo(contract.getQFirmCeo())
        .carVin(contract.getCarVin())
        .carManufacturer(contract.getCarManufacturer())
        .carModel(contract.getCarModel())
        .build();
  }

  private String getStringDuration1(final ContractDuration contractDuration) {
    return switch (contractDuration) {
      case FOUR_WEEKS -> "seitse";
      case TWELVE_WEEKS -> "neliteist";
    };
  }
}
