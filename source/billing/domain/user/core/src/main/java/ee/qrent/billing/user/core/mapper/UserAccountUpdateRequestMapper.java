package ee.qrent.billing.user.core.mapper;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.user.domain.Role;
import ee.qrent.billing.user.domain.UserAccount;
import ee.qrent.billing.user.api.in.request.UserAccountUpdateRequest;
import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountUpdateRequestMapper implements UpdateRequestMapper<UserAccountUpdateRequest, UserAccount> {

  private UserAccountLoadPort userAccountloadPort;

  @Override
  public UserAccount toDomain(final UserAccountUpdateRequest request) {
   final var userAccountFromDb =  userAccountloadPort.loadById(request.getId());
    userAccountFromDb.setFirstName(request.getFirstName());
    userAccountFromDb.setLastName(request.getLastName());
    userAccountFromDb.setRoles(request.getRoles().stream()
            .map(roleId-> Role.builder().id(roleId).build())
            .collect(toList()));

    return userAccountFromDb;
  }

  @Override
  public UserAccountUpdateRequest toRequest(final UserAccount domain) {
    final var roleIds = domain.getRoles().stream().map(Role::getId).collect(toSet());

    return UserAccountUpdateRequest.builder()
        .id(domain.getId())
        .username(domain.getUsername())
        .email(domain.getEmail())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .roles(roleIds)
        .build();
  }
}
