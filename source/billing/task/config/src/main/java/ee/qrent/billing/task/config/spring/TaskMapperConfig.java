package ee.qrent.billing.task.config.spring;

import ee.qrent.billing.task.core.mapper.TaskRunResultResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskMapperConfig {

  @Bean
  TaskRunResultResponseMapper getTaskRunResultResponseMapper() {

    return new TaskRunResultResponseMapper();
  }
}
