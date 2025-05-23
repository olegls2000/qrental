package ee.qrent.billing.report.repository.spring;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTransactionJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyReportTransactionSpringDataRepository
    extends JpaRepository<WeeklyReportTransactionJakartaEntity, Long> {}
