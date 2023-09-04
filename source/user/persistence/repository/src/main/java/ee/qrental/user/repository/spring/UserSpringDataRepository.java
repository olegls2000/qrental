package ee.qrental.user.repository.spring;

import ee.qrental.user.entity.jakarta.UserJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSpringDataRepository
        extends JpaRepository<UserJakartaEntity, Long> {
}
