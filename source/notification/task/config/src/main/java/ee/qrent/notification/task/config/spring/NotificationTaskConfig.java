package ee.qrent.notification.task.config.spring;

import ee.qrent.common.in.usecase.RunTaskUseCase;
import ee.qrent.notification.task.core.NotificationTaskScheduler;
import ee.qrent.notification.task.core.EmailNotificationTask;
import ee.qrent.notification.email.api.in.usecase.EmailSendUseCase;
import ee.qrent.queue.api.in.QueueEntryPullUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class NotificationTaskConfig {

  @Bean
  TaskScheduler getNotificationTaskScheduler() {
    final var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    final var NOTIFICATION_EXECUTOR_POOL_SIZE = 10;
    threadPoolTaskScheduler.setPoolSize(NOTIFICATION_EXECUTOR_POOL_SIZE);
    final var NOTIFICATION_EXECUTOR_NAME = "Notification-thread-pool-scheduler";
    threadPoolTaskScheduler.setThreadNamePrefix(NOTIFICATION_EXECUTOR_NAME);

    return threadPoolTaskScheduler;
  }

  @Bean
  PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  EmailNotificationTask getEmailNotificationTask(
      final EmailSendUseCase emailSendUseCase, final QueueEntryPullUseCase queuePullUseCase) {

    return new EmailNotificationTask(emailSendUseCase, queuePullUseCase);
  }

  @Bean
  NotificationTaskScheduler getEmailNotificationScheduler(
      final RunTaskUseCase runTaskUseCase, final EmailNotificationTask task) {

    return new NotificationTaskScheduler(runTaskUseCase, task);
  }
}
