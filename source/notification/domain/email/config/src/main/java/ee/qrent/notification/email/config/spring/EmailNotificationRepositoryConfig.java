package ee.qrent.notification.email.config.spring;

import ee.qrent.notification.email.persistence.repository.EmailNotificationRepository;
import ee.qrent.notification.email.persistence.repository.impl.EmailNotificationRepositoryImpl;
import ee.qrent.notification.email.persistence.repository.spring.EmailNotificationSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailNotificationRepositoryConfig {

  @Bean
  EmailNotificationRepository getEmailNotificationRepository(
      final EmailNotificationSpringDataRepository springDataRepository) {

    return new EmailNotificationRepositoryImpl(springDataRepository);
  }
}
