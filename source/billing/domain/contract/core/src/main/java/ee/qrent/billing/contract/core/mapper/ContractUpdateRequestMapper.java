package ee.qrent.billing.contract.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.contract.api.in.request.ContractUpdateRequest;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.domain.Contract;
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
    return ContractUpdateRequest.builder().id(domain.getId()).qFirmId(domain.getQFirmId()).build();
  }
}
