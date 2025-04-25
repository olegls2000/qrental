package ee.qrent.queue.config.spring;

import ee.qrent.queue.persistence.repository.QueueEntryRepository;
import ee.qrent.queue.persistence.repository.impl.QueueEntryRepositoryImpl;
import ee.qrent.queue.persistence.repository.spring.QueueEntrySpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueRepositoryConfig {

  @Bean
  QueueEntryRepository queueEntryRepository(
      final QueueEntrySpringDataRepository springDataRepository) {

    return new QueueEntryRepositoryImpl(springDataRepository);
  }
}
