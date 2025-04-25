package ee.qrent.queue.config.spring;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.core.QueueEntryPullResponseMapper;
import ee.qrent.queue.core.QueueEntryPushRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueMapperConfig {
  @Bean
  QueueEntryPushRequestMapper getQueueEntryPushRequestMapper(final QDateTime qDateTime) {

    return new QueueEntryPushRequestMapper(qDateTime);
  }

  @Bean
  QueueEntryPullResponseMapper getQueueEntryPullResponseMapper() {

    return new QueueEntryPullResponseMapper();
  }
}
