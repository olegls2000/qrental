package ee.qrental.user.repository.spring;

import ee.qrental.user.entity.jakarta.UserAccountJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountSpringDataRepository
        extends JpaRepository<UserAccountJakartaEntity, Long> {
    UserAccountJakartaEntity findByEmail(final String email);
    UserAccountJakartaEntity findByUsername(final String username);
}
