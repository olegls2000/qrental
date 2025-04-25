package ee.qrent.queue.config.spring;

import ee.qrent.queue.api.out.QueueEntryLoadPort;
import ee.qrent.queue.persistence.adapter.QueueEntryLoadAdapter;
import ee.qrent.queue.persistence.adapter.QueueEntryPersistenceAdapter;
import ee.qrent.queue.persistence.mapper.QueueEntryAdapterMapper;
import ee.qrent.queue.persistence.repository.QueueEntryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueAdapterConfig {

  @Bean
  public QueueEntryAdapterMapper getQueueEntryAdapterMapper() {
    return new QueueEntryAdapterMapper();
  }

  @Bean
  public QueueEntryLoadPort getQueueEntryLoadAdapter(
      final QueueEntryRepository repository, final QueueEntryAdapterMapper mapper) {

    return new QueueEntryLoadAdapter(repository, mapper);
  }

  @Bean
  QueueEntryPersistenceAdapter getQueueEntryPersistenceAdapter(
      final QueueEntryRepository repository, final QueueEntryAdapterMapper mapper) {

    return new QueueEntryPersistenceAdapter(repository, mapper);
  }
}
