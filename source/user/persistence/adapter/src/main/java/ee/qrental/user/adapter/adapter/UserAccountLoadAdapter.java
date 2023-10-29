package ee.qrental.user.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.driver.domain.UserAccount;
import ee.qrental.user.adapter.mapper.UserAccountAdapterMapper;
import ee.qrental.user.adapter.repository.UserAccountRepository;
import ee.qrental.user.api.out.UserAccountLoadPort;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountLoadAdapter implements UserAccountLoadPort {

  private final UserAccountRepository repository;
  private final UserAccountAdapterMapper mapper;

  @Override
  public List<UserAccount> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public UserAccount loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public UserAccount loadByEmail(final String email) {
    final var entity = repository.findByEmail(email);

    return mapper.mapToDomain(entity);
  }

  @Override
  public UserAccount loadByUsername(final String username) {
    final var entity = repository.findByUsername(username);

    return mapper.mapToDomain(entity);
  }

  @Override
  public List<UserAccount> loadByRoleId(final Long roleId) {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<UserAccount> loadByRoleName(final String roleName) {
    return repository.findByRoleName(roleName).stream().map(mapper::mapToDomain).collect(toList());
  }
}
