package ee.qrental.constant.repository.spring;

import ee.qrental.constant.entity.jakarta.ConstantJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstantSpringDataRepository
        extends JpaRepository<ConstantJakartaEntity, Long> {
    ConstantJakartaEntity findByConstant(final String constant);
}
