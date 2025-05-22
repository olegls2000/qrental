package ee.qrent.billing.report.adapter.repository;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import java.util.List;

public interface WeeklyReportRepository {
  List<WeeklyReportJakartaEntity> findAll();

  WeeklyReportJakartaEntity getReferenceById(final Long id);

  List<WeeklyReportJakartaEntity> findByCalculationId(final Long calculationId);

  WeeklyReportJakartaEntity save(final WeeklyReportJakartaEntity entity);
}
