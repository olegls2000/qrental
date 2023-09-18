package ee.qrental.contract.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.contract.api.in.response.ContractResponse;
import ee.qrental.contract.domain.Contract;

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
        .renterCeoName(domain.getRenterCeoName())
        .driverIsikukood(domain.getDriverTaxNumber())
        .qFirmName(domain.getQFirmName())
        .qFirmCeo(domain.getQFirmCeo())
        .created(domain.getCreated())
        .build();
  }

  @Override
  public String toObjectInfo(final Contract domain) {
    return format("Number: %s, Renter: %s", domain.getNumber(), domain.getRenterName());
  }
}
