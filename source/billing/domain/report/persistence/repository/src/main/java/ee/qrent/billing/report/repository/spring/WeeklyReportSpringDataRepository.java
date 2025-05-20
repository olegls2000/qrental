package ee.qrent.billing.report.repository.spring;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeeklyReportSpringDataRepository
    extends JpaRepository<WeeklyReportJakartaEntity, Long> {}
