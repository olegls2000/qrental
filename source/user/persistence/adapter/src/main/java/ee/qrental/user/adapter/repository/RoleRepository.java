package ee.qrental.user.adapter.repository;

import ee.qrental.user.entity.jakarta.RoleJakartaEntity;
import java.util.List;

public interface RoleRepository {
  
  List<RoleJakartaEntity> findAll();

  RoleJakartaEntity save(final RoleJakartaEntity entity);

  RoleJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  RoleJakartaEntity findByName(final String name);
  List<RoleJakartaEntity> findAllById(Iterable<Long> ids);

}
