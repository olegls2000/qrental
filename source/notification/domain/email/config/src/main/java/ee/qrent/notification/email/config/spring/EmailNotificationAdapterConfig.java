package ee.qrent.notification.email.config.spring;

import ee.qrent.notification.email.persistence.adapter.EmailNotificationPersistenceAdapter;
import ee.qrent.notification.email.persistence.mapper.EmailNotificationAdapterMapper;
import ee.qrent.notification.email.persistence.repository.EmailNotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailNotificationAdapterConfig {

  @Bean
  EmailNotificationAdapterMapper getEmailNotificationAdapterMapper() {

    return new EmailNotificationAdapterMapper();
  }

  @Bean
  EmailNotificationPersistenceAdapter getEmailNotificationPersistenceAdapter(
      final EmailNotificationRepository repository, final EmailNotificationAdapterMapper mapper) {

    return new EmailNotificationPersistenceAdapter(repository, mapper);
  }
}
