package ee.qrent.notification.task.config.spring;

import ee.qrent.notification.email.api.in.usecase.EmailSendUseCase;
import ee.qrent.notification.task.api.in.query.GetTaskRunResultQuery;
import ee.qrent.notification.task.api.in.usecase.TaskRunUseCase;
import ee.qrent.notification.task.api.out.TaskRunResultAddPort;
import ee.qrent.notification.task.api.out.TaskRunResultLoadPort;
import ee.qrent.notification.task.core.*;
import ee.qrent.notification.task.core.mapper.TaskRunResultResponseMapper;
import ee.qrent.notification.task.core.service.QTaskRunnerImpl;
import ee.qrent.notification.task.core.service.TaskRunQueryService;
import ee.qrent.notification.task.core.service.TaskRunService;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.usecase.QTaskRunner;
import ee.qrent.notification.task.core.task.EmailNotificationTask;
import ee.qrent.queue.api.in.QueueEntryPullUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class TaskServiceConfig {

  @Bean
  public TaskScheduler getThreadPoolTaskScheduler() {
    final var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    final var NOTIFICATION_SCHEDULER_POOL_SIZE = 5;
    threadPoolTaskScheduler.setPoolSize(NOTIFICATION_SCHEDULER_POOL_SIZE);
    final var NOTIFICATION_SCHEDULER_NAME = "Notification-thread-pool-scheduler";
    threadPoolTaskScheduler.setThreadNamePrefix(NOTIFICATION_SCHEDULER_NAME);

    return threadPoolTaskScheduler;
  }

  @Bean
  public NotificationTaskScheduler getNotificationTaskScheduler(
      final TaskRunUseCase taskRunUseCase) {

    return new NotificationTaskScheduler(taskRunUseCase);
  }

  @Bean
  public QTaskRunner getQTaskRunnerImpl(
      final QDateTime qDateTime, final TaskRunResultAddPort addPort) {

    return new QTaskRunnerImpl(qDateTime, addPort);
  }

  @Bean
  public TaskRunUseCase getBTaskRunService(
      final QTaskRunner taskRunner, final EmailNotificationTask emailNotificationTask) {

    return new TaskRunService(taskRunner, emailNotificationTask);
  }

  @Bean
  public GetTaskRunResultQuery getTaskRunQueryService(
      final TaskRunResultLoadPort loadPort, final TaskRunResultResponseMapper mapper) {

    return new TaskRunQueryService(loadPort, mapper);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {

    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  EmailNotificationTask getEmailNotificationTask(
      final EmailSendUseCase emailSendUseCase, final QueueEntryPullUseCase queuePullUseCase) {

    return new EmailNotificationTask(emailSendUseCase, queuePullUseCase);
  }
}
