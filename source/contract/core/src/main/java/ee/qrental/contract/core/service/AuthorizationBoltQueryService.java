package ee.qrental.contract.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.contract.api.in.query.GetAuthorizationBoltQuery;
import ee.qrental.contract.api.in.request.AuthorizationBoltUpdateRequest;
import ee.qrental.contract.api.in.response.AuthorizationBoltResponse;
import ee.qrental.contract.api.out.AuthorizationBoltLoadPort;
import ee.qrental.contract.core.mapper.AuthorizationBoltResponseMapper;
import ee.qrental.contract.core.mapper.AuthorizationBoltUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltQueryService implements GetAuthorizationBoltQuery {

  private final AuthorizationBoltLoadPort loadPort;
  private final AuthorizationBoltResponseMapper mapper;
  private final AuthorizationBoltUpdateRequestMapper updateRequestMapper;

  @Override
  public List<AuthorizationBoltResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public AuthorizationBoltResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public AuthorizationBoltUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
