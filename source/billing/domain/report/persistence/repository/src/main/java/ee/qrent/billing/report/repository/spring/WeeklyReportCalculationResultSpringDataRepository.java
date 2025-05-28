package ee.qrent.billing.report.repository.spring;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyReportCalculationResultSpringDataRepository
    extends JpaRepository<WeeklyReportCalculationResultJakartaEntity, Long> {}
