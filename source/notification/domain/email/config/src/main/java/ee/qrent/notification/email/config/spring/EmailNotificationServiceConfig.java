package ee.qrent.notification.email.config.spring;

import static java.util.Arrays.asList;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.out.port.AddPort;
import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.core.service.*;
import ee.qrent.notification.email.core.service.messagestrategy.*;

import java.util.List;

import ee.qrent.notification.email.api.in.usecase.EmailSendUseCase;
import ee.qrent.notification.email.domain.EmailNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Configuration
public class EmailNotificationServiceConfig {

  @Bean
  List<LetterBuildStrategy> getLetterBuildStrategies(final TemplateEngine templateEngine) {

    return asList(
        new ContractLetterBuildStrategy(templateEngine),
        new InvoiceLetterBuildStrategy(templateEngine),
        new ErrorLetterBuildStrategy(templateEngine),
        new UserRegistrationLetterBuildStrategy(templateEngine),
        new RentCalculationLetterBuildStrategy(templateEngine),
        new BonusCalculationLetterBuildStrategy(templateEngine),
        new ObligationCalculationLetterBuildStrategy(templateEngine),
        new AuthorizationLetterBuildStrategy(templateEngine));
  }

  @Bean
  EmailSendUseCase getEmailSendService(
      final JavaMailSender mailSender,
      final List<LetterBuildStrategy> strategies,
      final AddPort<EmailNotification> addPort,
      final AddRequestMapper<EmailSendRequest, EmailNotification> addRequestMapper) {

    return new EmailSendService(mailSender, strategies, addPort, addRequestMapper);
  }
}
