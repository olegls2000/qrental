package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.report.adapter.adapter.WeeklyReportCalculationLoadAdapter;
import ee.qrent.billing.report.adapter.adapter.WeeklyReportLoadAdapter;
import ee.qrent.billing.report.adapter.adapter.WeeklyReportCalculationPersistenceAdapter;
import ee.qrent.billing.report.adapter.mapper.WeeklyReportAdapterMapper;
import ee.qrent.billing.report.adapter.mapper.WeeklyReportCalculationAdapterMapper;
import ee.qrent.billing.report.adapter.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportAdapterConfig {

  @Bean
  WeeklyReportLoadAdapter getWeeklyReportLoadAdapter(
      final WeeklyReportRepository repository, final WeeklyReportAdapterMapper mapper) {

    return new WeeklyReportLoadAdapter(repository, mapper);
  }

  @Bean
  WeeklyReportCalculationLoadAdapter getWeeklyReportCalculationLoadAdapter(
      final WeeklyReportCalculationRepository repository,
      final WeeklyReportCalculationAdapterMapper mapper) {

    return new WeeklyReportCalculationLoadAdapter(repository, mapper);
  }

  @Bean
  WeeklyReportCalculationPersistenceAdapter getWeeklyReportPersistenceAdapter(
      final WeeklyReportCalculationRepository calculationRepository,
      final WeeklyReportCalculationResultRepository weeklyReportCalculationResultRepository,
      final WeeklyReportRepository weeklyReportRepository,
      final WeeklyReportTransactionRepository weeklyReportTransactionRepository,
      final WeeklyReportAdapterMapper weeklyReportMapper,
      final WeeklyReportCalculationAdapterMapper weeklyReportCalculationMapper) {

    return new WeeklyReportCalculationPersistenceAdapter(
        calculationRepository,
        weeklyReportCalculationResultRepository,
        weeklyReportRepository,
        weeklyReportTransactionRepository,
        weeklyReportMapper,
        weeklyReportCalculationMapper);
  }
}
