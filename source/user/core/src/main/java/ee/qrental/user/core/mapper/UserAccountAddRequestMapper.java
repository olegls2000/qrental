package ee.qrental.user.core.mapper;

import static java.util.stream.Collectors.toList;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.driver.domain.Role;
import ee.qrental.driver.domain.UserAccount;
import ee.qrental.user.api.in.request.UserAccountAddRequest;

public class UserAccountAddRequestMapper implements AddRequestMapper<UserAccountAddRequest, UserAccount> {

  @Override
  public UserAccount toDomain(final UserAccountAddRequest request) {
    
    return UserAccount.builder()
        .id(null)
        .password(null)
        .username(request.getUsername())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .roles(request.getRoles().stream()
              .map(roleId-> Role.builder().id(roleId).build())
              .collect(toList()))
        .build();
  }
}
