package ee.qrent.billing.constant.repository.spring;

import ee.qrent.billing.constant.persistence.entity.jakarta.ConstantJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstantSpringDataRepository
        extends JpaRepository<ConstantJakartaEntity, Long> {
    ConstantJakartaEntity findByConstant(final String constant);
}
