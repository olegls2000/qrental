package ee.qrent.billing.report.repository.spring;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeeklyReportCalculationSpringDataRepository
    extends JpaRepository<WeeklyReportCalculationJakartaEntity, Long> {
  @Query(
      value =
          "select wrc.q_week_id from weelky_report_calculation wrc LEFT JOIN q_week qw on wrc.q_week_id = qw.id order by qw.year desc, qw.number desc limit 1",
      nativeQuery = true)
  Long getLastCalculationQWeekId();
}
