package ee.qrent.billing.report.repository.spring;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTypeJakarta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeeklyReportCalculationSpringDataRepository
    extends JpaRepository<WeeklyReportCalculationJakartaEntity, Long> {
  @Query(
      value =
          "select wrc.q_week_id from weekly_report_calculation wrc LEFT JOIN q_week qw on wrc.q_week_id = qw.id order by qw.year desc, qw.number desc limit 1",
      nativeQuery = true)
  Long getLastCalculationQWeekId();


    @Query(
            value =
                    "select * from weekly_report_calculation where q_week_id =:qWeekId and type =:type ",
            nativeQuery = true)

  WeeklyReportCalculationJakartaEntity getByQWeekIdAndType(
            final @Param("qWeekId") Long qWeekId, final @Param("type") String type);
}
