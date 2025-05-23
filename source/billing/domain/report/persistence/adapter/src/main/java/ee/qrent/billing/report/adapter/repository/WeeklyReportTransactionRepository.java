package ee.qrent.billing.report.adapter.repository;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTransactionJakartaEntity;

public interface WeeklyReportTransactionRepository {
  WeeklyReportTransactionJakartaEntity save(final WeeklyReportTransactionJakartaEntity entity);
}
