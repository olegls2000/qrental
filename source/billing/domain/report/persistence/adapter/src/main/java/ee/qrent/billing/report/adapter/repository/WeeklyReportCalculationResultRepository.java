package ee.qrent.billing.report.adapter.repository;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationResultJakartaEntity;

public interface WeeklyReportCalculationResultRepository {
  WeeklyReportCalculationResultJakartaEntity save(
      final WeeklyReportCalculationResultJakartaEntity entity);
}
