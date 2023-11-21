package ee.qrental.contract.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.contract.api.in.request.AuthorizationBoltUpdateRequest;
import ee.qrental.contract.api.out.AuthorizationBoltLoadPort;
import ee.qrental.contract.domain.AuthorizationBolt;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltUpdateRequestMapper
    implements UpdateRequestMapper<AuthorizationBoltUpdateRequest, AuthorizationBolt> {

  private final AuthorizationBoltLoadPort loadPort;

  @Override
  public AuthorizationBolt toDomain(final AuthorizationBoltUpdateRequest request) {
    final var fromDb = loadPort.loadById(request.getId());

    return fromDb;
  }

  @Override
  public AuthorizationBoltUpdateRequest toRequest(final AuthorizationBolt domain) {
    return AuthorizationBoltUpdateRequest.builder().id(domain.getId()).build();
  }
}
