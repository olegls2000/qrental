package ee.qrental.email.spring.config;

import static java.util.Arrays.asList;

import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.email.core.service.*;
import ee.qrental.email.core.service.messagestrategy.LetterBuildStrategy;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Configuration
public class EmailServiceConfig {

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
  EmailSendUseCase getEmailSendUseCase(
      final JavaMailSender mailSender, final List<LetterBuildStrategy> strategies) {
    return new EmailSendService(mailSender, strategies);
  }
}
