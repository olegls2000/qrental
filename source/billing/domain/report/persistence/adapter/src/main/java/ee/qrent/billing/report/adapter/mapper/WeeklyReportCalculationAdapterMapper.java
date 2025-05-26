package ee.qrent.billing.report.adapter.mapper;

import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.domain.WeeklyReportTransactionsLink;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationResultJakartaEntity;

public class WeeklyReportCalculationAdapterMapper {

  public WeeklyReportCalculation mapToDomain(final WeeklyReportCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return WeeklyReportCalculation.builder()
        .id(entity.getId())
        .qWeekId(entity.getQWeekId())
        .actionDate(entity.getActionDate())
        .reportTransactionLinks(entity.getResults().stream().map(this::mapToDomain).toList())
        .comment(entity.getComment())
        .build();
  }

  private WeeklyReportTransactionsLink mapToDomain(
      final WeeklyReportCalculationResultJakartaEntity resultEntity) {

    return WeeklyReportTransactionsLink.builder().build();
  }

  public WeeklyReportCalculationJakartaEntity mapToEntity(
      final WeeklyReportCalculation calculation) {
    if (calculation == null) {
      return null;
    }
    return WeeklyReportCalculationJakartaEntity.builder().build();
  }
}
