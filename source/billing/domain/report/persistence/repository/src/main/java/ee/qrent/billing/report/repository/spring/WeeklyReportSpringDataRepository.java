package ee.qrent.billing.report.repository.spring;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeeklyReportSpringDataRepository
    extends JpaRepository<WeeklyReportJakartaEntity, Long> {

  @Query(
      value =
          "select inv.* from weekly_report wr "
              + " LEFT JOIN weekly_report_calculation_result wrcr ON wr.id = wrcr.invoice_id "
              + " where wrcr.calculation_id =:calculationId",
      nativeQuery = true)
  List<WeeklyReportJakartaEntity> findByCalculationId(
      @Param("calculationId") final Long calculationId);
}
