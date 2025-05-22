package ee.qrent.billing.report.repository.spring;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyReportCalculationSpringDataRepository
    extends JpaRepository<WeeklyReportCalculationJakartaEntity, Long> {}
