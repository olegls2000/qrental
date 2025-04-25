package ee.qrent.notification.email.core.service.messagestrategy;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.core.service.LetterBuildStrategy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static ee.qrent.notification.email.api.in.request.EmailType.ERROR_EMAIL;

@AllArgsConstructor
public class ErrorLetterBuildStrategy implements LetterBuildStrategy {

  private final TemplateEngine templateEngine;

  @Override
  public boolean canApply(final EmailSendRequest emailSendRequest) {
    return ERROR_EMAIL == emailSendRequest.getType();
  }

  @Override
  public MimeMessage process(final EmailSendRequest emailSendRequest, final MimeMessage message)
      throws MessagingException {
    final var recipients = emailSendRequest.getRecipients().toArray(new String[0]);
    final var from = "app@qrent.ee";
    final var subject = "Error in Q-Rent Application";
    final var emailText = getEmailText(emailSendRequest.getProperties());

    final var helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setSubject(subject);
    helper.setText(emailText, true);
    helper.setTo(recipients);
    helper.setFrom(from);

    return message;
  }

  private String getEmailText(Map<String, Object> properties) {
    final var invoiceTemplate = "errorMailTemplate";
    final var context = new Context();
    context.setVariable("errorMessage", properties.get("errorMessage"));
    context.setVariable("stackTrace", properties.get("stackTrace"));
    context.setVariable("uuid", properties.get("uuid"));

    return templateEngine.process(invoiceTemplate, context);
  }
}
