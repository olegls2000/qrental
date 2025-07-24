package ee.qrent.billing.report.adapter.mapper;

import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTypeJakarta;

import java.util.Collections;

public class WeeklyReportCalculationAdapterMapper {

  public WeeklyReportCalculationJakartaEntity mapToEntity(final WeeklyReportCalculation domain) {
    return WeeklyReportCalculationJakartaEntity.builder()
        .id(domain.getId())
        .type(WeeklyReportTypeJakarta.valueOf(domain.getReportType().name()))
        .actionDate(domain.getActionDate())
        .qWeekId(domain.getQWeekId())
        .comment(domain.getComment())
        .build();
  }

  public WeeklyReportCalculation mapToDomain(final WeeklyReportCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return WeeklyReportCalculation.builder()
        .id(entity.getId())
        .reportType(WeeklyReportType.valueOf(entity.getType().name()))
        .qWeekId(entity.getQWeekId())
        .actionDate(entity.getActionDate())
        .reportTransactionLinks(Collections.emptyList())
        .comment(entity.getComment())
        .build();
  }
}
