package ee.qrent.billing.report.adapter.repository;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;

import java.util.List;

public interface WeeklyReportCalculationRepository {
  WeeklyReportCalculationJakartaEntity save(final WeeklyReportCalculationJakartaEntity entity);

  List<WeeklyReportCalculationJakartaEntity> findAll();

  WeeklyReportCalculationJakartaEntity getReferenceById(final Long id);

  Long getLastCalculatedQWeekId();
}
