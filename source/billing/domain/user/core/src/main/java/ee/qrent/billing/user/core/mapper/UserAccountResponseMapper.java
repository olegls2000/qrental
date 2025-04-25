package ee.qrent.billing.user.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.user.domain.UserAccount;
import ee.qrent.billing.user.api.in.response.UserAccountResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountResponseMapper implements ResponseMapper<UserAccountResponse, UserAccount> {

  @Override
  public UserAccountResponse toResponse(final UserAccount domain) {

    return UserAccountResponse.builder()
        .id(domain.getId())
        .username(domain.getUsername())
        .password(domain.getPassword())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .email(domain.getEmail())
        .rolesCount(domain.getRoles().size())
        .build();
  }

  @Override
  public String toObjectInfo(UserAccount domain) {
    return format("%s %s ", domain.getFirstName(), domain.getLastName());
  }
}
