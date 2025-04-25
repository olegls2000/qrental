package ee.qrent.billing.user.persistence.repository;

import ee.qrent.billing.user.persistence.entity.jakarta.UserAccountJakartaEntity;
import java.util.List;

public interface UserAccountRepository {

  List<UserAccountJakartaEntity> findAll();

  List<UserAccountJakartaEntity> findByRoleId(final Long roleId);

  List<UserAccountJakartaEntity> findByRoleName(final String roleName);

  UserAccountJakartaEntity save(final UserAccountJakartaEntity entity);

  UserAccountJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  UserAccountJakartaEntity findByUsername(final String username);

  UserAccountJakartaEntity findByEmail(final String email);
}
