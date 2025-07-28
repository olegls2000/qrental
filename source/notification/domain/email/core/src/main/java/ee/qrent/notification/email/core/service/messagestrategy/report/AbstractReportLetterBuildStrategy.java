package ee.qrent.notification.email.core.service.messagestrategy.report;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.core.service.LetterBuildStrategy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Map;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
public abstract class AbstractReportLetterBuildStrategy implements LetterBuildStrategy {

  private static final String FROM = "operation@qrent.ee";

  private final TemplateEngine templateEngine;

  protected void useMimeMessageHelper(
      final MimeMessage message,
      final String subject,
      final EmailSendRequest emailSendRequest,
      final String attachmentFileName,
      final String templateName,
      final Map<String, Object> properties)
      throws MessagingException, IOException {

    final var recipients = emailSendRequest.getRecipients().toArray(new String[0]);
    final var attachment = new ByteArrayResource(emailSendRequest.getAttachment().readAllBytes());

    final var helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setSubject(subject);
    helper.setText(getEmailText(properties, templateName), true);
    helper.setTo(recipients);
    helper.setFrom(FROM);
    helper.addAttachment(attachmentFileName, attachment);
  }

  private String getEmailText(Map<String, Object> properties, String templateName) {
    final var context = new Context();
    context.setVariable("driverFirstName", properties.get("driverFirstName"));
    context.setVariable("driverLastName", properties.get("driverLastName"));
    context.setVariable("driverTaxNumber", properties.get("driverTaxNumber"));
    context.setVariable("callSign", properties.get("callSign"));

    return templateEngine.process(templateName, context);
  }
}
