package ee.qrent.billing.report.adapter.adapter;

import ee.qrent.billing.report.adapter.mapper.WeeklyReportAdapterMapper;
import ee.qrent.billing.report.adapter.mapper.WeeklyReportCalculationAdapterMapper;
import ee.qrent.billing.report.adapter.repository.WeeklyReportCalculationRepository;

import ee.qrent.billing.report.adapter.repository.WeeklyReportCalculationResultRepository;
import ee.qrent.billing.report.adapter.repository.WeeklyReportRepository;
import ee.qrent.billing.report.adapter.repository.WeeklyReportTransactionRepository;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationResultJakartaEntity;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTransactionJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationPersistenceAdapter implements WeeklyReportCalculationAddPort {

  private final WeeklyReportCalculationRepository calculationRepository;
  private final WeeklyReportCalculationResultRepository weeklyReportCalculationResultRepository;
  private final WeeklyReportRepository weeklyReportRepository;
  private final WeeklyReportTransactionRepository weeklyReportTransactionRepository;
  private final WeeklyReportAdapterMapper weeklyReportMapper;
  private final WeeklyReportCalculationAdapterMapper weeklyReportCalculationMapper;

  @Override
  public WeeklyReportCalculation add(final WeeklyReportCalculation domain) {
    final WeeklyReportCalculationJakartaEntity weeklyReportCalculationEntity =
        weeklyReportCalculationMapper.mapToEntity(domain);
    final var weeklyReportCalculationEntitySaved =
        calculationRepository.save(weeklyReportCalculationEntity);

    domain.setId(weeklyReportCalculationEntitySaved.getId());

    final var transactionLinks = domain.getReportTransactionLinks();
    for (final var link : transactionLinks) {
      final var weeklyReport = link.getWeeklyReport();
      final var weeklyReportEntity = weeklyReportMapper.mapToEntity(weeklyReport);
      final var weeklyReportEntitySaved = weeklyReportRepository.save(weeklyReportEntity);
      weeklyReport.setId(weeklyReportEntitySaved.getId());

      final var weeklyReportCalculationResultEntity =
          WeeklyReportCalculationResultJakartaEntity.builder()
              .id(null)
              .calculation(weeklyReportCalculationEntitySaved)
              .weeklyReport(weeklyReportEntitySaved)
              .build();
      final var savedResult =
          weeklyReportCalculationResultRepository.save(weeklyReportCalculationResultEntity);

      final var transactionIds = link.getTransactionIds();
      for (Long transactionId : transactionIds) {
        final var weeklyReportTransactionEntity =
            WeeklyReportTransactionJakartaEntity.builder()
                .transactionId(transactionId)
                .weeklyReport(weeklyReportEntitySaved)
                .build();
        weeklyReportTransactionRepository.save(weeklyReportTransactionEntity);
      }
    }

    return domain;
  }
}
