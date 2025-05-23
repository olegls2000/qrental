package ee.qrent.billing.report.repository.impl;

import ee.qrent.billing.report.adapter.repository.WeeklyReportTransactionRepository;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTransactionJakartaEntity;
import ee.qrent.billing.report.repository.spring.WeeklyReportTransactionSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportTransactionRepositoryImpl implements WeeklyReportTransactionRepository {
  final WeeklyReportTransactionSpringDataRepository springDataRepository;

  @Override
  public WeeklyReportTransactionJakartaEntity save(WeeklyReportTransactionJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
