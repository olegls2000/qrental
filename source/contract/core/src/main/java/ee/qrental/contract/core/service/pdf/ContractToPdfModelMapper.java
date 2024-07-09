package ee.qrental.contract.core.service.pdf;

import ee.qrental.contract.domain.Contract;
import ee.qrental.contract.domain.ContractDuration;

public class ContractToPdfModelMapper {

  public ContractPdfModel getPdfModel(final Contract contract) {

    final var duration = getStringDuration(contract.getContractDuration());

    final var duration1 = getStringDuration1(contract.getContractDuration());

    return ContractPdfModel.builder()
        .number(contract.getNumber())
        .duration(duration)
        .duration1(duration1)
        .renterName(contract.getRenterName())
        .renterRegistrationNumber(contract.getRenterRegistrationNumber())
        .renterAddress(contract.getRenterAddress())
        .renterCeoName(contract.getRenterCeoName())
        .renterCeoIsikukood(contract.getRenterCeoIsikukood())
        .renterEmail(contract.getRenterEmail())
        .driverIsikukood(contract.getDriverIsikukood())
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
        .qFirmVatPhone(contract.getQFirmVatPhone())
        .qFirmCeo(contract.getQFirmCeo())
        .carVin(contract.getCarVin())
        .carManufacturer(contract.getCarManufacturer())
        .carModel(contract.getCarModel())
        .build();
  }

  private String getStringDuration(final ContractDuration contractDuration) {
    return switch (contractDuration) {
      case FOUR_WEEKS -> "neli";
      case TWELVE_WEEKS -> "kaksteist";
    };
  }

  private String getStringDuration1(final ContractDuration contractDuration) {
    return switch (contractDuration) {
      case FOUR_WEEKS -> "seitse";
      case TWELVE_WEEKS -> "neliteist";
    };
  }



}
