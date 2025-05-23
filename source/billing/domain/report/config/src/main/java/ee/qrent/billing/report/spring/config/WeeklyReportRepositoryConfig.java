package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.report.adapter.repository.*;
import ee.qrent.billing.report.repository.impl.*;
import ee.qrent.billing.report.repository.spring.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportRepositoryConfig {

  @Bean
  WeeklyReportCalculationRepository getWeeklyReportCalculationRepositoryImpl(
      final WeeklyReportCalculationSpringDataRepository springDataRepository) {

    return new WeeklyReportCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  WeeklyReportRepository getWeeklyReportRepositoryImpl(
      final WeeklyReportSpringDataRepository springDataRepository) {

    return new WeeklyReportRepositoryImpl(springDataRepository);
  }

  @Bean
  WeeklyReportTransactionRepository getWeeklyReportTransactionRepositoryImpl(
      final WeeklyReportTransactionSpringDataRepository springDataRepository) {

    return new WeeklyReportTransactionRepositoryImpl(springDataRepository);
  }
}
