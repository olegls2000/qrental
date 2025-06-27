package ee.qrent.billing.task.config.spring;

import ee.qrent.billing.task.api.out.TaskRunResultLoadPort;
import ee.qrent.billing.task.persistence.adapter.TaskRunResultLoadAdapter;
import ee.qrent.billing.task.persistence.adapter.TaskRunResultPersistenceAdapter;
import ee.qrent.billing.task.persistence.mapper.TaskRunResultAdapterMapper;
import ee.qrent.billing.task.persistence.repository.TaskRunResultRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskAdapterConfig {
  @Bean
  TaskRunResultAdapterMapper getTaskRunResultAdapterMapper() {

    return new TaskRunResultAdapterMapper();
  }

  @Bean
  TaskRunResultLoadPort getTaskRunResultLoadAdapter(
      final TaskRunResultRepository repository, final TaskRunResultAdapterMapper mapper) {

    return new TaskRunResultLoadAdapter(repository, mapper);
  }

  @Bean
  TaskRunResultPersistenceAdapter getTaskRunResultPersistenceAdapter(
      final TaskRunResultRepository repository, final TaskRunResultAdapterMapper mapper) {

    return new TaskRunResultPersistenceAdapter(repository, mapper);
  }
}
