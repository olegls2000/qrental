package ee.qrent.billing.task.config.spring;

import ee.qrent.billing.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.usecase.QWeekAddUseCase;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCalculationAddUseCase;
import ee.qrent.billing.task.core.*;
import ee.qrent.billing.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.usecase.RunTaskUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class BillingTaskConfig {

  @Bean
  public TaskScheduler getBillingTaskScheduler() {
    final var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    final var BILLING_SCHEDULER_POOL_SIZE = 5;
    threadPoolTaskScheduler.setPoolSize(BILLING_SCHEDULER_POOL_SIZE);
    final var BILLING_SCHEDULER_NAME = "Billing-thread-pool-scheduler";
    threadPoolTaskScheduler.setThreadNamePrefix(BILLING_SCHEDULER_NAME);

    return threadPoolTaskScheduler;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public RentCalculationTask getRentCalculationTask(
      final RentCalculationAddUseCase addUseCase, final GetQWeekQuery qWeekQuery) {

    return new RentCalculationTask(addUseCase, qWeekQuery);
  }

  @Bean
  public InsuranceCaseCalculationTask getInsuranceCaseCalculationTask(
      final InsuranceCalculationAddUseCase addUseCase, final GetQWeekQuery qWeekQuery) {

    return new InsuranceCaseCalculationTask(addUseCase, qWeekQuery);
  }

  @Bean
  public QWeekCreationTask getQWeekCreationTask(
      final QWeekAddUseCase qWeekAddUseCase, final QDateTime qDateTime) {

    return new QWeekCreationTask(qWeekAddUseCase, qDateTime);
  }

  @Bean
  public ObligationCalculationTask getObligationCalculationTask(
      ObligationCalculationAddUseCase addUseCase, final GetQWeekQuery qWeekQuery) {

    return new ObligationCalculationTask(addUseCase, qWeekQuery);
  }

  @Bean
  public BillingTaskScheduler getBillingTaskScheduler(
      final RunTaskUseCase runTaskUseCase,
      final InsuranceCaseCalculationTask insuranceCalculationTask,
      final ObligationCalculationTask obligationCalculationTask,
      final QWeekCreationTask qWeekCreationTask,
      final RentCalculationTask rentCalculationTask) {

    return new BillingTaskScheduler(
        runTaskUseCase,
        insuranceCalculationTask,
        obligationCalculationTask,
        qWeekCreationTask,
        rentCalculationTask);
  }
}
