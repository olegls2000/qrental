package ee.qrental.link.repository.spring;

import ee.qrental.link.entity.jakarta.LinkJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkSpringDataRepository
        extends JpaRepository<LinkJakartaEntity, Long> {
}
