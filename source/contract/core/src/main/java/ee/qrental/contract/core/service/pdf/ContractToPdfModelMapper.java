package ee.qrental.contract.core.service.pdf;

import ee.qrental.contract.domain.Contract;

public class ContractToPdfModelMapper {

    public ContractPdfModel getPdfModel(final Contract contract) {

        // final var created = contract.getCreated();

        return ContractPdfModel.builder()
                .number(contract.getNumber())
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
                .build();
    }
}
