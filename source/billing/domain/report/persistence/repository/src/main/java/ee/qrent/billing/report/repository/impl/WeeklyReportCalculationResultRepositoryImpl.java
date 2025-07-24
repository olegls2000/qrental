package ee.qrent.billing.report.repository.impl;

import ee.qrent.billing.report.adapter.repository.WeeklyReportCalculationResultRepository;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationResultJakartaEntity;
import ee.qrent.billing.report.repository.spring.WeeklyReportCalculationResultSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationResultRepositoryImpl
    implements WeeklyReportCalculationResultRepository {

  private final WeeklyReportCalculationResultSpringDataRepository springDataRepository;

  @Override
  public WeeklyReportCalculationResultJakartaEntity save(
      WeeklyReportCalculationResultJakartaEntity entity) {

    return springDataRepository.save(entity);
  }
}
