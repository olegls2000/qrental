package ee.qrental.contract.core.mapper;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.contract.api.in.response.ContractResponse;
import ee.qrental.contract.domain.Contract;

import static java.lang.String.format;

public class ContractResponseMapper implements ResponseMapper<ContractResponse, Contract> {
    @Override
    public ContractResponse toResponse(final Contract domain) {
        if (domain == null) {
            return null;
        }
        return ContractResponse.builder()
                .id(domain.getId())
                .active(domain.isActive())
                .number(domain.getNumber())
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
                .build();
    }

    @Override
    public String toObjectInfo(final Contract domain) {
        return format("Number: %s, Renter: %s", domain.getNumber(), domain.getRenterName());
    }
}
