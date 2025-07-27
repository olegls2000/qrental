package ee.qrent.notification.task.config.spring;

import ee.qrent.notification.task.persistence.repository.TaskRunResultRepository;
import ee.qrent.notification.task.persistence.repository.impl.TaskRunResultRepositoryImpl;
import ee.qrent.notification.task.persistence.repository.spring.TaskRunResultSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskRepositoryConfig {

  @Bean
  TaskRunResultRepository getTaskRunResultRepositoryImpl(
      final TaskRunResultSpringDataRepository springDataRepository) {

    return new TaskRunResultRepositoryImpl(springDataRepository);
  }
}
