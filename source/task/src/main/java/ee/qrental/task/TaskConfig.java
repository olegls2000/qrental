package ee.qrental.task;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
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
      final GetQWeekQuery qWeekQuery, final RentCalculationAddUseCase rentCalculationAddUseCase) {

    return new RentCalculationTask(qWeekQuery, rentCalculationAddUseCase);
  }
}
