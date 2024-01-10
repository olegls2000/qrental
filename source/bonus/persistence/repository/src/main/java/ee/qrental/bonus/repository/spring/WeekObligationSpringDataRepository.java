package ee.qrental.bonus.repository.spring;

import ee.qrental.bonus.entity.jakarta.WeekObligationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekObligationSpringDataRepository
    extends JpaRepository<WeekObligationJakartaEntity, Long> {}
