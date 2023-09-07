package ee.qrental.user.repository.spring;

import ee.qrental.user.entity.jakarta.RoleJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleSpringDataRepository extends JpaRepository<RoleJakartaEntity, Long> {

  RoleJakartaEntity findByName(final String name);
}
