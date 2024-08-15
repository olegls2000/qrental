package ee.qrental.task;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.usecase.QWeekAddUseCase;
import ee.qrental.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrental.task.core.InsuranceCaseCalculationTask;
import ee.qrental.task.core.QTaskExecutor;
import ee.qrental.task.core.QWeekCreationTask;
import ee.qrental.task.core.RentCalculationTask;
import ee.qrental.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class TaskConfig {

  @Bean
  public TaskScheduler taskScheduler() {
    final var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(5);
    threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");

    return threadPoolTaskScheduler;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public RentCalculationTask getRentCalculationTask(
      final RentCalculationAddUseCase addUseCase,
      final GetQWeekQuery qWeekQuery,
      final QTaskExecutor qTaskExecutor) {

    return new RentCalculationTask(addUseCase, qWeekQuery, qTaskExecutor);
  }

  @Bean
  public InsuranceCaseCalculationTask getInsuranceCaseCalculationTask(
      final InsuranceCalculationAddUseCase addUseCase,
      final GetQWeekQuery qWeekQuery,
      final QTaskExecutor qTaskExecutor) {

    return new InsuranceCaseCalculationTask(addUseCase, qWeekQuery, qTaskExecutor);
  }

  @Bean
  public QWeekCreationTask getQWeekCreationTask(final QWeekAddUseCase qWeekAddUseCase) {

    return new QWeekCreationTask(qWeekAddUseCase);
  }

  @Bean
  public QTaskExecutor getQTaskExecutor(final QDateTime qDateTime) {
    return new QTaskExecutor(qDateTime);
  }
}
