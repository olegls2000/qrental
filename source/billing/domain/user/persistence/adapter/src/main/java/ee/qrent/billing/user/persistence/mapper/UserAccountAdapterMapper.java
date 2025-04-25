package ee.qrent.billing.user.persistence.mapper;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.user.domain.Role;
import ee.qrent.billing.user.domain.UserAccount;
import ee.qrent.billing.user.persistence.entity.jakarta.RoleJakartaEntity;
import ee.qrent.billing.user.persistence.entity.jakarta.UserAccountJakartaEntity;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountAdapterMapper {

  public UserAccount mapToDomain(final UserAccountJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    final var roles = mapToDomain(entity.getRoles());

    return UserAccount.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .username(entity.getUsername())
        .password(entity.getPassword())
        .roles(roles)
        .build();
  }

  private List<Role> mapToDomain(final List<RoleJakartaEntity> roles) {
    if (roles == null) {
      return emptyList();
    }
    return roles.stream()
        .map(entity -> Role.builder().id(entity.getId()).name(entity.getName()).build())
        .collect(toList());
  }

  public UserAccountJakartaEntity mapToEntity(final UserAccount domain) {

    return UserAccountJakartaEntity.builder()
        .id(domain.getId())
        .username(domain.getUsername())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .password(domain.getPassword())
        .email(domain.getEmail())
        .build();
  }
}
