package ee.qrent.queue.config.spring;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.out.QueueEntryDeletePort;
import ee.qrent.queue.api.out.QueueEntryLoadPort;
import ee.qrent.queue.api.out.QueueEntryAddPort;
import ee.qrent.queue.api.out.QueueEntryUpdatePort;
import ee.qrent.queue.core.QueueEntryPullResponseMapper;
import ee.qrent.queue.core.QueueEntryPushRequestMapper;
import ee.qrent.queue.core.QueueUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueServiceConfig {

  @Bean
  QueueUseCaseService getQueueUseCaseService(
      final QueueEntryLoadPort loadPort,
      final QueueEntryAddPort addPort,
      final QueueEntryUpdatePort updatePort,
      final QueueEntryDeletePort deletePort,
      final QueueEntryPushRequestMapper pushRequestMapper,
      final QueueEntryPullResponseMapper pullResponseMapper,
      final QDateTime qDateTime) {

    return new QueueUseCaseService(
        loadPort,
        addPort,
        updatePort,
        deletePort,
        pushRequestMapper,
        pullResponseMapper,
        qDateTime);
  }
}
