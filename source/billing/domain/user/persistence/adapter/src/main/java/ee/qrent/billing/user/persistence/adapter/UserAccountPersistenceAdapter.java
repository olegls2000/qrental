package ee.qrent.billing.user.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.user.domain.Role;
import ee.qrent.billing.user.domain.UserAccount;
import ee.qrent.billing.user.persistence.mapper.UserAccountAdapterMapper;
import ee.qrent.billing.user.persistence.repository.RoleRepository;
import ee.qrent.billing.user.persistence.repository.UserAccountRepository;
import ee.qrent.billing.user.api.out.UserAccountAddPort;
import ee.qrent.billing.user.api.out.UserAccountDeletePort;
import ee.qrent.billing.user.api.out.UserAccountUpdatePort;
import ee.qrent.billing.user.persistence.entity.jakarta.UserAccountJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountPersistenceAdapter
    implements UserAccountAddPort, UserAccountUpdatePort, UserAccountDeletePort {

  private final UserAccountRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserAccountAdapterMapper userAdapterMapper;

  @Override
  public UserAccount add(final UserAccount domain) {
    final var userToSave = userAdapterMapper.mapToEntity(domain);
    final var roles =
        roleRepository.findAllById(domain.getRoles().stream().map(Role::getId)
                .collect(toList()));
    userToSave.setRoles(roles);
    final var userSaved = userRepository.save(userToSave);

    return userAdapterMapper.mapToDomain(userSaved);
  }

  @Override
  public UserAccount update(final UserAccount domain) {
    final var userId = domain.getId();
    final var entityFromDatabase = userRepository.getReferenceById(userId);
    final var updatedEntity = updateSavedEntity(entityFromDatabase, domain);

    return userAdapterMapper.mapToDomain(userRepository.save(updatedEntity));
  }

  private UserAccountJakartaEntity updateSavedEntity(
      final UserAccountJakartaEntity entity, final UserAccount domain) {
    entity.setFirstName(domain.getFirstName());
    entity.setLastName(domain.getLastName());
    final var roles =
            roleRepository.findAllById(domain.getRoles().stream().map(Role::getId)
                    .collect(toList()));
    entity.setRoles(roles);

    return entity;
  }

  @Override
  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
