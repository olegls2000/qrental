package ee.qrental.contract.core.service.pdf;

import ee.qrental.contract.domain.Contract;

public class ContractToPdfModelMapper {

  public ContractPdfModel getPdfModel(final Contract contract) {

    return ContractPdfModel.builder()
        .number(contract.getNumber())
        .renterName(contract.getRenterName())
        .renterRegistrationNumber(contract.getRenterRegistrationNumber())
        .renterCeoName(contract.getRenterCeoName())
        .renterCeoIsikukood(contract.getRenterCeoTaxNumber())
        .renterPhone(contract.getRenterPhone())
        .renterEmail(contract.getRenterEmail())
        .driverIsikukood(contract.getDriverTaxNumber())
        .driverLicenceNumber(contract.getDriverLicenceNumber())
        .created(contract.getCreated())
        .build();
  }
}
