package ee.qrent.billing.persistence.user.repository.spring;

import ee.qrent.billing.user.persistence.entity.jakarta.RoleJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleSpringDataRepository extends JpaRepository<RoleJakartaEntity, Long> {

  RoleJakartaEntity findByName(final String name);
}
