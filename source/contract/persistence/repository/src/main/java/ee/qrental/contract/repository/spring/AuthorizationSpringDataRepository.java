package ee.qrental.contract.repository.spring;

import ee.qrental.contract.entity.jakarta.AuthorizationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationSpringDataRepository
    extends JpaRepository<AuthorizationJakartaEntity, Long> {}
