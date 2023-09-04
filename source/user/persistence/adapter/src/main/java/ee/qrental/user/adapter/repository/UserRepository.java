package ee.qrental.user.adapter.repository;

import ee.qrental.user.entity.jakarta.UserJakartaEntity;
import java.util.List;

public interface UserRepository {
  
  List<UserJakartaEntity> findAll();

  UserJakartaEntity save(final UserJakartaEntity entity);

  UserJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
