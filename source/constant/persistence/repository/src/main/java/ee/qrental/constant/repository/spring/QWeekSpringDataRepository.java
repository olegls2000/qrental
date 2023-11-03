package ee.qrental.constant.repository.spring;

import ee.qrental.constant.entity.jakarta.QWeekJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QWeekSpringDataRepository
        extends JpaRepository<QWeekJakartaEntity, Long> {
}
