package ee.qrent.billing.report.adapter.mapper;

import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;

import java.util.Collections;

public class WeeklyReportCalculationAdapterMapper {

  public WeeklyReportCalculation mapToDomain(final WeeklyReportCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return WeeklyReportCalculation.builder()
        .id(entity.getId())
        .qWeekId(entity.getQWeekId())
        .actionDate(entity.getActionDate())
        .reportTransactionLinks(Collections.emptyList())
        .comment(entity.getComment())
        .build();
  }
}
