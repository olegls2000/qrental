package ee.qrental.user.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrental.driver.domain.Role;
import ee.qrental.user.api.in.request.RoleUpdateRequest;

public class RoleUpdateRequestMapper implements UpdateRequestMapper<RoleUpdateRequest, Role> {

  @Override
  public Role toDomain(final RoleUpdateRequest request) {
    return Role.builder()
        .id(request.getId())
        .name(request.getName())
        .comment(request.getComment())
        .build();
  }

  @Override
  public RoleUpdateRequest toRequest(final Role domain) {
    return RoleUpdateRequest.builder()
            .id(domain.getId())
            .name(domain.getName())
            .comment(domain.getComment())
            .build();
  }
}
