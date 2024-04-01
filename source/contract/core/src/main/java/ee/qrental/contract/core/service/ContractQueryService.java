package ee.qrental.contract.core.service;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.contract.api.in.request.ContractUpdateRequest;
import ee.qrental.contract.api.in.response.ContractResponse;
import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.core.mapper.ContractResponseMapper;
import ee.qrental.contract.core.mapper.ContractUpdateRequestMapper;

import java.util.List;

import ee.qrental.contract.domain.ContractDuration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractQueryService implements GetContractQuery {

  private final ContractLoadPort loadPort;
  private final ContractResponseMapper mapper;
  private final ContractUpdateRequestMapper updateRequestMapper;

  @Override
  public List<ContractResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public ContractResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public ContractUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public ContractResponse getActiveContractByDriverId(Long driverId) {
    return mapper.toResponse(loadPort.loadActiveByDriverId(driverId));
  }

  @Override
  public List<String> getAllDurations() {
    return stream(ContractDuration.values()).map(ContractDuration::getLabel).toList();
  }
}
