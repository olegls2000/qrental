package ee.qrental.contract.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.contract.api.in.query.GetAuthorizationQuery;
import ee.qrental.contract.api.in.request.AuthorizationUpdateRequest;
import ee.qrental.contract.api.in.response.AuthorizationResponse;
import ee.qrental.contract.api.out.AuthorizationLoadPort;
import ee.qrental.contract.core.mapper.AuthorizationResponseMapper;
import ee.qrental.contract.core.mapper.AuthorizationUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationQueryService implements GetAuthorizationQuery {

  private final AuthorizationLoadPort loadPort;
  private final AuthorizationResponseMapper mapper;
  private final AuthorizationUpdateRequestMapper updateRequestMapper;

  @Override
  public List<AuthorizationResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public AuthorizationResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public AuthorizationUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
