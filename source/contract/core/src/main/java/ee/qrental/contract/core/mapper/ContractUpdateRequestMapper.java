package ee.qrental.contract.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.contract.api.in.request.ContractUpdateRequest;
import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.domain.Contract;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractUpdateRequestMapper
    implements UpdateRequestMapper<ContractUpdateRequest, Contract> {

  private final ContractLoadPort loadPort;

  @Override
  public Contract toDomain(final ContractUpdateRequest request) {
    final var contractFromDb = loadPort.loadById(request.getId());

    return contractFromDb;
  }

  @Override
  public ContractUpdateRequest toRequest(final Contract domain) {
    return ContractUpdateRequest.builder()
        .id(domain.getId())
        .build();
  }
}
