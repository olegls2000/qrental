package ee.qrental.insurance.core.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import ee.qrental.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrental.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrental.insurance.api.in.response.InsuranceCaseResponse;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.core.mapper.InsuranceCaseResponseMapper;
import ee.qrental.insurance.core.mapper.InsuranceCaseUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseQueryService implements GetInsuranceCaseQuery {

  private final InsuranceCaseLoadPort loadPort;
  private final InsuranceCaseResponseMapper responseMapper;
  private final InsuranceCaseUpdateRequestMapper updateRequestMapper;

  @Override
  public List<InsuranceCaseResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public InsuranceCaseResponse getById(final Long id) {
    return responseMapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return responseMapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public InsuranceCaseUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
