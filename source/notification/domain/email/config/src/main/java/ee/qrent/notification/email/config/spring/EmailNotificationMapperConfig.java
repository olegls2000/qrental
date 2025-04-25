package ee.qrent.notification.email.config.spring;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.core.service.EmailSendRequestMapper;
import ee.qrent.notification.email.domain.EmailNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailNotificationMapperConfig {
  @Bean
  AddRequestMapper<EmailSendRequest, EmailNotification> getEmailSendRequestMapper(
      final QDateTime qDateTime) {

    return new EmailSendRequestMapper(qDateTime);
  }
}
