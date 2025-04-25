package ee.qrent.billing.contract.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.contract.api.in.request.AuthorizationUpdateRequest;
import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import ee.qrent.billing.contract.domain.Authorization;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationUpdateRequestMapper
    implements UpdateRequestMapper<AuthorizationUpdateRequest, Authorization> {

  private final AuthorizationLoadPort loadPort;

  @Override
  public Authorization toDomain(final AuthorizationUpdateRequest request) {
    final var fromDb = loadPort.loadById(request.getId());

    return fromDb;
  }

  @Override
  public AuthorizationUpdateRequest toRequest(final Authorization domain) {
    return AuthorizationUpdateRequest.builder().id(domain.getId()).build();
  }
}
