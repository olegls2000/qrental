package ee.qrent.billing.report.adapter.repository;

import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import java.util.List;

public interface WeeklyReportRepository {
  WeeklyReportJakartaEntity save(final WeeklyReportJakartaEntity entity);
}
