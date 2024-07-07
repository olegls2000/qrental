package ee.qrental.user.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.driver.domain.Role;
import ee.qrental.user.api.in.request.RoleAddRequest;

public class RoleAddRequestMapper implements AddRequestMapper<RoleAddRequest, Role> {

  @Override
  public Role toDomain(final RoleAddRequest request) {
    return Role.builder()
        .id(null)
        .name(request.getName())
        .comment(request.getComment())
        .build();
  }
}
